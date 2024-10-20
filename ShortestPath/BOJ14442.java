import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ14442 {
    // Node 클래스 정의
    private static class Node implements Comparable<Node>{
        int row, col;
        int gCost;              // 시작점에서 현재 노드까지의 실제 비용 (이동 횟수)
        int fCost;              // f(n) = g(n) + h(n)
        int brokenCount;        // 벽을 부순 횟수

        public Node(int row, int col, int gCost, int fCost, int brokenCount) {
            this.row = row;
            this.col = col;
            this.gCost = gCost;
            this.fCost = fCost;
            this.brokenCount = brokenCount;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.fCost, other.fCost);
        }
    }

    private static int N, M, K;
    private static char[][] Map;
    private static int[][][] GCost; // 3차원 배열로 수정

    // 상, 하, 좌, 우 이동 방향
    private static final int[] ROW_DIRS = {-1, 1, 0, 0};
    private static final int[] COL_DIRS = {0, 0, -1, 1}; 

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        Map = new char[N][M];
        GCost = new int[K + 1][N][M];
        for (int row = 0; row < N; row++) {
            String line = br.readLine();

            for (int col = 0; col < M; col++) {
                Map[row][col] = line.charAt(col);
                for(int k = 0; k <= K; k++) GCost[k][row][col] = Integer.MAX_VALUE;     // 초기 비용을 무한대로 설정
            }
        }

        // 최단 경로 찾기
        int answer = aStar(0, 0, N - 1, M - 1);
        System.out.println(answer);        
        br.close();
    }

    // A* 알고리즘 구현
    private static int aStar(int startRow, int startCol, int destRow, int destCol) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int initialHeuristic = heuristic(startRow, startCol, destRow, destCol);
        pq.add(new Node(startRow, startCol, 1, initialHeuristic, 0));
        GCost[0][startRow][startCol] = 1;

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int row = current.row;
            int col = current.col;
            int currentGCost = current.gCost;
            int currentBrokenCount = current.brokenCount;

            // 목적지에 도착한 경우
            if (row == destRow && col == destCol) return currentGCost;

            // 이미 더 짧은 경로로 방문한 경우 무시
            if (currentGCost > GCost[currentBrokenCount][row][col]) continue;

            // 상, 하, 좌, 우 탐색
            for (int i = 0; i < 4; i++) {
                int nextRow = row + ROW_DIRS[i];
                int nextCol = col + COL_DIRS[i];
                int newBrokenCount = currentBrokenCount;

                // 맵의 경계를 벗어나지 않는지 확인
                if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= M) continue;
                int newGCost = currentGCost + 1;

                // 다음 칸이 벽일 경우
                if (Map[nextRow][nextCol] == '1') {
                    if (newBrokenCount >= K) continue;  // 더 이상 벽을 부술 수 없으면 스킵
                    newBrokenCount++;
                }

                // 새로운 경로가 기존 경로보다 짧은지 확인
                if (newGCost < GCost[newBrokenCount][nextRow][nextCol]) {
                    GCost[newBrokenCount][nextRow][nextCol] = newGCost;
                    int newHeuristic = heuristic(nextRow, nextCol, destRow, destCol);
                    int newFCost = newGCost + newHeuristic;
                    pq.add(new Node(nextRow, nextCol, newGCost, newFCost, newBrokenCount));
                }
            }
        }

        return -1;
    }

    private static int heuristic(int row, int col, int destRow, int destCol) {
        return Math.abs(row - destRow) + Math.abs(col - destCol);
    }
}
