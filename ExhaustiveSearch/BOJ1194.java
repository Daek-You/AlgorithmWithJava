import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ1194 {
    private static final char WALL = '#';
    private static final char START = '0';
    private static final char EXIT = '1';
    private static final int DIRECTION_COUNT = 4;
    private static final int[] ROW_DIRS = {-1, 1, 0, 0};
    private static final int[] COL_DIRS = {0, 0, -1, 1};
    private static final int KEY_COUNT = 6;

    private static int N, M;
    private static char[][] Map;
    private static boolean[][][] Visited;

    public static void main(String[] args) throws Exception {
        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Map = new char[N][M];
        Visited = new boolean[1 << KEY_COUNT][N][M];    // [보유한 키][행][열]
        int startRow = -1, startCol = -1;

        for (int row = 0; row < N; row++) {
            String line = br.readLine();
            
            for (int col = 0; col < M; col++) {
                Map[row][col] = line.charAt(col);
                if (Map[row][col] == START) {
                    startRow = row;
                    startCol = col;
                }
            }
        }
        br.close();

        // 2. BFS 시작
        int answer = bfs(startRow, startCol);
        System.out.println(answer);
    }

    private static int bfs(int startRow, int startCol) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{startRow, startCol, 0});      // {row, col, keys}
        Visited[0][startRow][startCol] = true;              // 처음엔 보유한 키가 없으므로(0b0000000 = 0)

        int moveCount = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            // 현재 레벨의 모든 노드를 탐색
            for (int s = 0; s < size; s++) {
                int[] current = queue.poll();
                int row = current[0], col = current[1];
                int keys = current[2];

                // 현재 위치가 출구라면 이동 횟수 반환
                if (Map[row][col] == EXIT) {
                    return moveCount;
                }

                for (int i = 0; i < DIRECTION_COUNT; i++) {
                    int nextRow = row + ROW_DIRS[i];
                    int nextCol = col + COL_DIRS[i];
                    int nextKeys = keys;

                    // 맵 밖이거나 벽이면 무시
                    if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= M || Map[nextRow][nextCol] == WALL)
                        continue;

                    // 문일 때 키가 없으면 무시
                    if ('A' <= Map[nextRow][nextCol] && Map[nextRow][nextCol] <= 'F') {
                        int keyIndex = Map[nextRow][nextCol] - 'A';
                        if ((nextKeys & (1 << keyIndex)) == 0)  continue;
                    }

                    // 키를 얻었다면 등록
                    if ('a' <= Map[nextRow][nextCol] && Map[nextRow][nextCol] <= 'f') {
                        int keyIndex = Map[nextRow][nextCol] - 'a';
                        nextKeys |= (1 << keyIndex);
                    }

                    // 이미 방문한 적이 있는 상태라면 진행 X
                    if (Visited[nextKeys][nextRow][nextCol])    continue;

                    Visited[nextKeys][nextRow][nextCol] = true;
                    queue.offer(new int[]{nextRow, nextCol, nextKeys});
                }
            }

            // 현재 레벨의 모든 노드들이 이동을 마쳤다면 +1
            moveCount++;
        }

        return -1;
    }
}