import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class BOJ21276 {
    private static int N;
    private static TreeSet<String> Parents = new TreeSet<>();
    private static TreeMap<String, ArrayList<String>> Childrens;
    private static HashMap<String, ArrayList<String>> Edges;
    private static HashMap<String, Integer> Indegrees;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        Childrens = new TreeMap<>();
        Edges = new HashMap<>();
        Indegrees = new HashMap<>();

        for (int i = 0; i < N; i++) {
            String name = st.nextToken();
            Childrens.put(name, new ArrayList<>());
            Edges.put(name, new ArrayList<>());
            Indegrees.put(name, 0);
        }

        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            String child = st.nextToken(), parent = st.nextToken();

            if (!Edges.containsKey(parent)) {
                Edges.put(parent, new ArrayList<>());
            }

            Edges.get(parent).add(child);
            Indegrees.put(child, Indegrees.get(child) + 1);
        }

        LinkedList<String> queue = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : Indegrees.entrySet()) {
            if (entry.getValue() == 0) {
                Parents.add(entry.getKey());
                queue.add(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            String parent = queue.poll();

            for (String child : Edges.get(parent)) {
                Indegrees.put(child, Indegrees.get(child) - 1);

                if (Indegrees.get(child) == 0) {
                    queue.add(child);
                    Childrens.get(parent).add(child);
                }
            }
        }

        // 정답 출력
        StringBuilder sb = new StringBuilder();
        sb.append(Parents.size()).append("\n");
        for (String name : Parents) sb.append(name).append(" ");
        sb.append("\n");

        for (Map.Entry<String, ArrayList<String>> entry : Childrens.entrySet()) {
            sb.append(entry.getKey()).append(" ").append(entry.getValue().size()).append(" ");
            Collections.sort(entry.getValue());

            for (String child : entry.getValue()) sb.append(child).append(" ");
            sb.append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }
}