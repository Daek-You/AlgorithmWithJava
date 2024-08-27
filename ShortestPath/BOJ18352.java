import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ18352 {
    private static class Route implements Comparable<Route> {
        int node, cost;

        public Route(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public int compareTo(Route o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    private static final int INF = 1_000_000_000;
    private static int N, M, K, X;
    private static ArrayList<Integer>[] Nodes;
    private static int[] Distances;


    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        
        Distances = new int[N + 1];
        Arrays.fill(Distances, INF);

        Nodes = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)    Nodes[i] = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            Nodes[from].add(to);
        }

        // 2. 다익스트라
        ArrayList<Integer> answers = dijkstra(X);
        
        if (answers.isEmpty()) {
            System.out.println("-1");
            return;
        }

        Collections.sort(answers);
        for (int answer : answers) {
            System.out.println(answer);
        }

        br.close();
    }

    public static ArrayList<Integer> dijkstra(int node) {
        PriorityQueue<Route> pq = new PriorityQueue<>();
        pq.add(new Route(node, 0));
        Distances[node] = 0;

        ArrayList<Integer> answers = new ArrayList<>();
        while (!pq.isEmpty()) {
            Route current = pq.poll();

            if (Distances[current.node] < current.cost) continue;

            if (current.cost == K) {
                answers.add(current.node);
                continue;
            }

            for (int nextNode : Nodes[current.node]) {
                int newCost = current.cost + 1;

                if (newCost < Distances[nextNode]) {
                    Distances[nextNode] = newCost;
                    pq.add(new Route(nextNode, newCost));
                }
            }
        }

        return answers;
    }
}