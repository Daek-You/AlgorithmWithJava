import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ1766 {
    private static int N;
    private static int[] InDegrees;
    private static ArrayList<Integer>[] Edges;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        InDegrees = new int[N + 1];
        Edges = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)    Edges[i] = new ArrayList<>();

        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            Edges[from].add(to);
            InDegrees[to]++;
        }

        // 쉬운 문제부터 풀기 위해 PriorityQueue 사용
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 1; i <= N; i++) {
            if (InDegrees[i] == 0) {
                pq.add(i);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            int problem = pq.poll();
            sb.append(problem).append(" ");

            for (int nextProblem : Edges[problem]) {
                if (--InDegrees[nextProblem] == 0) {
                    pq.add(nextProblem);
                }
            }
        }

        System.out.println(sb.toString());
        br.close();
    }
}
