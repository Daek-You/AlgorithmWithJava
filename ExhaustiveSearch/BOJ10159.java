import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ10159 {
    private static int N, M;
    private static int[][] Map;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        Map = new int[N + 1][N + 1];

        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int heavy = Integer.parseInt(st.nextToken());
            int light = Integer.parseInt(st.nextToken());
            Map[heavy][light] = 1;
        }

        // i행은 i번 물건의 방문 여부 확인용
        for (int i = 1; i <= N; i++) Map[i][0] = -1;

        // DFS 탐색 시작
        for (int i = 1; i <= N; i++) {
            if (Map[i][0] != -1) continue;
            dfs(i);
        }

        // 0행에 각 열의 나보다 가벼운 물건들을 누적하여 개수 저장
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                Map[0][j] += Map[i][j];
            }
        }

        // 무게 관계가 불확실한 물건 개수 계산
        int[] answers = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            if (Map[i][0] + Map[0][i] != N - 1) {
                answers[i] = N - 1 - (Map[i][0] + Map[0][i]);
            }
        }

        // 정답 출력
        for (int i = 1; i <= N; i++) System.out.println(answers[i]);
        br.close();
    }

    private static void dfs(int node) {

        for (int i = 1; i <= N; i++) {
            // 무게 관계를 모르는 물건은 skip
            if (Map[node][i] == 0) continue;

            // 탐색하지 않은 물건에 대해 탐색 진행
            if (Map[i][0] == -1) dfs(i);

            // node보다 무거운 물건이 있다면 그 관계를 node에 반영
            if (Map[i][0] > 0) {
                for (int j = 1; j <= N; j++) {
                    if (Map[i][j] != 0) Map[node][j] = 1;
                }
            }
        }

        // 탐색 완료 표시
        Map[node][0] = 0;
        
        for (int k = 1; k <= N; k++) {
            // node보다 무거운 물건 개수 누적
            Map[node][0] += Map[node][k];
        }
    }
}