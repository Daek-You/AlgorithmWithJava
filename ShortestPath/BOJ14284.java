import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class BOJ14284 {
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

    private static final int INF = 1_000_000_000;
    private static int N, M;
    private static ArrayList<Edge>[] Edges;


    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        Edges = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)    Edges[i] = new ArrayList<>();

        // 간선 정보
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()), to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            Edges[from].add(new Edge(to, cost));
            Edges[to].add(new Edge(from, cost));
        }

        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken()), t = Integer.parseInt(st.nextToken());

        // 2. 다익스트라 알고리즘
        int answer = dijkstra(s, t);
        System.out.println(answer);
        br.close();
    }

    public static int dijkstra(int start, int end) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        int[] Costs = new int[N + 1];
        Arrays.fill(Costs, INF);

        pq.add(new Edge(start, 0));
        Costs[start] = 0;

        while (!pq.isEmpty()) {
            Edge current = pq.poll();

            if (current.node == end) {
                break;
            }

            if (Costs[current.node] < current.cost) {
                continue;
            }

            for (Edge other : Edges[current.node]) {
                int nextNode = other.node;
                int newCost = current.cost + other.cost;

                if (newCost < Costs[nextNode]) {
                    Costs[nextNode] = newCost;
                    pq.add(new Edge(nextNode, newCost));
                }
            }
        }

        return Costs[end];
    }
}
