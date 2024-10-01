import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1700 {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] orders = new int[K];      // 전기 용품 사용 순서
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) orders[i] = Integer.parseInt(st.nextToken());

        int[] used = new int[N];    // 현재 콘센트에 꽂혀 있는 전기 용품
        int answer = 0;

        // 최소로 플러그를 빼는 횟수가 몇 번일지 세어 보기
        for (int i = 0 ; i < K; i++) {
            int current = orders[i];
            boolean isPluggedIn = false;
            
            // 이미 꽂혀 있는 제품인지 확인
            for (int j = 0; j < N; j++) {
                if (used[j] == current) {
                    isPluggedIn = true;
                    break;
                }
            }

            // 이미 꽂혀 있는 제품이라면 다음으로
            if (isPluggedIn)    continue;

            // 콘센트 빈 자리가 남아 있다면, 거기에 전기 제품 꼽기
            boolean emptySlotFound = false;
            for (int j = 0; j < N; j++) {
                if (used[j] == 0) {
                    used[j] = current;
                    emptySlotFound = true;
                    break;
                }
            }

            // 빈 자리가 있었다면 플러그를 꼽고 다음으로 넘어가면 됨
            if (emptySlotFound) continue;

            // 꽂을 자리가 없었다면, 현재 쓰는 제품들 중에서 가장 나중에 쓰이는 기기를 찾기
            int idxToUnplug = -1, farthest = -1;
            for (int j = 0; j < N; j++) {
                int nextUse = K;  // 앞으로 사용되지 않을 경우 최댓값으로 설정

                // 현재 콘센트에 꽂힌 제품이 앞으로 언제 다시 등장하는지 위치 구하기
                for (int k = i + 1; k < K; k++) {
                    if (orders[k] == used[j]) {
                        nextUse = k;
                        break;  // 첫 번째로 다시 사용될 시점을 찾으면 종료
                    }
                }

                // 콘센트에 꽂힌 제품들 중 더 나중에 쓰이는 제품이 있다면 갱신해주기
                if (nextUse > farthest) {
                    farthest = nextUse;
                    idxToUnplug = j;
                }
            }

            // 가장 나중에 쓰일 기기를 뽑고 새로운 기기 꼽기
            used[idxToUnplug] = current;
            answer++;
        }

        System.out.println(answer);
        br.close();
    }
}