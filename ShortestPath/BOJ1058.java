import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ1058 {
    private static final int INF = 1_000_000_000;
    private static int N;
    private static int[][] Map;

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        Map = new int[N][N];

        for (int r = 0; r < N; r++) {
            String row = br.readLine();
            for (int c = 0; c < N; c++) Map[r][c] = (row.charAt(c) == 'Y') ? 1 : INF;
        }

        // 2. 플로이드 워셜로 최댓값 구하기
        floydWarshall();
        int answer = 0;

        for (int from = 0; from < N; from++) {
            int numOfFriends = 0;

            for (int to = 0; to < N; to++) {
                if (Map[from][to] <= 2) {
                    numOfFriends++;
                }
            }

            answer = Math.max(answer, numOfFriends);
        }

        System.out.println(answer);
        br.close();
    }

    public static void floydWarshall() {
        for (int mid = 0; mid < N; mid++) {
            for (int from = 0; from < N; from++) {
                for (int to = 0; to < N; to++) {
                    if (from != to) {
                        Map[from][to] = Math.min(Map[from][to], Map[from][mid] + Map[mid][to]);
                    }
                }
            }
        }
    }
}
