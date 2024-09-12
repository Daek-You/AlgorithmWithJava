import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1080 {
    private static int N, M;
    private static int[][] A, B;

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        A = new int[N][M];
        B = new int[N][M];

        // A 행렬 입력 받기
        for (int i = 0; i < N; i++) {
            String row = br.readLine();
            for (int j = 0; j < M; j++) A[i][j] = Integer.parseInt(row.substring(j, j + 1));
        }

        // B 행렬 입력 받기
        for (int i = 0; i < N; i++) {
            String row = br.readLine();
            for (int j = 0; j < M; j++) B[i][j] = Integer.parseInt(row.substring(j, j + 1));
        }

        // 2. B - > A로 행렬 만들기
        int answer = 0;
        for (int row = 0; row <= N - 3; row++) {
            for (int col = 0; col <= M - 3; col++) {
                if (B[row][col] != A[row][col]) {
                    flip(row, col);
                    answer++;
                }
            }
        }

        // A랑 B랑 같아졌는지 확인
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < M; col++) {
                if (A[row][col] != B[row][col]) {
                    answer = -1;
                    break;
                }
            }
        }

        System.out.println(answer);
        br.close();
    }

    private static void flip(int row, int col) {
        for (int r = row; r < row + 3; r++) {
            for (int c = col; c < col + 3; c++) {
                B[r][c] = B[r][c] == 1 ? 0 : 1;
            }
        }
    }
}