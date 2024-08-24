import java.util.Arrays;
import java.util.Scanner;

public class BOJ16401 {
    private static int M, N;
    private static long[] SnackLengths;

    public static void main(String[] args) {
        initialize();
    
        long answer = binarySearch(1, SnackLengths[N - 1]);
        System.out.println(answer);
    }

    private static long binarySearch(long left, long right) {
        long maxLength = 0;

        while (left <= right) {
            long mid = (left + right) / 2;
            long numOfSnacks = 0;

            for (long snackLength : SnackLengths) {
                numOfSnacks += (snackLength / mid);
            }

            // 과자를 균일하게 나누어 줄 수 있을 때
            if (numOfSnacks / M >= 1) {
                maxLength = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return maxLength;
    }

    private static void initialize() {
        Scanner scanner = new Scanner(System.in);
        String[] args = scanner.nextLine().split(" ");

        M = Integer.parseInt(args[0]);
        N = Integer.parseInt(args[1]);
        SnackLengths = new long[N];
        
        args = scanner.nextLine().split(" ");
        
        for (int i = 0; i < N; i++) {
            SnackLengths[i] = Long.parseLong(args[i]);
        }

        Arrays.sort(SnackLengths);
        scanner.close();
    }
}