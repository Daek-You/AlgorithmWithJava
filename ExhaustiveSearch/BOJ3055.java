import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ3055 {
    private static class Route {
        int row, col;
        boolean isWater;

        public Route(int row, int col, boolean isWater) {
            this.row = row;
            this.col = col;
            this.isWater = isWater;
        }
    }

    private static final char WATER = '*';
    private static final char STONE = 'X';
    private static final char DESTINATION = 'D';
    private static final int DIRECTION_COUNT = 4;
    private static final int[] ROW_DIRS = {-1, 1, 0, 0};
    private static final int[] COL_DIRS = {0, 0, -1, 1};

    private static int R, C;
    private static char[][] Map;
    private static boolean[][] Visited;
    private static Queue<Route> _Queue = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        
        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        Map = new char[R][C];
        Visited = new boolean[R][C];

        int startRow = -1, startCol = -1;
        for (int row = 0; row < R; row++) {
            String line = br.readLine();

            for (int col = 0; col < C; col++) {
                Map[row][col] = line.charAt(col);
                
                if (Map[row][col] == 'S') {
                    startRow = row;
                    startCol = col;
                } else if (Map[row][col] == WATER) {
                    // 물이 먼저 차오를 수 있게 큐에 미리 삽입
                    _Queue.add(new Route(row, col, true));
                } 
            }
        }
        br.close();

        // 2. BFS 시작
        int answer = bfs(startRow, startCol);
        if (answer == -1)   System.out.println("KAKTUS");
        else                System.out.println(answer);
    }

    private static int bfs(int startRow, int startCol) {
        _Queue.add(new Route(startRow, startCol, false));
        Visited[startRow][startCol] = true;
        int moveTime = 0;

        while (!_Queue.isEmpty()) {
            int size = _Queue.size();

            for (int i = 0; i < size; i++) {
                Route current = _Queue.poll();
                int row = current.row, col = current.col;
                boolean isWater = current.isWater;

                // 목적지에 도착했다면
                if (Map[row][col] == DESTINATION) {
                    return moveTime;
                }

                for (int d = 0; d < DIRECTION_COUNT; d++) {
                    int nextRow = row + ROW_DIRS[d];
                    int nextCol = col + COL_DIRS[d];

                    // 맵 밖이거나 돌이거나 물일 경우 진행 x
                    if (nextRow < 0 || nextRow >= R || nextCol < 0 || nextCol >= C || Map[nextRow][nextCol] == STONE || Map[nextRow][nextCol] == WATER) continue;

                    // 물은 비버의 굴로 이동 불가
                    if (isWater && Map[nextRow][nextCol] == DESTINATION)    continue;

                    // 고슴도치가 이미 방문했던 곳은 skip
                    if (!isWater && Visited[nextRow][nextCol])  continue;

                    // 물인지 고슴도치에 따라 분기 처리
                    if (isWater) {
                        Map[nextRow][nextCol] = WATER;
                    } else {
                        Visited[nextRow][nextCol] = true;
                    }

                    _Queue.add(new Route(nextRow, nextCol, isWater));
                }
            }

            moveTime++;
        }

        return -1;
    }
}