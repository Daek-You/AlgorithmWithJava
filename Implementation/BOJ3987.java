import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ3987 {
    private static int N, M;
    private static char[][] Map;
    private static final char EMPTY = '.';
    private static final char BLACK_HOLE = 'C';

    private static final char[] DIRECTIONS = {'U', 'R', 'D', 'L'};
    private static int[][][] ReflectionDirections = {
        { {0, -1}, {1, 0}, {0, 1}, {-1, 0}},    // 상, 우, 하, 좌 방향으로 진행 중일 때 '\'을 만났을 때의 반사 방향들
        { {0, 1}, {-1, 0}, {0, -1}, {1, 0}}     // 상, 우, 하, 좌 방향으로 진행 중일 때 '/'을 만났을 때의 반사 방향들
    };

    private static final int[] ROW_DIRS = {-1, 0, 1, 0};
    private static final int[] COL_DIRS = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        // N x M
        // 행성('/', '\'), 블랙홀, 빈칸
        // 인접한 네 칸 중 하나를 골라 시그널 보내 시그널 보내 찌릿찌릿 찌릿찌릿
        // 시그널 항상 일직선 but, 행성 만난 경우에는 90도 방향 회전
        // 블랙홀 or 맵 밖을 벗어날 때까지 계속 직진
        // 인접한 칸으로 이동하는 데 걸리는 시간은 1초
        // 탐사선이 어느 방향으로 시그널을 보내면, 시그널이 항성계 내부에 있는 시간이 최대가 되는지 구하기
        // 무한 루프가 도는 경우는 어떻게 탐지할 것인가?  ->  첫 시작 행성을 저장해두고, 돌다가 이리로 돌아오면 무한 루프

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Map = new char[N][];

        for (int row = 0; row < N; row++)   Map[row] = br.readLine().toCharArray();
        st = new StringTokenizer(br.readLine());
        int startRow = Integer.parseInt(st.nextToken()) - 1, startCol = Integer.parseInt(st.nextToken()) - 1;

        // 탐사선 위치에서 4방향으로 전파 쏘기
        int directionIdx = Integer.MAX_VALUE, time = 0;
        for (int d = 0; d < 4; d++) {
            int result = startSimulation(startRow, startCol, d);

            if (result == -1) {
                System.out.println(DIRECTIONS[d]);
                System.out.println("Voyager");
                return;
            }

            if (result > time) {
                time = result;
                directionIdx = d;
            } else if (result == time && d < directionIdx) {
                directionIdx = d;
            }
        }

        System.out.println(DIRECTIONS[directionIdx]);
        System.out.println(time);
    }

    private static int startSimulation(int row, int col, int dir) {
        int time = 0;
        int firstPlanetRow = -1, firstPlanetCol = -1, firstPlanetDir = -1;

        while (isValidRange(row, col) && Map[row][col] != BLACK_HOLE) {
            // 행성을 만난 경우
            if (Map[row][col] != EMPTY) {
                // 처음 만난 행성을 또 방문하는 경우 -> 무한 사이클
                if (row == firstPlanetRow && col == firstPlanetCol && firstPlanetDir == dir) {
                    return -1; 
                }

                // 첫 행성을 만난 경우
                if (firstPlanetRow == -1 && firstPlanetCol == -1 && firstPlanetDir == -1) {
                    firstPlanetRow = row;
                    firstPlanetCol = col;
                    firstPlanetDir = dir;
                }

                // 해당 행성의 반사 타입 알아내서 회전
                int type = Map[row][col] == '/' ? 1 : 0;
                int[] nextDirs = ReflectionDirections[type][dir];
                dir = getDirectionIndex(nextDirs);
            }

            row += ROW_DIRS[dir];
            col += COL_DIRS[dir];
            time++;
        }

        return time;
    }

    private static boolean isValidRange(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }

    private static int getDirectionIndex(int[] dir) {
        for (int i = 0; i < 4; i++) {
            if (dir[0] == ROW_DIRS[i] && dir[1] == COL_DIRS[i]) {
                return i;
            }
        }

        return -1;
    }
}
