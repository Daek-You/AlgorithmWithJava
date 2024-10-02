import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ11909 {
    private static int N;
    private static int[][] Map;
    private static int[][] DP;
    private static final int INF = 1_000_000_000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        Map = new int[N][N];
        DP = new int[N][N];

        for (int row = 0; row < N; row++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < N; col++) {
                Map[row][col] = Integer.parseInt(st.nextToken());
                DP[row][col] = INF;
            }
        }

        DP[0][0] = 0;  // 시작점에서 버튼을 누른 횟수는 0

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                int currentCost = DP[row][col];

                // 아래쪽으로 이동할 때
                if (row < N - 1) {
                    int nextRow = row + 1;
                    int extraPresses = 0;
                    
                    // 다음 칸의 높이가 현재보다 높거나 같으면 버튼을 눌러야 한다
                    if (Map[nextRow][col] >= Map[row][col]) {
                        extraPresses = Map[nextRow][col] - Map[row][col] + 1;
                    }
                    
                    DP[nextRow][col] = Math.min(DP[nextRow][col], currentCost + extraPresses);
                }

                // 오른쪽으로 이동할 때
                if (col < N - 1) {
                    int nextCol = col + 1;
                    int extraPresses = 0;
                    
                    // 다음 칸의 높이가 현재보다 높거나 같으면 버튼을 눌러야 한다
                    if (Map[row][nextCol] >= Map[row][col]) {
                        extraPresses = Map[row][nextCol] - Map[row][col] + 1;
                    }
                    
                    DP[row][nextCol] = Math.min(DP[row][nextCol], currentCost + extraPresses);
                }
            }
        }

        System.out.println(DP[N - 1][N - 1]);  // 도착점까지 최소 버튼 누름 횟수 출력
        br.close();
    }
}
