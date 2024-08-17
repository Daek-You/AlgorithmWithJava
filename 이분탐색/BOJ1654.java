import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ1654 {
    private static int K, N;
    private static long[] LanCables;

    public static void main(String[] args) throws Exception {
        initialize();
        long answer = binarySearch(1, LanCables[K - 1]);

        System.out.println(answer);
    }

    private static long binarySearch(long minLength, long maxLength) {
        long currentLength = 0;

        while (minLength <= maxLength) {
            long mid = (minLength + maxLength) / 2;
            long numOfLanCables = 0;

            for (long cable : LanCables) {
                numOfLanCables += (cable / mid);
            }

            if (numOfLanCables >= N) {
                currentLength = mid;
                minLength = mid + 1;
            } else {
                maxLength = mid - 1;
            }
        }
        
        return currentLength;
    }

    private static void initialize() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] args = br.readLine().split(" ");

        K = Integer.parseInt(args[0]);
        N = Integer.parseInt(args[1]);
        LanCables = new long[K];

        for (int i = 0; i < K; i++) {
            LanCables[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(LanCables);
        br.close();
    }
}