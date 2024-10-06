import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

// A* 알고리즘 연습해보기
public class SWEA1226 {
    private static class Node implements Comparable<Node>{
        int row, col, gCost, fCost;

        public Node(int row, int col, int gCost, int fCost) {
            this.row = row;
            this.col = col;
            this.gCost = gCost;
            this.fCost = fCost;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.fCost, other.fCost);
        }
    }

    private static final int N = 16;
    private static final int[] DIR_ROWS = {-1, 1, 0, 0};
    private static final int[] DIR_COLS = {0, 0, -1, 1};

    private static int[][] Map = new int[N][N];
    private static boolean[][] visited;
    private static int StartRow, StartCol;
    private static int DestRow, DestCol;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        for (int i = 0; i < 10; i++) {
            // 테스트케이스 번호 읽기
            int testCaseNumber = Integer.parseInt(br.readLine());
            visited = new boolean[N][N];
            
            // 맵 입력 받기
            for (int row = 0; row < N; row++) {
                String line = br.readLine();
                for (int col = 0; col < N; col++) {
                    Map[row][col] = line.charAt(col) - '0';

                    if (Map[row][col] == 2) {
                        StartRow = row;
                        StartCol = col;
                    }
                    else if (Map[row][col] == 3) {
                        DestRow = row;
                        DestCol = col;
                    }
                }
            }

            // A* 알고리즘 실행
            int answer = aStar();
            System.out.printf("#%d %d\n", testCaseNumber, answer);
        }
        
        br.close();
    }

    // 맨해튼 거리 휴리스틱 함수
    private static int heuristic(int row, int col) {
        return Math.abs(DestRow - row) + Math.abs(DestCol - col);
    }

    // A* 알고리즘 구현
    private static int aStar() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(StartRow, StartCol, 0, heuristic(StartRow, StartCol)));
        visited[StartRow][StartCol] = true;

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // 도착점에 도달했으면 1 반환
            if (current.row == DestRow && current.col == DestCol) return 1;

            // 상, 하, 좌, 우 탐색
            for (int i = 0; i < 4; i++) {
                int nextRow = current.row + DIR_ROWS[i];
                int nextCol = current.col + DIR_COLS[i];

                // 맵 경계를 벗어나지 않고, 방문하지 않았으며, 벽이 아닌 경우
                if (nextRow >= 0 && nextRow < N && nextCol >= 0 && nextCol < N 
                    && !visited[nextRow][nextCol] && Map[nextRow][nextCol] != 1) {
                    
                    visited[nextRow][nextCol] = true;
                    int gCost = current.gCost + 1;                      // 이동 비용 1 증가
                    int fCost = gCost + heuristic(nextRow, nextCol);    // f = g + h
                    pq.add(new Node(nextRow, nextCol, gCost, fCost));
                }
            }
        }

        // 경로를 찾지 못했을 경우 0 반환
        return 0;
    }
}
