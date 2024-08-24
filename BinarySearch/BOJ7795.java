import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ7795 {
    private static int[] A, B;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        int T = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= T; testCase++) {
            // 1. 입력 받기
            String[] input = br.readLine().split(" ");
            int N = Integer.parseInt(input[0]);
            int M = Integer.parseInt(input[1]);
            A = new int[N];
            B = new int[M];

            input = br.readLine().split(" ");
            for (int i = 0; i < N; i++) A[i] = Integer.parseInt(input[i]);

            input = br.readLine().split(" ");
            for (int i = 0; i < M; i++) B[i] = Integer.parseInt(input[i]);

            // 2. A 오름차순 정렬
            Arrays.sort(A);

            long answer = 0;
            for (int bSize : B) {
                int upperIndex = upperBound(0, N - 1, bSize);

                if (bSize < A[upperIndex]) {
                    answer += N - upperIndex;
                }
            }

            System.out.println(answer);
        }

        br.close();
    }

    private static int upperBound(int left, int right, int value) {
        while (left < right) {
            int mid = (left + right) / 2;

            if (A[mid] <= value)    left = mid + 1;
            else                    right = mid;

        }

        return right;
    }
}
