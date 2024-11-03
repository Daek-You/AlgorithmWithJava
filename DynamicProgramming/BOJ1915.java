import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1915 {
    private static int N, M;
    private static int[][] Map;
    private static int[][] DP;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Map = new int[N][M];
        DP = new int[N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) Map[i][j] = line.charAt(j) - '0';
        }

        int maxSquareLength = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {

                if (Map[i][j] == 1) {
                    // 첫 행이나 첫 열에서는 자기 자신이 최대 정사각형 크기
                    if (i == 0 || j == 0) {
                        DP[i][j] = 1;
                    } 
                    
                    // 왼쪽, 위, 왼쪽 위 중 최소값 + 1
                    else {
                        DP[i][j] = Math.min(Math.min(DP[i - 1][j], DP[i][j - 1]), DP[i - 1][j - 1]) + 1;
                    }

                    maxSquareLength = Math.max(maxSquareLength, DP[i][j]);
                }
            }
        }

        int answer = maxSquareLength * maxSquareLength;
        System.out.println(answer);
        br.close();
    }
}
