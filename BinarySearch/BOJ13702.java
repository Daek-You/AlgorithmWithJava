import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ13702 {
    private static int N, K;
    private static long[] Drinks;
    private static long MaxDrinkAmount;

    public static void main(String[] args) throws Exception {
        initialize();
        Arrays.sort(Drinks);

        long left = 1, right = MaxDrinkAmount, answer = 0;

        while (left <= right) {
            long mid = (left + right) / 2;
            long numOfFriends = 0;

            for (long drinkAmount : Drinks) {
                numOfFriends += drinkAmount / mid;
            }

            if (K <= numOfFriends) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        System.out.println(answer);
    }
    
    private static void initialize() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] args = br.readLine().split(" ");
        
        N = Integer.parseInt(args[0]);
        K = Integer.parseInt(args[1]);

        Drinks = new long[N];
        for (int i = 0; i < N; i++) {
            Drinks[i] = Long.parseLong(br.readLine());
            MaxDrinkAmount = Math.max(MaxDrinkAmount, Drinks[i]);
        }

        br.close();
    }
}
