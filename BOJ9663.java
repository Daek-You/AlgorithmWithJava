import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ9663 {
    private static int N;
    private static boolean[][] Visited;

    public static void main(String[] args) throws Exception {
        initialize(new BufferedReader(new InputStreamReader(System.in)));

        int answer = dfs(0);
        System.out.println(answer);
    }

    private static void initialize(BufferedReader br) throws Exception {
        N = Integer.parseInt(br.readLine());
        Visited = new boolean[N][N];
    }

    private static int dfs(int row) {
        if (row == N)
            return 1;

        int numOfPossiblePlacement = 0;

        for (int column = 0; column < N; column++) {
            if (isPossible(row, column)) {
                Visited[row][column] = true;
                numOfPossiblePlacement += dfs(row + 1);
                Visited[row][column] = false;
            }
        }

        return numOfPossiblePlacement;
    }

    private static boolean isPossible(int startRow, int startColumn) {
        // 1. 윗 쪽 탐색
        for (int row = startRow - 1; row >= 0; row--)
            if (Visited[row][startColumn])
                return false;

        // 2. 대각선 왼쪽 위 탐색
        for (int row = startRow - 1, column = startColumn - 1; row >= 0 && column >= 0; row--, column--)
            if (Visited[row][column])
                return false;

        // 3. 대각선 오른쪽 위 탐색
        for (int row = startRow - 1, column = startColumn + 1; row >= 0 && column < N; row--, column++)
            if (Visited[row][column])
                return false;

        return true;
    }
}
