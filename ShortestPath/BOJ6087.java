import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ6087 {
   private static int H, W;
   private static char[][] Map;
   
   private static final int INF = 1_000_000_000;
   private static final int DIRECTION_COUNT = 5;
   private static final int[] ROW_DIRS = {0, -1, 1, 0, 0};
   private static final int[] COL_DIRS = {0, 0, 0, -1, 1};

   public static void main(String[] args) throws Exception {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       StringTokenizer st = new StringTokenizer(br.readLine());
       W = Integer.parseInt(st.nextToken());
       H = Integer.parseInt(st.nextToken());
       Map = new char[H][W];
       int[] source = null, dest = null;

       for (int i = 0; i < H; i++) {
           String line = br.readLine();
           for (int j = 0; j < W; j++) {
               Map[i][j] = line.charAt(j);
               if (Map[i][j] == 'C') {
                   if (source == null) source = new int[] {i, j};
                   else                dest = new int[] {i, j};
               }
           }
       }

       int answer = bfs(source, dest);
       System.out.println(answer);
   }

   private static int bfs(final int[] source, final int[] dest) {
       int[][][] mirrors = new int[H][W][DIRECTION_COUNT];
       for (int i = 0; i < H; i++) {
           for (int j = 0; j < W; j++) {
               Arrays.fill(mirrors[i][j], INF);
           }
       }
       
       PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[3], b[3]));
       mirrors[source[0]][source[1]][0] = 0;
       pq.add(new int[] {source[0], source[1], 0, 0});

       while (!pq.isEmpty()) {
           int[] current = pq.poll();
           int row = current[0], col = current[1];
           int direction = current[2], numOfUsedMirrors = current[3];

           if (row == dest[0] && col == dest[1]) continue;

           for (int i = 1; i < DIRECTION_COUNT; i++) {
               int nextRow = row + ROW_DIRS[i];
               int nextCol = col + COL_DIRS[i];
               int newNumOfUsedMirrors = numOfUsedMirrors + (direction == 0 || i == direction ? 0 : 1);

               // 맵 밖이거나 벽일 경우
               if (nextRow < 0 || nextRow >= H || nextCol < 0 || nextCol >= W || Map[nextRow][nextCol] == '*') continue;

               // 더 적은 개수의 거울을 사용하여 지나갈 수 있을 경우
               if (mirrors[nextRow][nextCol][i] > newNumOfUsedMirrors) {
                   mirrors[nextRow][nextCol][i] = newNumOfUsedMirrors;
                   pq.add(new int[] {nextRow, nextCol, i, newNumOfUsedMirrors});
               }
           }
       }

       int result = INF;
       for (int i = 0; i < DIRECTION_COUNT; i++)    result = Math.min(result, mirrors[dest[0]][dest[1]][i]);
       return result;
   }
}