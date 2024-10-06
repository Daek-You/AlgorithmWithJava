import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA1953 {
    // 상, 우, 하, 좌
    private static final int DIRECTION_COUNT = 4;
    private static final int[] ROW_DIRS = {-1, 0, 1, 0};
    private static final int[] COL_DIRS = {0, 1, 0, -1};
    private static final int[][] Tunnels = {
        {},
        {0, 1, 2, 3},    // 1번 터널: 상, 우, 하, 좌
        {0, 2},          // 2번 터널: 상, 하
        {3, 1},          // 3번 터널: 좌, 우      
        {0, 1},          // 4번 터널: 상, 우      
        {2, 1},          // 5번 터널: 하, 우
        {2, 3},          // 6번 터널: 하, 좌
        {0, 3}           // 7번 터널: 상, 좌
    };

    private static int N, M, R, C, L;
    private static int[][] Map;
    private static boolean[][] Visited;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= T; testCase++) {
            // 1. 입력 받기
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());
            
            Visited = new boolean[N][M];
            Map = new int[N][M];
            for (int row = 0; row < N; row++) {
                st = new StringTokenizer(br.readLine());
                for (int col = 0; col < M; col++) Map[row][col] = Integer.parseInt(st.nextToken());
            }

            int answer = bfs();
            System.out.printf("#%d %d\n", testCase, answer);
        }

        br.close();
    }

    private static int bfs() {
        int positionCount = 1, currentTime = 1;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {R, C});
        Visited[R][C] = true;

        while (!queue.isEmpty() && currentTime < L) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int row = current[0], col = current[1];

                int holeType = Map[row][col];
                int[] directions = Tunnels[holeType];

                for (int direction : directions) {
                    int nextRow = row + ROW_DIRS[direction];
                    int nextCol = col + COL_DIRS[direction];

                    // 맵 밖이거나 터널이 없는 곳, 이미 방문했던 곳은 X
                    if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= M || Map[nextRow][nextCol] == 0) continue;
                    if (!isConnected(nextRow, nextCol, direction) || Visited[nextRow][nextCol])   continue;

                    Visited[nextRow][nextCol] = true;
                    queue.add(new int[] {nextRow, nextCol});
                    positionCount++;
                }
            }

            currentTime++;
        }

        return positionCount;
    }

    private static boolean isConnected(int nextRow, int nextCol, int myDirection) {
        // 다음 위치에 있는 터널의 방향 가져오기
        int holeType = Map[nextRow][nextCol];
        int[] directions = Tunnels[holeType];

        // 해당 터널의 방향 중에 내가 진행 중인 방향의 반대 방향이 존재한다면 연결된 것
        for (int otherHoleDirection : directions) {
            if (otherHoleDirection == (myDirection + 2) % DIRECTION_COUNT) {
                return true;
            }
        }

        return false;
    }
}
