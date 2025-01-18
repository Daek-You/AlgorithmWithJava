import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ10835 {
    private static int N;
    private static int[] A, B;
    private static Integer[][] dp;
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        A = new int[N];
        B = new int[N];
        dp = new Integer[N][N];
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) A[i] = Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) B[i] = Integer.parseInt(st.nextToken());
        
        int answer = solve(0, 0);
        System.out.println(answer);
    }
    
    private static int solve(int left, int right) {
        if (left >= N || right >= N) return 0;
        
        // 이미 계산된 경우
        if (dp[left][right] != null) return dp[left][right];
        
        // 왼쪽 카드만 버리기
        int score = solve(left + 1, right);
        
        // 양쪽 카드 모두 버리기
        score = Math.max(score, solve(left + 1, right + 1));
        
        // 오른쪽 카드만 버리기 (조건 충족 시)
        if (A[left] > B[right]) {
            score = Math.max(score, solve(left, right + 1) + B[right]);
        }
        
        return dp[left][right] = score;
    }
}