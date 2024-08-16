import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class BOJ1926 {
    private static final int NON_COLORED = 0;
    private static final int COLORED = 1;
    private static final int VISITED = 2;

    private static int MaxRow, MaxColumn;
    private static int NumOfDrawings = 0;
    private static int MaxArea = 0;
    
    private static int[][] Paper;
    private static final int DIRECTION_COUNT = 4;
    private static final int[] RowDirections = {-1, 1, 0, 0};
    private static final int[] ColumnDirections = {0, 0, -1, 1};
    
    public static void main(String[] args) throws Exception {
        initialize();

        for (int row = 0; row < MaxRow; row++) {
            for (int column = 0; column < MaxColumn; column++) {
                if (Paper[row][column] == COLORED) {
                    MaxArea = Math.max(MaxArea, bfs(row, column));
                    NumOfDrawings++;
                }
            }
        }

        System.out.println(NumOfDrawings);
        System.out.println(MaxArea);
    }

    private static int bfs(int row, int column) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(row, column));
        Paper[row][column] = VISITED;

        int area = 1;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            for (int i = 0; i < DIRECTION_COUNT; i++) {
                int nextRow = current.row + RowDirections[i];
                int nextColumn = current.column + ColumnDirections[i];

                if (nextRow < 0 || nextRow >= MaxRow || nextColumn < 0 || nextColumn >= MaxColumn)
                    continue;

                if (Paper[nextRow][nextColumn] == NON_COLORED || Paper[nextRow][nextColumn] == VISITED)
                    continue;

                Paper[nextRow][nextColumn] = VISITED;
                queue.add(new Point(nextRow, nextColumn));
                area++;
            }
        }

        return area;
    }

    private static void initialize() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] args = br.readLine().split(" ");
        
        MaxRow = Integer.parseInt(args[0]);
        MaxColumn = Integer.parseInt(args[1]);
        Paper = new int[MaxRow][MaxColumn];

        for (int i = 0; i < MaxRow; i++) {
            args = br.readLine().split(" ");

            for (int j = 0; j < MaxColumn; j++) {
                Paper[i][j] = Integer.parseInt(args[j]);
            }
        }
        
        br.close();
    }
}

class Point {
    public Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

    int row, column;
}