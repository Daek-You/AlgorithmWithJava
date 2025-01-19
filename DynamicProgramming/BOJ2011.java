import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ2011 {
    private static final int MOD = 1000000;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        int[] dp = new int[input.length() + 1];
        dp[0] = 1;      // 빈 문자열은 1가지 경우

        for (int i = 1; i <= input.length(); i++) {
            // 현재 숫자가 한 자리로 해석이 가능한 경우 (1~9)  ->  분기할 구간이 없으므로 이전 경우의 수 그대로 가져감
            if (input.charAt(i - 1) != '0') {
                dp[i] = dp[i - 1];
            }

            // 두 자리 숫자로 해석 가능한 경우 (10 ~ 26) -> 한 자리로 해석하는 경우와 두 자리로 해석하는 경우를 합치기
            if (i >= 2) {
                int twoDigit = Integer.parseInt(input.substring(i - 2, i));
                if (twoDigit >= 10 && twoDigit <= 26) {
                    dp[i] = (dp[i] + dp[i - 2]) % MOD;
                }
            }
        }

        int answer = dp[input.length()];
        System.out.println(answer);
    }
}