import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ17951 {
    private static int N, K;
    private static int[] Scores;

    public static void main(String[] args) throws Exception {
        initialize();

        int left = 0, right = 20 * N;

        while (left <= right) {
            int mid = (left + right) / 2, sum = 0, groups = 0;

            for (int i = 0; i < N; i++) {
                sum += Scores[i];

                // 합이 mid 이상이라면, 해당 점수들을 하나의 구간으로 묶기
                if (mid <= sum) {
                    sum = 0;
                    groups++;
                }
            }

            if (groups >= K) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        System.out.println(right);
    }

    private static void initialize() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        String[] args = br.readLine().split(" ");

        N = Integer.parseInt(args[0]);
        K = Integer.parseInt(args[1]);
        Scores = new int[N];
        args = br.readLine().split(" ");

        for (int i = 0; i < N; i++) {
            Scores[i] = Integer.parseInt(args[i]);
        }

        br.close();
    }
}
