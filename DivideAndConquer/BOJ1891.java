import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 2^50 = 1,125,899,906,842,624 -> long
public class BOJ1891 {
    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long d = Long.parseLong(st.nextToken());
        String num = st.nextToken();
        
        st = new StringTokenizer(br.readLine());
        long x = Long.parseLong(st.nextToken()), y = Long.parseLong(st.nextToken());
        
        // 2. 시작점 좌표를 먼저 구하기 (row, col)
        final long size = 1L << d;
        long row = 0, col = 0, tempSize = size;
        
        for (int i = 0; i < d; i++) {
            tempSize /= 2;
            switch (num.charAt(i)) {
                case '1':
                    col += tempSize;
                    break;
                case '3':
                    row += tempSize;
                    break;
                case '4':
                    row += tempSize;
                    col += tempSize;
                    break;
            }
        }

        // 2. 칸 이동
        row -= y;    // 위쪽 이동은 -
        col += x;

        // 유효하지 않은 사분면이라면 -1 출력
        if (row < 0 || row >= size || col < 0 || col >= size) {
            System.out.println("-1");
            return;
        }

        // 3. 이동한 좌표를 토대로 역변환
        tempSize = size;
        StringBuilder answer = new StringBuilder();
        long startRow = 0, startCol = 0;

        for (int i = 0; i < d; i++) {
            // 첫 시작 좌표는 항상 2사분면
            tempSize /= 2;

            // 2사분면에 위치하는 경우
            if (row < startRow + tempSize && col < startCol + tempSize) {
                answer.append("2");
            }
            // 1사분면에 위치하는 경우
            else if (row < startRow + tempSize && col >= startCol + tempSize) {
                startCol += tempSize;
                answer.append("1");
            }
            // 3사분면에 위치하는 경우
            else if (row >= startRow + tempSize && col < startCol + tempSize) {
                startRow += tempSize;
                answer.append("3");
            }

            // 4사분면에 위치하는 경우
            else if (row >= startRow + tempSize && col >= startCol + tempSize) {
                startRow += tempSize;
                startCol += tempSize;
                answer.append("4");
            }
        }

        System.out.println(answer.toString());
        br.close();
    }
}