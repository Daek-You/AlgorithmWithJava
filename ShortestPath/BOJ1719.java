import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1719 {
    private static final int INF = 1_000_000_000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
        int[][] answers = new int[N + 1][N + 1];
        int[][] costs = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i != j) costs[i][j] = INF;
            }
        }

        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()), to = Integer.parseInt(st.nextToken()), cost = Integer.parseInt(st.nextToken());
            costs[from][to] = costs[to][from] = cost;
            answers[from][to] = to;
            answers[to][from] = from;
        }

        for (int mid = 1; mid <= N; mid++) {
            for (int from = 1; from <= N; from++) {
                for (int to = 1; to <= N; to++) {
                    // 더 작은 가중치로 갈 수 있는 중간 경로가 있다면 update
                    if (costs[from][to] > costs[from][mid] + costs[mid][to]) {
                        costs[from][to] = costs[from][mid] + costs[mid][to];
                        answers[from][to] = answers[from][mid];
                    }
                }
            }
        }

        // 정답 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i == j) sb.append("-");
                else        sb.append(answers[i][j]);
                sb.append(" ");
            }

            sb.append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}
