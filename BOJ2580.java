import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BOJ2580 {
    private static final int N = 9;
    private static final int BLANK = 0;
    private static boolean FoundSolution = false;

    private static int[][] Board;
    private static boolean[] Visited;
    private static List<Point> BlankPositions;

    public static void main(String[] args) throws Exception {
        initialize(new BufferedReader(new InputStreamReader(System.in)));
        findCompleteSudoku(0);
    }

    private static void findCompleteSudoku(int blankIndex) {
        if (blankIndex >= BlankPositions.size()) {
            print();
            FoundSolution = true;
            return;
        }

        Point current = BlankPositions.get(blankIndex);

        for (int value = 1; value <= N; value++) {
            Board[current.row][current.column] = value;

            if (isPossible(current.row, current.column)) {
                findCompleteSudoku(blankIndex + 1);

                if (FoundSolution)
                    return;
            }

            Board[current.row][current.column] = BLANK;
        }
    }

    private static boolean isPossible(int row, int column) {
        // 1. 행(Row) 검사
        resetVisitedArray(false);
        for (int i = 0; i < N; i++) {
            int value = Board[i][column];

            if (value != BLANK) {
                if (Visited[value])
                    return false;
                Visited[value] = true;
            }
        }

        // 2. 열(Column) 검사
        resetVisitedArray(false);
        for (int i = 0; i < N; i++) {
            int value = Board[row][i];

            if (value != BLANK) {
                if (Visited[value])
                    return false;
                Visited[value] = true;
            }
        }

        // 3. 현재 속한 3x3 영역 검사
        resetVisitedArray(false);
        int startRow = 3 * (row / 3), startColumn = 3 * (column / 3);

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startColumn; c < startColumn + 3; c++) {
                int value = Board[r][c];

                if (value != BLANK) {
                    if (Visited[value])
                        return false;
                    Visited[value] = true;
                }
            }
        }

        return true;
    }

    private static void initialize(BufferedReader br) throws Exception {
        Board = new int[N][N];
        BlankPositions = new ArrayList<>();
        Visited = new boolean[N + 1];

        for (int row = 0; row < N; row++) {
            String[] line = br.readLine().split(" ");

            for (int column = 0; column < N; column++) {
                Board[row][column] = Integer.parseInt(line[column]);

                if (Board[row][column] == BLANK)
                    BlankPositions.add(new Point(row, column));
            }
        }
    }

    private static void resetVisitedArray(boolean value) {
        for (int i = 1; i <= N; i++)
            Visited[i] = value;
    }

    private static void print() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                sb.append(Board[row][column]).append(" ");
            }

            System.out.println(sb.toString());
            sb.setLength(0);  // == clear()
        }
    }
}

class Point {
    Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

    int row, column;
}