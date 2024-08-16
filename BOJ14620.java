import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ14620 {
    private static int N, Answer;
    private static int[][] Ground;
    private static int[] RowDirections      = {0, -1, 1, 0, 0};
    private static int[] ColumnDirections   = {0, 0, 0, -1, 1};

    private static final int DIRECTION_COUNT = 5;
    private static final int MAX_FLOWER_COUNT = 3;
    private static final int FLOWER = -1;

    public static void main(String[] args) throws Exception {
        initialize();
        DFS(0, 0);

        System.out.println(Answer);
    }

    private static void initialize() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        Ground = new int[N][N];
        Answer = Integer.MAX_VALUE;

        for (int row = 0; row < N; row++) {
            String[] args = br.readLine().split(" ");

            for (int column = 0; column < N; column++){
                Ground[row][column] = Integer.parseInt(args[column]);
            }
        }

        br.close();
    }

    private static void DFS(int numOfFlowers, int cost) {
        if (numOfFlowers == MAX_FLOWER_COUNT) {
            Answer = Math.min(Answer, cost);
            return;
        }

        for (int row = 1; row < N - 1; row++) {
            for (int column = 1; column < N - 1; column++) {
                if (hasNearlyFlower(row, column))
                    continue;

                int sum = 0;
                int[] originValues = new int[5];

                // 주변 땅 임대해서 꽃 심기
                for (int i = 0; i < DIRECTION_COUNT; i++) {
                    int nextRow = row + RowDirections[i];
                    int nextColumn = column + ColumnDirections[i];
                    
                    sum += Ground[nextRow][nextColumn];
                    originValues[i] = Ground[nextRow][nextColumn];
                    Ground[nextRow][nextColumn] = FLOWER;
                }

                DFS(numOfFlowers + 1, cost + sum);

                // 되돌리기
                for (int i = 0; i < DIRECTION_COUNT; i++) {
                    int nextRow = row + RowDirections[i];
                    int nextColumn = column + ColumnDirections[i];
                    Ground[nextRow][nextColumn] = originValues[i];
                }
            }
        }
    }

    private static boolean hasNearlyFlower(int startRow, int startColumn) {
        for (int i = 0; i < DIRECTION_COUNT; i++) {
            int nextRow = startRow + RowDirections[i];
            int nextColumn = startColumn + ColumnDirections[i];
            
            if (Ground[nextRow][nextColumn] == FLOWER) {
                return true;
            }
        }

        return false;
    }
}