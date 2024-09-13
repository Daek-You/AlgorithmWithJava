import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

// 단순 위상정렬로는 54%에서 틀린다. -> DP를 활용해 갱신해나가야 함
public class BOJ1005 {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            // 입력 받기
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken()), K = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            int[] buildTimes = new int[N + 1];
            for (int i = 1; i <= N; i++)
                buildTimes[i] = Integer.parseInt(st.nextToken());

            ArrayList<Integer>[] edges = new ArrayList[N + 1];
            for (int i = 1; i <= N; i++)
                edges[i] = new ArrayList<>();

            int[] indegrees = new int[N + 1];
            while (K-- > 0) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());

                edges[from].add(to);
                indegrees[to]++;
            }

            int targetBuildingNumber = Integer.parseInt(br.readLine());

            // 위상 정렬 시작
            // 진입 차수가 0인 건물 번호를 큐에 넣고, DP 값 초기화
            int[] DP = new int[N + 1];
            LinkedList<Integer> queue = new LinkedList<>();
            for (int i = 1; i <= N; i++) {
                if (indegrees[i] == 0) {
                    queue.add(i);
                    DP[i] = buildTimes[i];
                }
            }

            while (!queue.isEmpty()) {
                int current = queue.poll();

                // 다음 건물 건설에 필요한 시간 vs 현재 건물까지 짓는 데 걸린 시간 + 다음 건물 짓는 시간
                for (int next : edges[current]) {
                    DP[next] = Math.max(DP[next], DP[current] + buildTimes[next]);
                    if (--indegrees[next] == 0) {
                        queue.add(next);
                    }
                }
            }

            System.out.println(DP[targetBuildingNumber]);
        }

        br.close();
    }
}
