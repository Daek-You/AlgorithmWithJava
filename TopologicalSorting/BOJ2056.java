import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BOJ2056 {
    private static int N;
    private static ArrayList<Integer>[] Edges;
    private static int[] WorkTimes, Indegrees, DP;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        WorkTimes = new int[N + 1];
        Indegrees = new int[N + 1];
        DP = new int[N + 1];
        Edges = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) Edges[i] = new ArrayList<>();

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            WorkTimes[i] = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());

            while (count-- > 0) {
                int previousNode = Integer.parseInt(st.nextToken());
                Edges[previousNode].add(i);
                Indegrees[i]++;
            }
        }

        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (Indegrees[i] == 0) {
                queue.add(i);
                DP[i] = WorkTimes[i];
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int nextNode : Edges[current]) {
                if (DP[nextNode] < DP[current] + WorkTimes[nextNode]) {
                    DP[nextNode] = DP[current] + WorkTimes[nextNode];
                }

                if (--Indegrees[nextNode] == 0) {
                    queue.add(nextNode);
                }
            }
        }

        // 연결이 되어 있지 않은 경우도 있다.
        int answer = -1;
        for (int i = 1; i <= N; i++) {
            answer = Math.max(answer, DP[i]);
        }

        System.out.println(answer);
        br.close();
    }
}
