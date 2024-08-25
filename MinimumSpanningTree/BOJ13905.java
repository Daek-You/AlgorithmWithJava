import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ13905 {
    private static class Bridge implements Comparable<Bridge> {
        int node, weight;

        public Bridge(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        @Override
        public int compareTo(Bridge o) {
            return Integer.compare(o.weight, this.weight);  // 다리가 버틸 수 있는 무게 값이 높은 걸 우선순위로
        }
    }

    private static int N, M;
    private static ArrayList<Bridge>[] Bridges;
    private static boolean[] MST;

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        MST = new boolean[N + 1];
        Bridges = new ArrayList[N + 1];

        for (int i = 1; i <= N; i++)    Bridges[i] = new ArrayList<>();
 
        st = new StringTokenizer(br.readLine());
        int startNode = Integer.parseInt(st.nextToken());
        int endNode = Integer.parseInt(st.nextToken());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            Bridges[from].add(new Bridge(to, weight));
            Bridges[to].add(new Bridge(from, weight));
        }

        // 2. 최소신장트리 구성
        int answer = prim(startNode, endNode);
        answer = (MST[endNode]) ? answer : 0;   // 도착지에 못 가는 경우도 있음
        System.out.println(answer);
        br.close();
    }

    public static int prim(int start, int end) {
        PriorityQueue<Bridge> pq = new PriorityQueue<>();
        MST[start] = true;

        for (Bridge bridge : Bridges[start]) {
            pq.add(new Bridge(bridge.node, bridge.weight));
        }
        
        int minWeight = Integer.MAX_VALUE;
        while (!pq.isEmpty()) {
            Bridge next = pq.poll();

            if (MST[next.node]) continue;

            MST[next.node] = true;
            minWeight = Math.min(minWeight, next.weight);

            if (next.node == end)   break;

            for (Bridge other : Bridges[next.node]) {
                pq.add(new Bridge(other.node, other.weight));                
            }
        }

        return minWeight;
    }
}
