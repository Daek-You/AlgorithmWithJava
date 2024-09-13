import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BOJ14567 {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());

        // 각 과목마다 진입 차수 개수 구하기
        int[] indegrees = new int[N + 1];
        ArrayList<Integer>[] edges = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)    edges[i] = new ArrayList<>();
        
        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            edges[from].add(to);
            indegrees[to]++;
        }

        // 진입 차수가 0인 과목들 큐에 담기
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (indegrees[i] == 0) {
                queue.add(i);
            }
        }

        // 각 학기마다 들을 수 있는 과목들을 들으며 위상정렬 시작
        int term = 1;
        int[] answers = new int[N + 1];
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int node = queue.poll();
                answers[node] = term;

                for (int next : edges[node]) {
                    if (--indegrees[next] == 0) {
                        queue.add(next);
                    }
                }
            }

            term++;
        }
        
        // 정답 출력하기
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++)    sb.append(answers[i]).append(" ");

        System.out.println(sb.toString());
    }
}