import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ9344 {
    private static class Edge implements Comparable<Edge>{
        int from, to, cost;

        Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken()), q = Integer.parseInt(st.nextToken());

            int[] parents = new int[N + 1];
            int[] ranks = new int[N + 1];

            for (int i = 1; i <= N; i++) {
                parents[i] = i;
                ranks[i] = 1;
            }

            PriorityQueue<Edge> edges = new PriorityQueue<>();
            while (M-- > 0) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken()), v = Integer.parseInt(st.nextToken()), w = Integer.parseInt(st.nextToken());
                edges.add(new Edge(u, v, w));
            }

            int edgeCount = 0;
            boolean isConnected = false;
            while (!edges.isEmpty()) {
                Edge edge = edges.poll();

                if (union(parents, ranks, edge.from, edge.to)) {
                    if ((edge.from == p && edge.to == q) || (edge.from == q && edge.to == p)) isConnected = true;
                    
                    edgeCount++;
                    if (edgeCount == N - 1) break;
                }
            }

            String answer = (isConnected) ? "YES" : "NO";
            System.out.println(answer);
        }

        br.close();
    }

    private static int find(int[] parents, int node) {
        if (node == parents[node])  return node;
        return parents[node] = find(parents, parents[node]);
    }

    public static boolean union(int[] parents, int[] ranks, int node1, int node2) {
        int root1 = find(parents, node1), root2 = find(parents, node2);
        if (root1 == root2) return false;

        if (ranks[root1] == ranks[root2]) {
            parents[root2] = root1;
            ranks[root1]++;
        } else if (ranks[root1] < ranks[root2]) {
            parents[root1] = root2;
        } else {
            parents[root2] = root1;
        }

        return true;
    }
}
