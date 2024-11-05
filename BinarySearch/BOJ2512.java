import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ2512 {
    private static int N, M;
    private static int[] Budgets;

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        N = Integer.parseInt(br.readLine());
        Budgets = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        int right = 0;
        for (int i = 0; i < N; i++) {
            Budgets[i] = Integer.parseInt(st.nextToken());
            right = Math.max(right, Budgets[i]);
        }

        M = Integer.parseInt(br.readLine());
        
        // 2. 이진 탐색
        int left = 1, maxSum = 0, answer = 0;
        while (left <= right) {
            int mid = (left + right) / 2;
            int sum = 0;
            int maxBudget = 0;
            boolean isAvailableBudget = true;

            // 현재 정한 정수 상한액(mid)으로 각 예산 계산
            for (int budget : Budgets) {
                int cost = Math.min(budget, mid);
                sum += cost;
                maxBudget = Math.max(maxBudget, cost);

                // 배정 예산이 총 예산을 넘어갈 경우에는 상한액을 낮추기
                if (sum > M) {
                    isAvailableBudget = false;
                    right = mid - 1;
                    break;
                }
            }

            // 배정 예산이 가능한 경우에는 좀 더 상한액을 높여서 진행
            if (isAvailableBudget) {
                if (maxSum < sum) {
                    maxSum = sum;
                    answer = maxBudget;
                }
                left = mid + 1;
            }
        }

        // 3. 정답 출력
        System.out.println(answer);
        br.close();
    }
}