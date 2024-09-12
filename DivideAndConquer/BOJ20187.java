import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class BOJ20187 {
    private static int H;
    private static char[] Commands;
    private static int[][] Map;
    private static HashMap<Integer, Integer> Horizontals = new HashMap<>();
    private static HashMap<Integer, Integer> Verticals = new HashMap<>();

    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int K = Integer.parseInt(br.readLine());
        int length = (int)Math.pow(2, K);

        StringTokenizer st = new StringTokenizer(br.readLine());
        int size = st.countTokens();
        Commands = new char[size];
        for (int i = 0; i < size; i++)  Commands[i] = st.nextToken().charAt(0);

        H = Integer.parseInt(br.readLine());
        Map = new int[length][length];

        // 좌우 대칭값 저장
        Horizontals.put(0, 1);
        Horizontals.put(1, 0);
        Horizontals.put(2, 3);
        Horizontals.put(3, 2);

        // 상하 대칭값 저장
        Verticals.put(0, 2);
        Verticals.put(2, 0);
        Verticals.put(1, 3);
        Verticals.put(3, 1);

        // 분할 정복 시작
        divide(0, 0, 0, length, length);

        // 정답 출력
        StringBuilder sb = new StringBuilder();
        for (int[] row : Map) {
            for (int element : row) {
                sb.append(element).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }

    public static void divide(int row, int col, int idx, int horizontalLen, int verticalLen) {
        if (horizontalLen == 1 && verticalLen == 1) {
            Map[row][col] = H;
            return;
        }

        int halfHorizontalLen = horizontalLen / 2;
        int halfVerticalLen = verticalLen / 2;
        int offset = 1;

        switch (Commands[idx]) {
            case 'U': {
                // 위로 접으면 시작 지점은 바뀌지 않음
                divide(row, col, idx + 1, horizontalLen, halfVerticalLen);

                // 접었던 아래 부분은 윗 부분을 그대로 대칭하여 복사하면 됨
                int startRow = row + halfVerticalLen;
                for (int r = startRow; r < startRow + halfVerticalLen; r++) {
                    for (int c = col; c < col + horizontalLen; c++) {
                        int key = Map[r - offset][c];
                        int value = Verticals.get(key);
                        Map[r][c] = value;
                    }

                    offset += 2;
                }

                break;
            }

            case 'D': {
                // 아래로 접으면 시작 지점 row는 halfVerticalLen만큼 아래로 내려감 
                divide(row + halfVerticalLen, col, idx + 1, horizontalLen, halfVerticalLen);

                // 접었던 윗 부분은 아랫 부분을 그대로 대칭하여 복사하면 됨
                int startRow = row + halfVerticalLen - 1;
                for (int r = startRow; r >= row; r--) {
                    for (int c = col; c < col + horizontalLen; c++) {
                        int key = Map[r + offset][c];
                        int value = Verticals.get(key);
                        Map[r][c] = value;
                    }

                    offset += 2;
                }

                break;
            }

            case 'L': {
                // 왼쪽으로 접으면 시작 지점은 바뀌지 않음
                divide(row, col, idx + 1, halfHorizontalLen, verticalLen);

                // 접었던 오른쪽 부분은 왼쪽 부분을 그대로 대칭하여 복사하면 됨
                int startCol = col + halfHorizontalLen;
                for (int c = startCol; c < startCol + halfHorizontalLen; c++) {
                    for (int r = row; r < row + verticalLen; r++) {
                        int key = Map[r][c - offset];
                        int value = Horizontals.get(key);
                        Map[r][c] = value;
                    }
    
                    offset += 2;
                }
    
                break;
            }

            case 'R': {
                // 오른쪽으로 접으면 시작 지점은 halfHorizontalLen만큼 오른쪽으로 감
                divide(row, col + halfHorizontalLen, idx + 1, halfHorizontalLen, verticalLen);

                // 접었던 왼쪽 부분은 오른쪽 부분을 그대로 대칭하여 복사하면 됨
                int startCol = col + halfHorizontalLen - 1;

                for (int c = startCol; c >= col; c--) {
                    for (int r = row; r < row + verticalLen; r++) {
                        int key = Map[r][c + offset];
                        int value = Horizontals.get(key);
                        Map[r][c] = value;
                    }

                    offset += 2;
                }

                break;
            }
        }
    }
}
