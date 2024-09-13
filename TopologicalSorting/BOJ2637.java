import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BOJ2637 {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());

        // 기본 부품 -> 진입 차수가 0인 부품들
        ArrayList<int[]>[] edges = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)    edges[i] = new ArrayList<>();
        int[] indegrees = new int[N + 1];

        while (M-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            int subNode = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());
            edges[subNode].add(new int[] {node, count});
            indegrees[node]++;
        }

        int[] answer = solution(edges, indegrees, N);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            if (answer[i] != 0) sb.append(i).append(" ").append(answer[i]).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }


    private static int[] solution(ArrayList<int[]>[] edges, int[] indegrees, int N) {
        LinkedList<Integer> queue = new LinkedList<>();
        int[][] dp = new int[N + 1][N + 1];

        // 진입차수가 0인 애들 큐에 넣기
        for (int i = 1; i <= N; i++) {
            if (indegrees[i] == 0) {
                dp[i][i] = 1;
                queue.add(i);
            }
        }

        for (int i = 1; i <= N; i++) {
            int lowerPart = queue.poll();

            for (int[] nexts : edges[lowerPart]) {
                int upperPart = nexts[0], upperCount = nexts[1];

                // 상위 부품(upper)을 만드는 데에 필요한 하위 부품(lower) 개수 업데이트
                for (int j = 1; j <= N; j++) {
                    dp[upperPart][j] += dp[lowerPart][j] * upperCount;
                    // 필요한 하위 부품이 없는 j라면 0과 곱해져 0이 나올 것임
                }

                // 진입 차수가 0개가 되었으면 큐에 추가
                if (--indegrees[upperPart] == 0) {
                    queue.add(upperPart);
                }
            }
        }

        // dp[N]에 최종적으로 필요한 기본 부품 갯수들,
        // 중간 부품 갯수들은 0개인 채로 존재할 것
        return dp[N];
    }
}