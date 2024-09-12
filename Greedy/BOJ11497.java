import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ11497 {
    private static int N;
    private static int[] Heights;

    public static void main(String[] args) throws Exception {

        // 각 인접한 통나무의 높이 차가 최소
        // 건너뛰기 난이도 = 인접한 두 통나무 간의 높이 차의 최댓값
        // 통나무 개수는 최소 5개  ~ 최대 1만개
        // 모든 인접한 나무의 높이 차가 최소가 되려면 짧은 나무는 짧은 나무들끼리, 높은 나무는 높은 나무들끼리 그룹을 형성해야 함
        // 오름차순으로 정렬된 게 가장 작은 높이 차이지만, "원형"이라는 점을 고려해야 함
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            // 입력 받기
            N = Integer.parseInt(br.readLine());
            Heights = new int[N];
            
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) Heights[i] = Integer.parseInt(st.nextToken());

            // 오름차순으로 정렬
            Arrays.sort(Heights);
            int leftTarget = Heights[0], rightTarget = Heights[0];

            boolean isEven = N % 2 == 0;
            int answer = 0;
            for (int left = 1, right = 2; left < N && right < N; left += 2, right += 2) {
                int leftDiff = Math.abs(leftTarget - Heights[left]);
                int rightDiff = Math.abs(rightTarget - Heights[right]);

                int maxDiff = Math.max(leftDiff, rightDiff);
                answer = Math.max(answer, maxDiff);

                leftTarget = Heights[left];
                rightTarget = Heights[right];
            }

            // 짝수 개수일 경우 마지막 통나무를 빼먹게 되므로 해당 통나무도 비교해주기
            if (isEven) {
                int leftDiff = Math.abs(leftTarget - Heights[N - 1]);
                int rightDiff = Math.abs(rightTarget - Heights[N - 1]);
                int maxDiff = Math.max(leftDiff, rightDiff);
                answer = Math.max(answer, maxDiff);
            }

            System.out.println(answer);
        }

        br.close();
    }
}
