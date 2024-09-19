import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

// AOE(Activity On Edge) Network
public class BOJ1948 {
    private static class Edge {
        int node, cost;

        public Edge(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }
    }

    private static int N, M;
    private static ArrayList<Edge>[] Forwards, Backwards;
    private static int[] Indegrees, Earliests;

    public static void main(String[] args) throws Exception {

        // ----------1. 입력 받기 start----------
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        Indegrees = new int[N + 1];
        Earliests = new int[N + 1];
        Forwards = new ArrayList[N + 1];
        Backwards = new ArrayList[N + 1];

        for (int i = 1; i <= N; i++) {
            Forwards[i] = new ArrayList<>();
            Backwards[i] = new ArrayList<>();
        }

        while (M-- > 0 ) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            Forwards[from].add(new Edge(to, cost));
            Backwards[to].add(new Edge(from, cost));
            Indegrees[to]++;
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int destination = Integer.parseInt(st.nextToken());
        // ----------입력 받기 end----------

        // ----------2. 위상 정렬을 통해 Earliest Start Time 구하기 start----------
        // Earliest Start Time : 어떤 작업이 가장 빠르게 시작될 수 있는 시각
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(start);
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            for (Edge next : Forwards[current]) {
                int nextNode = next.node;
                int nextCost = next.cost;
                
                Earliests[nextNode] = Math.max(Earliests[nextNode], Earliests[current] + nextCost);
                if (--Indegrees[nextNode] == 0) {
                    queue.add(nextNode);
                }
            }
        }
        // ----------위상 정렬을 통해 Earliest 구하기 end----------
        
        // ----------역방향 그래프를 통해 임계 경로 간선 구하기 start----------
        int criticalPathEdges = 0;
        boolean[] visited = new boolean[N + 1];
        queue.add(destination);
        visited[destination] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (Edge next : Backwards[current]) {
                int nextNode = next.node;
                int nextCost = next.cost;

                // EST과 LST 차이가 0인 간선이 임계 경로에 속한다.
                // LST(Latest Start Time) : 어떤 작업이 이때부터 시작해도 이후의 작업을 지연시키지 않는 가장 늦은 시간
                if (Earliests[current] == Earliests[nextNode] + nextCost) {
                    criticalPathEdges++;

                    if (!visited[nextNode]) {
                        queue.add(nextNode);
                        visited[nextNode] = true;
                    }
                }
            }
        }
        // ----------역방향 그래프를 통해 임계 경로 간선 구하기 end----------

        // 정답 출력
        System.out.println(Earliests[destination]);
        System.out.println(criticalPathEdges);
        br.close();
    }
}