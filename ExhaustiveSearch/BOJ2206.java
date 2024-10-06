import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ2206 {
    private static final int WALL = 1;
    private static final int DIRECTION_COUNT = 4;
    private static final int[] ROW_DIRS = {-1, 1, 0, 0};
    private static final int[] COL_DIRS = {0, 0, -1, 1};

    private static int N, M;
    private static int[][] Map;
    private static boolean[][][] Visited;

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Map = new int[N][M];
        Visited = new boolean[2][N][M];         // 0: 벽 안 부수고 이동, 1: 벽 부수고 이동

        for (int row = 0; row < N; row++) {
            String line = br.readLine();
            for (int col = 0; col < M; col++)   Map[row][col] = line.charAt(col) - '0';
        }
        br.close();

        // 2. bfs 시작
        int answer = bfs(0, 0);
        System.out.println(answer);
    }

    private static int bfs(int startRow, int startCol) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {startRow, startCol, 0});   // {행, 열, 벽 부수기 유무}
        Visited[0][startRow][startCol] = true;
        
        int minDistance = 1;    // 시작점과 도착점도 포함해서 세야 한다고 했으니
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int level = 0; level < size; level++) {
                int[] current = queue.poll();
                int row = current[0], col = current[1], isBrokenWall = current[2];

                // 목표 지점에 도착했다면 리턴
                if (row == N - 1 && col == M - 1) {
                    return minDistance;
                }

                for (int i = 0; i < DIRECTION_COUNT; i++) {
                    int nextRow = row + ROW_DIRS[i];
                    int nextCol = col + COL_DIRS[i];
                    int nextBroken = isBrokenWall;

                    // 맵 밖이라면 진입 X
                    if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= M) continue;

                    // 이미 진입한 적이 있던 루트라면 진입 X
                    if (Visited[nextBroken][nextRow][nextCol])  continue;

                    // 다음 위치가 벽이라면
                    if (Map[nextRow][nextCol] == WALL) {
                        // 이미 이전에 벽을 부순 적이 있다면 더 이상 진입 X
                        if (nextBroken == 1)  continue;

                        // 이전에 벽을 부순 적이 없다면 부수고 진입
                        nextBroken = 1;
                    }

                    Visited[nextBroken][nextRow][nextCol] = true;
                    queue.add(new int[] {nextRow, nextCol, nextBroken});
                }
            }

            minDistance++;
        }

        return -1;
    }
}
