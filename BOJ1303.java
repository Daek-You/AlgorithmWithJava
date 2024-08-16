import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class BOJ1303 {
    private static int MaxRow, MaxColumn;
    private static char[][] Field;

    private static final char MY_TEAM = 'W';
    private static final char ENEMY_TEAM = 'B';
    private static final char VISITED = 'S';
    private static final int DIRECTION_COUNT = 4;
    private static final int[] RowDirections = {-1, 1, 0, 0};
    private static final int[] ColumnDirections = {0, 0, -1, 1};

    private static int MyStatus = 0;
    private static int EnemyStatus = 0;

    public static void main(String[] args) throws Exception {
        initialize();

        for (int row = 0; row < MaxRow; row++) {
            for (int column = 0; column < MaxColumn; column++) {
                if (Field[row][column] == VISITED)
                    continue;
                
                if (Field[row][column] == MY_TEAM) {
                    MyStatus += bfs(row, column, MY_TEAM);
                } else {
                    EnemyStatus += bfs(row, column, ENEMY_TEAM);
                }
                
            }
        }

        System.out.println(MyStatus);
        System.out.println(EnemyStatus);
    }

    private static int bfs(int row, int column, char color) {
        Queue<Knight> queue = new LinkedList<>();
        queue.add(new Knight(row, column));
        Field[row][column] = VISITED;

        int numOfKnights = 1;

        while(!queue.isEmpty()) {
            Knight knight = queue.poll();

            for (int i = 0; i < DIRECTION_COUNT; i++) {
                int nextRow = knight.row + RowDirections[i];
                int nextColumn = knight.column + ColumnDirections[i];

                if (nextRow < 0 || nextRow >= MaxRow || nextColumn < 0 || nextColumn >= MaxColumn)
                    continue;
                
                if (Field[nextRow][nextColumn] == VISITED || Field[nextRow][nextColumn] != color)
                    continue;
                
                Field[nextRow][nextColumn] = VISITED;
                queue.add(new Knight(nextRow, nextColumn));
                numOfKnights++;                
            }
        }

        return numOfKnights * numOfKnights;
    }

    private static void initialize() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] args = br.readLine().split(" ");

        MaxColumn = Integer.parseInt(args[0]);
        MaxRow = Integer.parseInt(args[1]);
        Field = new char[MaxRow][MaxColumn];

        for (int i = 0; i < MaxRow; i++) {
            String row = br.readLine();;

            for (int j = 0; j < MaxColumn; j++) {
                Field[i][j] = row.charAt(j);
            }
        }

        br.close();
    }
}

class Knight {
    public Knight(int row, int column) {
        this.row = row;
        this.column = column;
    }

    int row, column;
}