import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ10423 {
    private static class City implements Comparable<City> {
        int node, cost;

        public City(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public int compareTo(City o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    private static int N, M, K;
    private static ArrayList<City>[] Nodes;

    public static void main(String[] args) throws Exception {
        // 도시 개수 1000개, 케이블 수 최대 10만 개, 발전소의 개수 K개
        // 모든 도시에 전기가 공급될 수 있어야 함
        // 발전소를 기준으로 최소 연결을 해야 함
        // 처음에는 발전소 인접 애들을 모두 MST에 등록 -> 인접 가중치들 PQ에 등록 -> 프림의 알고리즘

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // 발전소 입력 받기
        ArrayList<Integer> powerPlants = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) powerPlants.add(Integer.parseInt(st.nextToken()));

        // 정점 및 간선 정보 정리
        Nodes = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)    Nodes[i] = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()), to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            Nodes[from].add(new City(to, cost));
            Nodes[to].add(new City(from, cost));
        }

        // 2. 프림의 알고리즘
        int answer = prim(powerPlants);
        System.out.println(answer);
        br.close();
    }

    public static int prim(ArrayList<Integer> powerPlants) {
        PriorityQueue<City> pq = new PriorityQueue<>();
        boolean[] MST = new boolean[N + 1];

        // 발전소와 인접한 도시들 정보를 우선순위 큐에 넣기
        for (int city : powerPlants) {
            MST[city] = true;

            for (City otherCity : Nodes[city]) {
                pq.add(new City(otherCity.node, otherCity.cost));
            }
        }

        // 최소신장트리
        int edgeCount = 0, minSum = 0;
        while (!pq.isEmpty()) {
            City current = pq.poll();

            // 이미 최소신장트리 집합에 속한 도시라면 skip
            if (MST[current.node])  continue;

            MST[current.node] = true;
            minSum += current.cost;
            edgeCount++;

            // 최소신장트리가 만들어졌다면 stop
            if (edgeCount == N - 1) break;

            // 인접 도시들 정보 우선순위 큐에 넣어주기
            for (City otherCity : Nodes[current.node]) {
                pq.add(new City(otherCity.node, otherCity.cost));
            }
        }

        return minSum;
    }
}