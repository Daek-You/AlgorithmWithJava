import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ1405 {
    private static final int DIRECTION_COUNT = 4;
    private static int N;
    private static double Answer;

    private static int[] RowDirections       = {0, 0, 1, -1};
    private static int[] ColumnDirections    = {1, -1, 0, 0};
    private static boolean[][] Visited;
    private static double[] Probabilities;

    public static void main(String[] args) throws Exception {
        initialize();

        int originRow = N, originColumn = N;
        Visited[originRow][originColumn] = true;

        startSimulation(originRow, originColumn, 0, 1);
        System.out.println(Answer);
    }

    private static void startSimulation(int row, int column, int depth, double currentProbability) {
        if (depth == N) {
            Answer += currentProbability;
            return;
        }

        for (int i = 0; i < DIRECTION_COUNT; i++) {
            int nextRow = row + RowDirections[i];
            int nextColumn = column + ColumnDirections[i];

            if (!Visited[nextRow][nextColumn]) {
                Visited[nextRow][nextColumn] = true;
                startSimulation(nextRow, nextColumn, depth + 1, currentProbability * Probabilities[i]);
                Visited[nextRow][nextColumn] = false;
            }
        }
    }

    private static void initialize() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] args = br.readLine().split(" ");

        N = Integer.parseInt(args[0]);
        // 홀수든 짝수든 x2를 하면 짝수가 됨 -> 짝수는 중앙을 기준으로 상하좌우 대칭이 안 맞으므로 +1씩
        Visited = new boolean[N * 2 + 1][N * 2 + 1];

        // 동서남북 확률값 입력 받기
        Probabilities = new double[DIRECTION_COUNT];
        for (int i = 1; i <= DIRECTION_COUNT; i++) {
            Probabilities[i - 1] = Double.parseDouble(args[i]) * 0.01;
        }

        br.close();
    }
}