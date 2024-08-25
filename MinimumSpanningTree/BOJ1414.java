import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class BOJ1414 {
    private static class LanCable implements Comparable<LanCable> {
        int from, to, length;

        public LanCable(int from, int to, int length) {
            this.from = from;
            this.to = to;
            this.length = length;
        }

        @Override
        public int compareTo(LanCable o) {
            return Integer.compare(this.length, o.length);
        }
    }

    private static int N;
    private static ArrayList<LanCable> Edges = new ArrayList<>();

    private static int[] Parents;
    private static int[] Ranks;

    public static void main(String[] args) throws Exception {
        // A -> B, B -> A로 가는 간선 가중치가 달라, 프림의 알고리즘보다는 크루스칼 알고리즘이 더 효율적

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        Parents = new int[N];
        Ranks = new int[N];
        
        for (int i = 0; i < N; i++) {
            Parents[i] = i;
            Ranks[i] = 1;
        }

        int totalCableLength = 0;
        for (int from = 0; from < N; from++) {
            String cableLengths = br.readLine();

            for (int to = 0; to < N; to++) {
                int cableLength = convertToIntLength(cableLengths.charAt(to));

                // 연결되지 않은 컴퓨터라면 skip
                if (cableLength == 0)   continue;

                Edges.add(new LanCable(from, to, cableLength));
                totalCableLength += cableLength;
            }
        }

        // 2. 최소신장트리
        Collections.sort(Edges);
        int nodeCount = 0, minLength = 0;

        for (LanCable edge : Edges) {
            if (isSameSet(edge.from, edge.to))  continue;

            union(edge.from, edge.to);
            nodeCount++;
            minLength += edge.length;

            if (nodeCount == N - 1) break;
        }

        int answer = (nodeCount < N - 1) ? -1 : totalCableLength - minLength;
        System.out.println(answer);
        br.close();
    }

    public static boolean isSameSet(int node1, int node2) {
        return find(node1) == find(node2);
    }

    public static int find(int node) {
        if (node == Parents[node])  return node;
        return Parents[node] = find(Parents[node]);
    }

    public static void union(int node1, int node2) {
        int root1 = find(node1), root2 = find(node2);

        if (root1 == root2) return;

        if (Ranks[root1] == Ranks[root2]) {
            Parents[root2] = root1;
            Ranks[root1]++;
        } else if (Ranks[root1] < Ranks[root2]) {
            Parents[root1] = root2;
        } else if (Ranks[root1] > Ranks[root2]) {
            Parents[root2] = root1;
        }
    }

    private static int convertToIntLength(char length) {
        if (length == '0')  return 0;
        if ('a' <= length && length <= 'z') return length - 'a' + 1;

        return length - 'A' + 27;
    }
}
