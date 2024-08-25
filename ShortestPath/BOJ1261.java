import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ1261 {
    private static class Position implements Comparable<Position> {
        int row, col, cost;

        public Position(int row, int col, int cost) {
            this.row = row;
            this.col = col;
            this.cost = cost;
        }

        @Override
        public int compareTo(Position o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    private static final int INF = 1_000_000_000;
    private static final int WALL = 1;
    private static int N, M;
    private static int[][] Map;
    private static int[][] BrokenWalls;

    private static final int DRIECTION_COUNT = 4;
    private static final int[] ROW_DIRS = {-1, 1, 0, 0};
    private static final int[] COL_DIRS = {0, 0, -1, 1};
   
    
    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        Map = new int[N][M];
        BrokenWalls = new int[N][M];

        for (int r = 0; r < N; r++) {
            String row = br.readLine();

            for (int c = 0; c < M; c++) {
                Map[r][c] = row.charAt(c) - '0';
                BrokenWalls[r][c] = INF;
            }
        }

        // 2. 다익스트라 탐색 시작
        dijkstra(0, 0);
        System.out.println(BrokenWalls[N - 1][M - 1]);
        br.close();
    }

    public static void dijkstra(int startRow, int startCol) {
        PriorityQueue<Position> pq = new PriorityQueue<>();
        pq.add(new Position(startRow, startCol, 0));
        BrokenWalls[startRow][startCol] = 0;
        
        while (!pq.isEmpty()) {
            Position current = pq.poll();

            if (BrokenWalls[current.row][current.col] < current.cost)   continue;

            for (int i = 0; i < DRIECTION_COUNT; i++) {
                int nextRow = current.row + ROW_DIRS[i];
                int nextCol = current.col + COL_DIRS[i];

                if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= M) continue;

                int isWall = (Map[nextRow][nextCol] == WALL)? 1 : 0;
                int newCost = current.cost + isWall;

                if (newCost < BrokenWalls[nextRow][nextCol]) {
                    BrokenWalls[nextRow][nextCol] = newCost;
                    pq.add(new Position(nextRow, nextCol, newCost));
                }
            }
        }
    }
}
