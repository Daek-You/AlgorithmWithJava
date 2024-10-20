import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ14712 {
    static int N, M, res;
    static boolean[][] board;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new boolean[N][M];

        dfs(0, 0);
        System.out.println(res);
        br.close();
    }

    private static void dfs(int row, int col) {
        // 마지막 열을 넘어선다면 범위 조정 후 로직 진행
        if (col == M) {
            row++;
            col = 0;
        }

        // 마지막 행을 넘어섰다면 가능한 넴모 배치 조합으로 간주
        if (row == N) {
            res++;
            return;
        }

        // 현재 위치에 넴모를 배치하지 않고 이동
        dfs(row, col + 1);

        // 현재 위치에 넴모를 배치할 수 있는지 검사 후, 놓을 수 있으면 놓고 다음으로 이동
        if (canPlace(row, col)) {
            board[row][col] = true;
            dfs(row, col + 1);
            board[row][col] = false;
        }
    }

    private static boolean canPlace(int row, int col) {
        if (row > 0 && col > 0 && board[row - 1][col] && board[row][col - 1] && board[row - 1][col - 1]) {
            return false;
        }

        return true;
    }

}