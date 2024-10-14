import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ1992 {
    private static StringBuilder answer = new StringBuilder();

    public static void main(String[] args) throws Exception {

        // 1. 입력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        char[][] arr = new char[N][N];
        for (int row = 0; row < N; row++) {
            String line = br.readLine();
            for (int col = 0; col < N; col++) arr[row][col] = line.charAt(col);
        }
        
        divide(arr, N, 0, 0);
        
        System.out.println(answer.toString());
        br.close();
    }

    private static void divide(char[][] arr, int size, int startRow, int startCol) {
        // 마지막 영역까지 내려왔다는 건 다 다르다는 의미이므로, 그냥 찍고 끝내기
        if (size == 1) {
            answer.append(arr[startRow][startCol]);
            return;
        }

        // 주어진 영역이 모두 같다면
        if (isAllSame(arr, size, startRow, startCol)) {
            answer.append(arr[startRow][startCol]);
            return;
        }
        
        answer.append("(");
        // 주어진 영역이 전부 같지 않다면? -> 4분할
        int halfSize = size / 2;
        divide(arr, halfSize, startRow, startCol);                          // 왼쪽 위
        divide(arr, halfSize, startRow, startCol + halfSize);               // 오른쪽 위
        divide(arr, halfSize, startRow + halfSize, startCol);               // 왼쪽 아래
        divide(arr, halfSize, startRow + halfSize, startCol + halfSize);    // 오른쪽 아래
        answer.append(")");                                             // 처리 후, 괄호 닫기
    }

    private static boolean isAllSame(char[][] arr, int size, int startRow, int startCol) {
        char element = arr[startRow][startCol];

        for (int row = startRow; row < startRow + size; row++) {
            for (int col = startCol; col < startCol + size; col++) {
                if (element != arr[row][col])   return false;
            }
        }

        return true;
    }
}