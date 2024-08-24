import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BOJ2468 {

    private static final int DIRECTION_COUNT = 4;
    private static final int WATER       = -1;
    private static final int UNVISITIED  = -2;
    private static final int VISITED     = -3;

    private static int N, Answer;
    private static int MaxAreaHeight;

    private static int[][] Map;
    private static int[][] Visited;
    private static int[] RowDirections       = {-1, 1, 0, 0};
    private static int[] ColumnDirections    = {0, 0, -1, 1};
    private static Queue<Point> Areas;

    public static void main(String[] args) throws Exception {
        initialize();

        for (int height = 0; height <= MaxAreaHeight; height++) {
            fill(Visited, UNVISITIED);
            flood(height);

            int numOfSafeAreas = findSafeAreaCount();
            Answer = Math.max(Answer, numOfSafeAreas);
        }

        System.out.println(Answer);
    }

    // height 이하 높이 모두 침수시키기
    private static void flood(int height) {
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                if (Map[row][column] <= height) {
                    Visited[row][column] = WATER;
                }
            }
        }
    }

    private static int findSafeAreaCount() {
        int count = 0;

        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                if (Visited[row][column] == UNVISITIED) {
                    bfs(row, column);
                    count++;
                }
            }
        }

        return count;
    }

    private static void bfs(int row, int column) {
        Areas.clear();
        Areas.add(new Point(row, column));
        Visited[row][column] = VISITED;

        while (!Areas.isEmpty()) {
            Point current = Areas.poll();

            for (int i = 0; i < DIRECTION_COUNT; i++) {
                int nextRow = current.row + RowDirections[i];
                int nextColumn = current.column + ColumnDirections[i];

                if (nextRow < 0 || nextRow >= N || nextColumn < 0 || nextColumn >= N) {
                    continue;
                }

                if (Visited[nextRow][nextColumn] == WATER || Visited[nextRow][nextColumn] == VISITED) {
                    continue;
                }

                Visited[nextRow][nextColumn] = VISITED;
                Areas.add(new Point(nextRow, nextColumn));
            }
        }
    }

    private static void initialize() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        Map = new int[N][N];
        Areas = new LinkedList<>();
        Visited = new int[N][N];

        MaxAreaHeight = Integer.MIN_VALUE;

        for (int row = 0; row < N; row++) {
            String[] args = br.readLine().split(" ");

            for (int column = 0; column < N; column++) {
                Map[row][column] = Integer.parseInt(args[column]);
                MaxAreaHeight = Math.max(MaxAreaHeight, Map[row][column]);
            }
        }

        br.close();
    }
    
    private static void fill(int[][] arr, int value) {
        for (int[] elements : arr) {
            Arrays.fill(elements, value);
        }
    }
}

class Point {
    Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

    int row;
    int column;
}
