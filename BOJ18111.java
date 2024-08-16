import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ18111 {
    private static int N, M, B;
    private static int[][] Field;
    private static int MaxHeight, MinHeight;
    private static int AnswerTime, AnswerHeight;

    public static void main(String[] args) throws Exception {
        initialize();

        for (int height = MaxHeight; height >= MinHeight; height--) {
            int time = 0, blocks = B;

            for (int row = 0; row < N; row++) {
                for (int column = 0; column < M; column++) {
                    if (Field[row][column] < height) {
                        int difference = height - Field[row][column];
                        time += difference;
                        blocks -= difference;
                    }

                    else if (Field[row][column] > height) {
                        int difference = Field[row][column] - height;
                        time += difference * 2;
                        blocks += difference;
                    }
                }
            }

            if (blocks >= 0 && time < AnswerTime) {
                AnswerTime = time;
                AnswerHeight = height;
            }
        }

        System.out.println(AnswerTime + " " + AnswerHeight);
    }

    private static void initialize() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] args = br.readLine().split(" ");

        N = Integer.parseInt(args[0]);
        M = Integer.parseInt(args[1]);
        B = Integer.parseInt(args[2]);

        Field = new int[N][M];
        MaxHeight = 0;
        MinHeight = Integer.MAX_VALUE;
        AnswerTime = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            args = br.readLine().split(" ");

            for (int j = 0; j < M; j++) {
                Field[i][j] = Integer.parseInt(args[j]);
                MaxHeight = Math.max(MaxHeight, Field[i][j]);
                MinHeight = Math.min(MinHeight, Field[i][j]);
            }
        }

        br.close();
    }
}
