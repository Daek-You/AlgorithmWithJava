import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ14950 {
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

    private static int N, M, T;
    private static ArrayList<Edge>[] Edges;

    public static void main(String[] args) throws Exception {
        // N개의 도시와 M개의 도로, 모든 도시의 쌍에는 그 도시를 연결하는 도로로 구성된 경로 존재 (양방향)
        // N <= 1만, M <= 3만이므로 정점이 적을 때 유리한 프림 알고리즘 사용

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        
        Edges = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)    Edges[i] = new ArrayList<>();


        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            Edges[from].add(new Edge(to, cost));
            Edges[to].add(new Edge(from, cost));
        }

        // 2. 프림의 알고리즘
        int answer = prim(1);
        System.out.println(answer);
        
        br.close();
    }

    public static int prim(int node) {
        // 1.시작 정점을 최소신장트리 집합에 넣기
        boolean[] MST = new boolean[N + 1];
        MST[node] = true;

        // 2. 시작 정점과 인접한 다른 노드 정보들 넣기
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        
        for (Edge edge : Edges[node]) {
            pq.add(new Edge(edge.node, edge.cost));
        }

        // 3. 비용 구하기
        int increaseCost = 0;
        int totalCost = 0;

        while (!pq.isEmpty()) {
            Edge next = pq.poll();

            // 이미 최소신장트리에 속한 노드면 skip
            if (MST[next.node]) continue;

            MST[next.node] = true;
            totalCost += next.cost + increaseCost;
            
            // 새로운 도시를 점령했기에 도로 비용 T만큼 증가
            increaseCost += T;

            // 점령한 도시의 인접 도시들 정보 받기
            for (Edge other : Edges[next.node]) {
                pq.add(new Edge(other.node, other.cost));
            }
        }

        return totalCost;
    }
}
