import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ5904 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Integer.parseInt(br.readLine());

        // n번째 문자를 구할 수 있는 길이의 S(k) 길이 구하기
        long length = 3, previousLength = 3, k = 0;
        while (length < n) {
            k++;
            previousLength = length;
            length = (previousLength * 2) + k + 3;    // m(1) + o(k + 2)
        }

        char answer = findChar(length, n, k);
        System.out.println(answer);
        br.close();
    }

    private static char findChar(long length, long n, long k) {
        if (k == 0)
            return (n == 1) ? 'm' : 'o';

        // "왼쪽"/"중간"/"오른쪽" 구간 중 n이 "왼쪽" 구간에 있다면 거기로 이동
        long previousLength = (length - (k + 3)) / 2;
        if (n <= previousLength)
            return findChar(previousLength, n, k - 1);
        
        // "중간" 구간에 n이 존재한다면 인덱스에 따라 m 또는 o 리턴
        else if (previousLength + 1 <= n && n < previousLength + k + 3)
            return (previousLength + 1 == n) ? 'm' : 'o';

        // "오른쪽" 구간에 n이 존재한다면 거기로 이동 (n의 인덱스 조정 필요)
        return findChar(previousLength, n - (previousLength + k + 3), k - 1);
    }
}