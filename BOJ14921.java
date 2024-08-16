import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ14921 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        int N = Integer.parseInt(br.readLine());

        int[] attributeValues = new int[N];
        String[] arguments = br.readLine().split(" ");
        for (int i = 0; i < N; i++) attributeValues[i] = Integer.parseInt(arguments[i]);

        int value = Integer.MAX_VALUE;
        int minDifference = Integer.MAX_VALUE;
        int left = 0, right = N - 1;

        while (left < right) {
            int sum = attributeValues[left] + attributeValues[right];
            int difference = Math.abs(sum);

            if (difference < minDifference) {
                minDifference = difference;
                value = sum;
            }

            if (sum == 0) {
                value = 0;
                break;
            } else if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }

        System.out.println(value);
    }
}
