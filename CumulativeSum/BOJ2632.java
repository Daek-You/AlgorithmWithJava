import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class BOJ2632 {
    private static int Size, M, N;
    private static int[] A, B;
    private static HashMap<Integer, Integer> APizza, BPizza;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Size = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        A = new int[M];
        N = Integer.parseInt(st.nextToken());
        B = new int[N];

        for (int i = 0; i < M; i++) A[i] = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) B[i] = Integer.parseInt(br.readLine());
        APizza = new HashMap<>();
        BPizza = new HashMap<>();
        
        getPizzaSums(A, M, APizza);
        getPizzaSums(B, N, BPizza);

        int answer = 0;
        for (int aSum : APizza.keySet()) {
            int bRequired = Size - aSum;
            if (BPizza.containsKey(bRequired))  answer += APizza.get(aSum) * BPizza.get(bRequired);
        }

        System.out.println(answer);
        br.close();
    }

    private static void getPizzaSums(int[] pizza, int length, HashMap<Integer, Integer> sums) {
        // 아무것도 선택하지 않는 경우
        sums.put(0, 1);
        
        int[] cumulativeSum = new int[length];
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length; j++) {
                cumulativeSum[j] += pizza[(i + j) % length];
                sums.put(cumulativeSum[j], sums.getOrDefault(cumulativeSum[j], 0) + 1);
            }
        }
        
        // 전체 피자 선택하는 경우
        int totalSum = 0;
        for (int i = 0; i < length; i++) totalSum += pizza[i];
        sums.put(totalSum, 1);
    }
}