import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ4485 {
    private static class Node implements Comparable<Node> {
        int row, col, gCost, fCost;

        public Node(int row, int col, int gCost, int fCost) {
            this.row = row;
            this.col = col;
            this.gCost = gCost;
            this.fCost = fCost;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.fCost, o.fCost);
        }
    }

    private static int N;
    private static int[][] Map;
    private static int[][] GCost;

    private static final int[] ROW_DIRS = {-1, 1, 0, 0};
    private static final int[] COL_DIRS = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int testCaseNum = 1;

        while (true) {
            N = Integer.parseInt(br.readLine());
            if (N == 0) break;

            Map = new int[N][N];
            GCost = new int[N][N];
            for (int row = 0; row < N; row++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                
                for (int col = 0; col < N; col++) {
                    Map[row][col] = Integer.parseInt(st.nextToken());
                    GCost[row][col] = Integer.MAX_VALUE;
                }
            }

            int answer = aStar(0, 0, N - 1, N - 1);
            sb.append("Problem ").append(testCaseNum++).append(": ").append(answer).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }

    private static int aStar(int startRow, int startCol, int destRow, int destCol) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int initHeuristic = heuristic(startRow, startCol, destRow, destCol);
        int initGCost = Map[startRow][startCol];

        pq.add(new Node(startRow, startCol, initGCost, initHeuristic));
        GCost[startRow][startCol] = initGCost;

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int row = current.row, col = current.col;
            int currentGCost = current.gCost;

            // 목적지에 도착한 경우 최단 경로 리턴
            if (row == destRow && col == destCol)   return currentGCost;

            // 현재 비용이 이미 저장된 최소 비용보다 크면 무시
            if (currentGCost > GCost[row][col])     continue;


            for (int i = 0; i < 4; i++) {
                int nextRow = row + ROW_DIRS[i];
                int nextCol = col + COL_DIRS[i];

                // 맵 밖이면 x
                if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= N) continue;

                int newGCost = currentGCost + Map[nextRow][nextCol];
                
                // 새로운 비용이 기존 비용보다 작으면 업데이트
                if (newGCost < GCost[nextRow][nextCol]) {
                    GCost[nextRow][nextCol] = newGCost;
                    int newHeuristic = heuristic(row, col, destRow, destCol);
                    int newF = newGCost + newHeuristic;

                    pq.add(new Node(nextRow, nextCol, newGCost, newF));
                }
            }
        }

        return -1;
    }

    private static int heuristic(int x1, int x2, int y1, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 -y2);
    }
}