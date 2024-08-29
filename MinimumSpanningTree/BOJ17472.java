import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BOJ17472 {
    private static class Point {
        int row, col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static class Edge implements Comparable<Edge> {
        int node1, node2, cost;

        public Edge(int node1, int node2, int cost) {
            this.node1 = node1;
            this.node2 = node2;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    private static final int SEA = 0;
    private static final int ISLAND = 1;
    private static int N, M;
    private static int[][] Map;
    private static ArrayList<Edge> Edges = new ArrayList<>();
    private static int[] Parents, Ranks;

    private static final int DIRECTION_COUNT = 4;
    private static final int[] ROW_DIRECTIONS = {-1, 1, 0, 0};
    private static final int[] COL_DIRECTIONS = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {

        // 다리의 길이는 2 이상
        // 다리의 방향은 무조건 가로 또는 세로 방향의 일자 다리
        // 섬 A와 B를 연결하는 다리가 중간에 섬 C와 인접한 바다를 지나가는 경우에 섬 C는 A, B와 연결되어있는 것이 아니다.

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Map = new int[N][M];

        for (int row = 0; row < N; row++) {
            st = new StringTokenizer(br.readLine());

            for (int col = 0; col < M; col++) {
                Map[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        // 2. 각 섬에 대해 고유 번호 마킹하기
        int islandNumber = 10;                  // 섬의 개수는 최대 6개이므로 넉넉하게 10번부터
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                if (Map[row][col] == ISLAND) {
                    bfs(row, col, islandNumber++);            
                }
            }
        }

        // 3. 맵을 탐색하며 섬을 만나면 바다쪽을 향해 나가는 거리 계산하기 (맵이 최대 10 x 10이므로 충분히 가능)
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                if (Map[row][col] == SEA)   continue;

                // 섬을 만났다면 바다 방향을 찾고 거길 향해 뻗어나가기
                for (int i = 0; i < DIRECTION_COUNT; i++) {
                    int nextRow = row + ROW_DIRECTIONS[i];
                    int nextCol = col + COL_DIRECTIONS[i];

                    if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= M) continue;
                    
                    if (Map[nextRow][nextCol] == SEA) {
                       dfs(nextRow, nextCol, Map[row][col], ROW_DIRECTIONS[i], COL_DIRECTIONS[i], 1);
                    }
                }
            }
        }

        // 4. 크루스칼 알고리즘을 적용하기 위한 유니온 파인드 연산 준비
        Parents = new int[islandNumber];
        Ranks = new int[islandNumber];
        
        for (int i = 10; i < islandNumber; i++) {
            Parents[i] = i;
            Ranks[i] = 1;
        }
        
        // 5. 간선 정보들을 오름차순 정렬 후, 크루스칼 알고리즘 적용하기
        Collections.sort(Edges);
        int answer = 0, edgeCount = 0, numOfIslands = islandNumber - 10;
        
        for (Edge edge : Edges) {
            if (union(edge.node1, edge.node2)) {
                answer += edge.cost;
                edgeCount++;
                
                // 모든 섬을 연결했다면 종료
                if (edgeCount == numOfIslands - 1) break;
            }
        }

        // 모든 섬이 연결된 게 아니라면 -1
        answer = (edgeCount < numOfIslands - 1) ? -1 : answer;
        System.out.println(answer);
        br.close();

    }

    public static void bfs(int row, int col, int islandNumber) {
        LinkedList<Point> queue = new LinkedList<>();
        queue.add(new Point(row, col));
        Map[row][col] = islandNumber;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            for (int i = 0; i < DIRECTION_COUNT; i++) {
                int nextRow = current.row + ROW_DIRECTIONS[i];
                int nextCol = current.col + COL_DIRECTIONS[i];

                if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= M) continue;
                
                if (Map[nextRow][nextCol] == ISLAND) {
                    Map[nextRow][nextCol] = islandNumber;
                    queue.add(new Point(nextRow, nextCol));
                }
            }
        }
    }

    public static void dfs(int row, int col, int fromIslandNumber, int directionRow, int directionCol, int length) {
        // 맵 밖이라면 그만하기
        if (row < 0 || row >= N || col < 0 || col >= M) return;

        // 바다를 건너 육지를 발견했고, 그 경로가 2 이상이라면 간선 추가하기
        if (Map[row][col] != SEA) {
            if (length > 2) {
                int toIslandNumber = Map[row][col];
                Edges.add(new Edge(fromIslandNumber, toIslandNumber, length - 1));
            }

            return;
        }

        // 여전히 바다라면 뻗어나가기
        int nextRow = row + directionRow;
        int nextCol = col + directionCol;
        dfs(nextRow, nextCol, fromIslandNumber, directionRow, directionCol, length + 1);
    }

    public static boolean union(int node1, int node2) {
        int root1 = find(node1), root2 = find(node2);

        if (root1 == root2) return false;

        if (Ranks[root1] == Ranks[root2]) {
            Parents[root2] = root1;
            Ranks[root1]++;
        } else if (Ranks[root1] < Ranks[root2]) {
            Parents[root1] = root2;
        } else {
            Parents[root2] = root1;
        }

        return true;
    }

    public static int find(int node) {
        if (node == Parents[node])  return node;
        return Parents[node] = find(Parents[node]);
    }
}
