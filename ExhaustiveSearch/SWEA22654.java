import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA22654 {
    // 상, 우, 하, 좌
    private static final int DIRECTION_COUNT = 4;
    private static final int[] ROW_DIRS = {-1, 0, 1, 0};
    private static final int[] COL_DIRS = {0, 1, 0, -1};

    private static int N;
    private static char[][] Map;
    private static int CurrentRow, CurrentCol, CurrentDirection;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= T; testCase++) {

            // 1. 입력 받기
            N = Integer.parseInt(br.readLine());
            Map = new char[N][N];

            int startRow = -1, startCol = -1;
            int destRow = -1, destCol = -1;
            for (int row = 0; row < N; row++) {
                String line = br.readLine();

                for (int col = 0; col < N; col++) {
                    Map[row][col] = line.charAt(col);

                    if (Map[row][col] == 'X') {         // 시작 지점인 경우
                        startRow = row;
                        startCol = col;
                    } else if (Map[row][col] == 'Y') {  // 도착 지점인 경우
                        destRow = row;
                        destCol = col;
                    }
                }
            }

            // 2. 커맨드 입력
            int Q = Integer.parseInt(br.readLine());
            StringBuilder sb = new StringBuilder();

            while (Q-- > 0) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int C = Integer.parseInt(st.nextToken());
                String commands = st.nextToken();

                CurrentRow = startRow;
                CurrentCol = startCol;
                CurrentDirection = 0;       // 초기에는 윗 방향을 보고 있다고 했음

                // 커맨드에 따른 기능 수행
                for (int i = 0; i < C; i++) {
                    char command = commands.charAt(i);
                    execute(command);
                }

                // 커맨드가 모두 끝난 후 목적지 도달 여부 저장
                int result = (CurrentRow == destRow && CurrentCol == destCol) ? 1 : 0;
                sb.append(result).append(" ");
            }

            System.out.printf("#%d %s\n", testCase, sb.toString());
        }

        br.close();
    }

    private static void execute(char command) {
        switch (command) {
            case 'A': {         // 전진
                int nextRow = CurrentRow + ROW_DIRS[CurrentDirection];
                int nextCol = CurrentCol + COL_DIRS[CurrentDirection];

                // 맵 밖이거나 나무가 있다면 진입 x
                if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= N || Map[nextRow][nextCol] == 'T') return;
                
                CurrentRow = nextRow;
                CurrentCol = nextCol;
                break;
            }

            case 'L': { // 왼쪽으로 90도 회전
                CurrentDirection = (CurrentDirection + 3) % DIRECTION_COUNT;
                break;
            }

            case 'R': { // 오른쪽으로 90도 회전
                CurrentDirection = (CurrentDirection + 1) % DIRECTION_COUNT;
                break;
            }
        }
    }
}