import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

// 시간: 444ms, 메모리: 60756KB
public class BOJ22856 {
    private static int N;
    private static int[][] Graph;
    private static HashSet<Integer> Visited;

    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static int Answer;

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        Graph = new int[2][N + 1];
        Visited = new HashSet<>();

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            int leftChildNode = Integer.parseInt(st.nextToken()), rightChildNode = Integer.parseInt(st.nextToken());

            Graph[LEFT][node] = leftChildNode;
            Graph[RIGHT][node] = rightChildNode;
            Visited.add(i);
        }

        // 2. 순회 시작
        dfs(1);
        System.out.println(Answer);
        br.close();
    }

    private static void dfs(int node) {
        // 왼쪽 자식이 있다면
        if (Graph[LEFT][node] != -1) {
            Answer++;
            dfs(Graph[LEFT][node]);
            
            if (!Visited.isEmpty()) Answer++;
        }
        
        // 방문 체크
        Visited.remove(node);

        // 오른쪽 자식이 있다면
        if (Graph[RIGHT][node] != -1) {
            Answer++;
            dfs(Graph[RIGHT][node]);

            if (!Visited.isEmpty()) Answer++;
        }
    }
}
