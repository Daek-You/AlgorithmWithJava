import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ9084 {

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for (int testCase = 1; testCase <= T; testCase++) {
			
			// 1. 입력 받기
			int n = Integer.parseInt(br.readLine());
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int[] coins = new int[n];
			for (int i = 0; i < n; i++)	coins[i] = Integer.parseInt(st.nextToken());
			
			int m = Integer.parseInt(br.readLine());
			int[] DP = new int[m + 1];
			DP[0] = 1;
			
			/*
			 * 처음에는 잘못 생각해서 금액을 기준으로 하여 동전을 돌렸음
			 * for (int cost = coins[0]; ...)
			 * 		for (int coin : coins)
			 * 			...
			 * 
			 * 이렇게 하니 이전 동전에 의해 중복 카운팅을 하는 문제 발생
			 * 동전을 기준으로 하여 금액을 구하는 로직으로 변경
			 */
			
			// 2. 현재 금액 경우의 수 = (현재 금액 - 동전 금액)에 해당하는 금액을 구할 수 있는 경우의 수
			for (int coin : coins) {
				for (int cost = coin; cost <= m; cost++) {
					DP[cost] += DP[cost - coin];
				}
			}

			System.out.println(DP[m]);
		}
		
		br.close();
	}
}
