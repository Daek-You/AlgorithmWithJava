import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class BOJ30804 {
    private static int N;
    private static int[] Fruits;
    private static Map<Integer, Integer> CountMap;

    public static void main(String[] args) throws Exception {
        initialize();
        int left = 0, maxLength = 0;

        for (int right = 0; right < N; right++) {
            CountMap.put(Fruits[right], CountMap.getOrDefault(Fruits[right], 0) + 1);

            while (CountMap.size() > 2) {
                CountMap.put(Fruits[left], CountMap.get(Fruits[left]) - 1);

                if (CountMap.get(Fruits[left]) == 0) {
                    CountMap.remove(Fruits[left]);
                }

                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        System.out.println(maxLength);
    }

    private static void initialize() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        String[] args = br.readLine().split(" ");
        Fruits = new int[N];
        CountMap = new HashMap<>();

        for (int i = 0; i < N; i++) {
            Fruits[i] = Integer.parseInt(args[i]);
        }

        br.close();
    }
}