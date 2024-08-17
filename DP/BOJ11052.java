import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ11052 {
    private static int N;
    private static int[] P;
    private static int[] Costs;

    public static void main(String[] args) throws Exception {
        initialize();

        for (int numOfCards = 2; numOfCards <= N; numOfCards++) {
            for (int idx = 1; idx < numOfCards; idx++) {
                Costs[numOfCards] = Math.max(Costs[numOfCards - idx] + Costs[idx], Costs[numOfCards]);
            }
        }

        System.out.println(Costs[N]);
    }

    private static void initialize() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        P = new int[N + 1];
        Costs = new int[N + 1];

        String[] args = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) {
            P[i] = Integer.parseInt(args[i - 1]);
            Costs[i] = P[i];
        }

        br.close();
    }
}