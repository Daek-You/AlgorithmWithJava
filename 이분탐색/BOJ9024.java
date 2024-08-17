import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ9024 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        int T = Integer.parseInt(br.readLine());

        for (int testCase = 0; testCase < T; testCase++) {
            String[] inputs = br.readLine().split(" ");
            int n = Integer.parseInt(inputs[0]);
            int k = Integer.parseInt(inputs[1]);

            inputs = br.readLine().split(" ");
            int[] numbers = new int[n];

            for (int i = 0; i < n; i++) {
                numbers[i] = Integer.parseInt(inputs[i]);
            }

            Arrays.sort(numbers);
            int answer = 0, difference = Integer.MAX_VALUE;

            for (int i = 0; i < n - 1; i++) {
                int left = i + 1, right = n - 1;

                while (left <= right) {
                    int mid = (left + right) / 2;
                    int sum = numbers[i] + numbers[mid];

                    if (Math.abs(sum - k) < difference) {
                        difference = Math.abs(sum - k);
                        answer = 1;
                    } else if (difference == Math.abs(sum - k)) {
                        answer++;
                    }

                    if (sum == k)       break;
                    else if (sum < k)   left = mid + 1;
                    else if (sum > k)   right = mid - 1;
                }
            }

            System.out.println(answer);
        }

        br.close();
    }
}
