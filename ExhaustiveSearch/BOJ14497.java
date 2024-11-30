import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BOJ14497 {
    private static int N, M;
    private static char[][] Map;
    private static boolean[][] Visited;

    private static final int[] ROW_DIRS = {-1, 1, 0, 0};
    private static final int[] COL_DIRS = {0, 0, -1, 1};
    private static final char EMPTY = '0';
    private static final char FRIEND = '1';
    private static final char REMOVE_TARGET = '2';
    private static final char CRIMINAL = '#';

    public static void main(String[] args) throws Exception {
        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] junanPosition = new int[] {Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1};
        int[] criminalPosition = new int[] {Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1};

        Map = new char[N][];
        for (int i = 0; i < N; i++) Map[i] = br.readLine().toCharArray();
        br.close();

        // 2. BFS 시작
        int answer = 0;
        while (!bfs(junanPosition[0], junanPosition[1])) {
            answer++;
        }

        System.out.println(answer + 1);
    }

    private static boolean bfs(int startRow, int startCol) {
        Visited = new boolean[N][M];
        LinkedList<int[]> queue = new LinkedList<>();
        LinkedList<int[]> removeTargets = new LinkedList<>();

        queue.add(new int[]{startRow, startCol});
        Visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0], col = current[1];

            for (int i = 0; i < 4; i++) {
                int nextRow = row + ROW_DIRS[i];
                int nextCol = col + COL_DIRS[i];

                if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= M || Visited[nextRow][nextCol])    continue;
                Visited[nextRow][nextCol] = true;

                if (Map[nextRow][nextCol] == CRIMINAL)      return true;
                else if (Map[nextRow][nextCol] == EMPTY)    queue.add(new int[]{nextRow, nextCol});
                else if (Map[nextRow][nextCol] == FRIEND) {
                    removeTargets.add(new int[]{nextRow, nextCol});
                    Map[nextRow][nextCol] = REMOVE_TARGET;
                }
            }
        }

        // 친구들 제거
        while (!removeTargets.isEmpty()) {
            int[] next = removeTargets.poll();
            Map[next[0]][next[1]] = EMPTY;
        }

        return false;
    }
}