import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ12865 {
	private static int N, K;
	private static int[] Weights, Values;
	private static int[] DP;
	
	public static void main(String[] args) throws Exception {
		
		// 1. 입력 받기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		Weights = new int[N];
		Values = new int[N];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			Weights[i] = Integer.parseInt(st.nextToken());
			Values[i] = Integer.parseInt(st.nextToken());
		}

		// 2. 냅색 구하기
		DP = new int[K + 1];
		
		for (int i = 0; i < N; i++) {
			for (int weight = K; weight >= Weights[i]; weight--) {
				// 현재 무게에서의 최대 가치 vs 이전 무게에서 현재 보석의 가치를 더한 가치
				DP[weight] = Math.max(DP[weight], DP[weight - Weights[i]] + Values[i]);
			}
		}
		
		System.out.println(DP[K]);
		br.close();
	}
}