import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ22116 {
    private static class Node implements Comparable<Node> {
        int row, col, slope;

        public Node(int row, int col, int slope) {
            this.row = row;
            this.col = col;
            this.slope = slope;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.slope, o.slope);
        }
    }

    private static final int INF = 1_000_000_001;
    private static final int[] ROW_DIRS = {-1, 1, 0, 0};
    private static final int[] COL_DIRS = {0, 0, -1, 1};
    private static int N;
    private static int[][] Map;

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        Map = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) Map[i][j] = Integer.parseInt(st.nextToken());
        }

        // 2. 다익스트라
        System.out.println(dijkstra(0, 0));
    }

    private static int dijkstra(int startRow, int startCol) {
        int[][] slopes = new int[N][N];
        for (int[] slope : slopes)  Arrays.fill(slope, INF);
        PriorityQueue<Node> pq = new PriorityQueue<>();

        slopes[startRow][startCol] = 0;
        pq.add(new Node(startRow, startCol, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int row = current.row, col = current.col;
            int slope = current.slope;

            // 이미 더 좋은 루트로 간 경로가 있다면 skip
            if (slopes[row][col] < slope)   continue;

            for (int i = 0; i < 4; i++) {
                int nextRow = row + ROW_DIRS[i];
                int nextCol = col + COL_DIRS[i];

                // 맵 밖일 경우 x
                if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= N) continue;

                int newSlope = Math.abs(Map[nextRow][nextCol] - Map[row][col]);
                int maxSlope = Math.max(slope, newSlope);

                // 최소 경사의 최댓값이 더 작을 때에만 업데이트
                if (maxSlope < slopes[nextRow][nextCol]) {
                    slopes[nextRow][nextCol] = maxSlope;
                    pq.add(new Node(nextRow, nextCol, maxSlope));
                }
            }
        }

        return slopes[N-1][N-1];
    }
}