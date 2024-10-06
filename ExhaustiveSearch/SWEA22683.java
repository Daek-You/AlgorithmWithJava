import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA22683 {
    private static int N, K;
    private static char[][] Map;
    private static boolean[][][][] Visited;

    // 상, 우, 하, 좌
    private static final int DIRECTION_COUNT = 4;
    private static final int[] ROW_DIRS = {-1, 0, 1, 0};
    private static final int[] COL_DIRS = {0, 1, 0, -1};

    private static class State {
        int row, col, direction, cutCount, operationCount;

        public State(int row, int col, int direction, int cutCount, int operationCount) {
            this.row = row;
            this.col = col;
            this.direction = direction;
            this.cutCount = cutCount;
            this.operationCount = operationCount;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= T; testCase++) {

            // 1. 입력 받기
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            Map = new char[N][N];
            Visited = new boolean[DIRECTION_COUNT][K+1][N][N];      // [어느 방향으로][나무 몇 개를 자른 체][행][열]      

            int startRow = -1, startCol = -1;
            int destRow = -1, destCol = -1;
            for (int row = 0; row < N; row++) {
                String line = br.readLine();

                for (int col = 0; col < N; col++) {
                    Map[row][col] = line.charAt(col);

                    if (Map[row][col] == 'X') {         // 출발지일 경우
                        startRow = row;
                        startCol = col;
                    } else if (Map[row][col] == 'Y') {  // 도착지일 경우
                        destRow = row;
                        destCol = col;
                    }
                }
            }

            // 2. bfs 수행
            int answer = bfs(startRow, startCol, destRow, destCol);
            System.out.printf("#%d %d\n", testCase, answer);
        }

        br.close();
    }

    private static int bfs(int startRow, int startCol, int destRow, int destCol) {
        Queue<State> queue = new LinkedList<>();
        queue.add(new State(startRow, startCol, 0, 0, 0));      // 초기 방향은 위(0)
        Visited[0][0][startRow][startCol] = true;

        while (!queue.isEmpty()) {
            State current = queue.poll();
            int row = current.row, col = current.col;
            int direction = current.direction, cutCount = current.cutCount;
            int operationCount = current.operationCount;

            // 목적지에 도착했다면 조작 횟수 리턴
            if (row == destRow && col == destCol) {
                return operationCount;
            }

            // 왼쪽 회전
            int newDirLeft = (direction + 3) % 4;
            if (!Visited[newDirLeft][cutCount][row][col]) {
                Visited[newDirLeft][cutCount][row][col] = true;
                queue.add(new State(row, col, newDirLeft, cutCount, operationCount + 1));
            }

            // 오른쪽 회전
            int newDirRight = (direction + 1) % 4;
            if (!Visited[newDirRight][cutCount][row][col]) {
                Visited[newDirRight][cutCount][row][col] = true;
                queue.add(new State(row, col, newDirRight, cutCount, operationCount + 1));
            }

            // 정면 방향(전진)
            int nextRow = row + ROW_DIRS[direction];
            int nextCol = col + COL_DIRS[direction];

            // 맵 밖이면 진입 X
            if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= N) continue;
            
            // 나무를 만났다면
            if (Map[nextRow][nextCol] == 'T') {
                if (cutCount >= K) continue;        // 이미 나무를 K번 잘랐다면 더 이상 X
                
                if (!Visited[direction][cutCount + 1][nextRow][nextCol]) {
                    Visited[direction][cutCount + 1][nextRow][nextCol] = true;
                    queue.add(new State(nextRow, nextCol, direction, cutCount + 1, operationCount + 1));
                }
            } else {
                if (!Visited[direction][cutCount][nextRow][nextCol]) {
                    Visited[direction][cutCount][nextRow][nextCol] = true;
                    queue.add(new State(nextRow, nextCol, direction, cutCount, operationCount + 1));
                }
            }
        }

        return -1;
    }
}