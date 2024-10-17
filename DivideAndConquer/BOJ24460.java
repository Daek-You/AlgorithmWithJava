import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ24460 {
    private static int N;
    private static int[][] Map;

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        Map = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) Map[i][j] = Integer.parseInt(st.nextToken());
        }
        br.close();

        // 2. 분할 정복
        // 상 받는 사람이 한 명뿐이라면 그 사람이 받기
        if (N == 1) {
            System.out.println(Map[0][0]);
            return;
        }

        int answer = divide(N, 0, 0);
        System.out.println(answer);
    }

    private static int divide(int size, int startRow, int startCol) {
        if (size == 2) {
            int[] arr = new int[] { Map[startRow][startCol], Map[startRow + 1][startCol], Map[startRow][startCol + 1], Map[startRow + 1][startCol + 1] };
            int secondNum = getSecondMaxNum(arr);
            return secondNum;
        }

        int halfSize = size / 2;
        int[] arr = new int[] {
            divide(halfSize, startRow, startCol),                   // 왼쪽 위
            divide(halfSize, startRow, startCol + halfSize),        // 오른쪽 위
            divide(halfSize, startRow + halfSize, startCol),            // 왼쪽 아래
            divide(halfSize, startRow + halfSize, startCol + halfSize)      // 오른쪽 아래
        };

        return getSecondMaxNum(arr);
    }

    private static int getSecondMaxNum(int[] arr) {
        Arrays.sort(arr);
        return arr[1];
    }
}