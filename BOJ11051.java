import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ11051 {
    private static final int MOD = 10_007;
    private static int[][] Pascal;

    public static void main(String[] args) throws Exception {
        
        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        Pascal = new int[N + 1][];

        // 2. 파스칼의 삼각형
        for (int i = 1; i <= N + 1; i++) {
            Pascal[i - 1] = new int[i];
        }

        Pascal[0][0] = 1;

        for (int n = 1; n <= N; n++) {
            for (int k = 0; k < Pascal[n].length; k++) {
                int leftParent = (k - 1 < 0) ? 0 : Pascal[n - 1][k - 1];
                int rightParent = (k < Pascal[n].length - 1) ? Pascal[n - 1][k] : 0;
                Pascal[n][k] = (leftParent % MOD + rightParent % MOD) % MOD;
            }
        }

        System.out.println(Pascal[N][K]);
        br.close();
    }
}
