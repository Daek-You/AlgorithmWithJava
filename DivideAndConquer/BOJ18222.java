import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ18222 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long k = Long.parseLong(br.readLine());

		// 좌 (이전 꺼)
		// 우 (이전 꺼 플립된 거)
		// left ~ mid, mid + 1 ~ right
		// 01101001  -> 7번째 수  
		// 1001 -> 3  T
		// 01  -> 1   T
		// 1 -> F
		// T, T, F  = 0 -> 1 - 0 -> 0

		long size = 1;
		while (size <= k) size *= 2;

		int answer = divide(size, k) ? 1 : 0;
		System.out.println(answer);
		br.close();
	}

	private static boolean divide(long size, long k) {
		if (size == 1) return (k == 1) ? false : true;
		long mid = size >> 1;
		
		if (mid < k) return !divide(mid, k - mid);
		return divide(mid, k);
	}
}