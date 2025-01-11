import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BOJ11967 {
    private static int N;
    private static boolean[][] Light;
    private static ArrayList<int[]>[] Switches;
    private static final int[] ROW_DIRS = {-1, 1, 0, 0};
    private static final int[] COL_DIRS = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        Light = new boolean[N][N];
        Switches = new ArrayList[N * N];
        for (int i = 0; i < N * N; i++) Switches[i] = new ArrayList<>();

        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int fromRow = Integer.parseInt(st.nextToken()) - 1, fromCol = Integer.parseInt(st.nextToken()) - 1;
            int toRow = Integer.parseInt(st.nextToken()) - 1, toCol = Integer.parseInt(st.nextToken()) - 1;
            int index = convertToIndex(fromRow, fromCol);
            Switches[index].add(new int[] {toRow, toCol});
        }

        // 2. BFS
        Light[0][0] = true;
        while (true) {
            LinkedList<int[]> queue = new LinkedList<>();
            boolean[][] visited = new boolean[N][N];
            queue.add(new int[] {0, 0});
            visited[0][0] = true;

            boolean hasNewLight = false;
            while (!queue.isEmpty()) {
                int[] currentPosition = queue.poll();
                int index = convertToIndex(currentPosition[0], currentPosition[1]);

                // 불부터 켜기
                for (int[] canOnLightPosition : Switches[index]) {
                    int row = canOnLightPosition[0], col = canOnLightPosition[1];

                    // 이미 불을 켜 놓은 곳은 skip
                    if (Light[row][col])    continue;
                    hasNewLight = true;
                    Light[row][col] = true;
                }

                // 현재 위치에서 주변에 불 켜진 방이 있다면 이동
                for (int i = 0; i < 4; i++) {
                    int nextRow = currentPosition[0] + ROW_DIRS[i];
                    int nextCol = currentPosition[1] + COL_DIRS[i];

                    if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= N || visited[nextRow][nextCol] || !Light[nextRow][nextCol])    continue;
                    visited[nextRow][nextCol] = true;
                    queue.add(new int[] {nextRow, nextCol});
                }
            }

            // 더 이상 불을 켤 곳이 없으면 중단
            if (!hasNewLight) break;
        }

        // 불이 켜진 방 세기
        int answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                answer += Light[i][j] ? 1 : 0;
            }
        }

        System.out.println(answer);
        br.close();
    }

    private static int convertToIndex(int row, int col) {
        return row * N + col;
    }
}