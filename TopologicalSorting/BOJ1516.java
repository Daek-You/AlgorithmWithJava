import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BOJ1516 {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] inDegrees = new int[N + 1];
        int[] buildTimes = new int[N + 1];

        ArrayList<Integer>[] edges = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)    edges[i] = new ArrayList<>();

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            buildTimes[i] = Integer.parseInt(st.nextToken());

            while (true) {
                int previousBuilding = Integer.parseInt(st.nextToken());
                if (previousBuilding == -1) break;

                edges[previousBuilding].add(i);
                inDegrees[i]++;
            }
        }

        LinkedList<Integer> queue = new LinkedList<>();
        int[] dp = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            if (inDegrees[i] == 0) {
                queue.add(i);
                dp[i] = buildTimes[i];
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next : edges[current]) {
                if (dp[next] < dp[current] + buildTimes[next]) {
                    dp[next] = dp[current] + buildTimes[next];
                }

                if (--inDegrees[next] == 0) {
                    queue.add(next);
                }
            }
        }

        // 정답 출력
        for (int i = 1; i <= N; i++) {
            System.out.println(dp[i]);
        }

        br.close();
    }
}