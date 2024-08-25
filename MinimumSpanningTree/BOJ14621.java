import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class BOJ14621 {
    private static class Edge implements Comparable<Edge> {
        int from, to, distance;

        public Edge(int from, int to, int distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.distance, o.distance);
        }
    }

    private static int N, M;
    private static char[] Types;
    private static int[] Parents;
    private static int[] Ranks;
    private static ArrayList<Edge> Edges;

    public static void main(String[] args) throws Exception {
        // 두 개의 집단을 구분해 연결해야 함 -> 분리 집합 -> 크루스칼 알고리즘

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Types = new char[N + 1];

        String[] types = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) {
            Types[i] = types[i - 1].charAt(0);
        }

        Parents = new int[N + 1];
        Ranks = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            Parents[i] = i;
            Ranks[i] = 1;
        }

        Edges = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()), to = Integer.parseInt(st.nextToken());
            int distance = Integer.parseInt(st.nextToken());

            Edges.add(new Edge(from, to, distance));
        }

        Collections.sort(Edges);

        // 2. 크루스칼 알고리즘
        int answer = 0;
        for (Edge edge : Edges) {
            if (canConnect(edge.from, edge.to)) {
                answer += edge.distance;
                union(edge.from, edge.to);
            }
        }

        answer = isAllConnected() ? answer : -1;
        System.out.println(answer);
        br.close();
    }

    public static boolean canConnect(int from, int to) {
        // 같은 성별 집단 대학끼리는 연결 불가
        if (Types[from] == Types[to])   return false;

        //  이미 같은 집합일 경우에도 불가능
        int root1 = find(from), root2 = find(to);
        return root1 != root2;
    }

    public static boolean isAllConnected() {
        int x = find(1);

        for (int i = 2; i <= N; i++) {
            int root = find(i);

            // 다른 집합에 속하는 학교가 있다면 모든 학교가 연결된 것이 아님
            if (root != x) {
                return false;
            }
        }

        return true;
    }

    public static int find(int node) {
        // 루트 노드일 경우
        if (node == Parents[node])   return node;
        return Parents[node] = find(Parents[node]);
    }

    public static void union(int node1, int node2) {
        int root1 = find(node1), root2 = find(node2);

        if (root1 == root2) return;

        // 두 분리 집합 트리의 높이가 같다면, 둘 중 하나를 골라 다른 쪽 하위로 넣기 (넣어진 쪽은 높이가 1 증가)
        if (Ranks[root1] == Ranks[root2]) {
            Parents[root2] = root1;
            Ranks[root1]++;
        }

        // 높이가 작은 트리가 높이가 높은 트리 밑으로 들어가서 편향 트리가 발생하지 않음
        else if (Ranks[root1] < Ranks[root2]) {
            Parents[root1] = root2;
        } else if (Ranks[root1] > Ranks[root2]) {
            Parents[root2] = root1;
        }
    }
}
