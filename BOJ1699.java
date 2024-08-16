import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ1699 {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] DP = new int[N + 1];
		
		Arrays.fill(DP, Integer.MAX_VALUE);
		DP[0] = 0;
		DP[1] = 1;
		
		for (int num = 1; num <= N; num++) {
			for (int i = 1; i * i <= num; i++) {
				DP[num] = Math.min(DP[num], DP[num - i * i] + 1);
			}
		}
		
		System.out.println(DP[N]);
		br.close();
	}
}
