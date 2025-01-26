import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ18428 {
    private static int N;
    private static char[][] Map;

    private static ArrayList<int[]> Teachers = new ArrayList<>();
    private static String Answer = "NO";

    private static final int[] ROW_DIRS = {-1, 1, 0, 0};
    private static final int[] COL_DIRS = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        Map = new char[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                Map[i][j] = st.nextToken().charAt(0);
                if (Map[i][j] == 'T')       Teachers.add(new int[] {i, j});
            }
        }

        // 2. backtracking
        dfs(0, 0, 0);
        System.out.println(Answer);
    }

    private static void dfs(int row, int col, int numOfObstacles) {
        if (numOfObstacles == 3) {
            if (isAllHide())    Answer = "YES";
            return;
        }

        row += (col / N);
        col %= N;

        if (row == N || Answer == "YES")    return;

        if (Map[row][col] == 'X') {
            Map[row][col] = 'O';
            dfs(row, col + 1, numOfObstacles + 1);
            Map[row][col] = 'X';
        }

        dfs(row, col + 1, numOfObstacles);
    }

    private static boolean isAllHide() {
        for (int[] position : Teachers) {
            for (int d = 0; d < 4; d++) {
                int row = position[0], col = position[1];
                
                while (isValid(row, col) && Map[row][col] != 'O') {
                    if (Map[row][col] == 'S') return false;
                    row += ROW_DIRS[d];
                    col += COL_DIRS[d];
                }
            }
        }

        return true;
    }

    private static boolean isValid(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }
}
