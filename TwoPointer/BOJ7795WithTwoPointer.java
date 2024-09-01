import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ7795WithTwoPointer {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        int T = Integer.parseInt(br.readLine());

        for (int tNum = 0; tNum < T; tNum++) {
		        // 1. 입력 받기
            String[] arguments = br.readLine().split(" ");
            int N = Integer.parseInt(arguments[0]);
            int M = Integer.parseInt(arguments[1]);

            int[] A = new int[N];
            arguments = br.readLine().split(" ");
            for (int i = 0; i < N; i++) A[i] = Integer.parseInt(arguments[i]);

            int[] B = new int[M];
            arguments = br.readLine().split(" ");
            for (int i = 0; i < M; i++) B[i] = Integer.parseInt(arguments[i]);

            // 2. A, B 배열 오름차순으로 정렬
            Arrays.sort(A);
            Arrays.sort(B);

            int bPtr = 0, answer = 0;
            for (int aPtr = 0; aPtr < N && bPtr < M;) {
                if (A[aPtr] <= B[bPtr]) {
                    aPtr++;
                    continue;
                }
                
				// A 배열의 현재 요소가 B 배열을 먹을 수 있다면, A 배열의 나머지 뒷 요소들도 다 먹을 수 있다.
                answer += (N - aPtr);
                bPtr++;
            }

            System.out.println(answer);
        }

        br.close();
    }
}
