import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ21924 {
    private static class Edge implements Comparable<Edge> {
        int node, cost;

        public Edge(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    private static int N, M;
    private static ArrayList<Edge>[] Edges;
    private static boolean[] MST;

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        Edges = new ArrayList[N + 1];
        MST = new boolean[N + 1];
        for (int i = 1; i <= N; i++)    Edges[i] = new ArrayList<>();

        long totalCost = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()), to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            Edges[from].add(new Edge(to, cost));
            Edges[to].add(new Edge(from, cost));
            totalCost += cost;
        }

        // 2. 최소신장트리
        long minCost = prim(1);
        long answer = totalCost - minCost;

        answer = isAllConnected()? answer : -1;
        System.out.println(answer);
        br.close();
    }

    public static long prim(int node) {
        MST[node] = true;

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for (Edge edge : Edges[node]) {
            pq.add(new Edge(edge.node, edge.cost));
        }

        long minCost = 0;
        while (!pq.isEmpty()) {
            Edge current = pq.poll();

            if (MST[current.node])  continue;

            MST[current.node] = true;
            minCost += current.cost;

            for (Edge other : Edges[current.node]) {
                pq.add(new Edge(other.node, other.cost));
            }
        }

        return minCost;
    }

    private static boolean isAllConnected() {
        for (int i = 1; i <= N; i++) {
            if (!MST[i]) {
                return false;
            }
        }

        return true;
    }
}
 