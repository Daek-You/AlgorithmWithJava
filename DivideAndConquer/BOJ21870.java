import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ21870 {
    private static int N;
    private static int[] Arr;

    public static void main(String[] args) throws Exception {
        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        Arr = new int[N];
        for (int i = 0; i < N; i++) Arr[i] = Integer.parseInt(st.nextToken());
        br.close();

        // 2. 분할 정복
        int answer = solution(0, N - 1);
        System.out.println(answer);
    }

    public static int solution(int left, int right) {
        // 선택된 원소가 한 개일 때
        if (left == right)  return Arr[left];

        // 짝수일 경우에는 -1을 해도 N/2개가 선택되지만, 홀수일 경우에 -1을 안 하면 N/2 + 1개가 선택되기에 -1을 해줌
        int mid = (left + right - 1) / 2;

        // 왼쪽부터 N/2개를 고른 경우
        int leftValue = computeRangeGCD(left, mid) + solution(mid + 1, right);
        // 오른쪽부터 N/2개를 고른 경우
        int rightValue = solution(left, mid) + computeRangeGCD(mid + 1, right);

        return Math.max(leftValue, rightValue);
    }

    private static int computeRangeGCD(int left, int right) {
        int result = 0;
        for (int i = left; i <= right; i++) {
            result = gcd(result, Arr[i]);
        }

        return result;
    }

    private static int gcd(int a, int b) {
        // gcd(a, b) = gcd(b, a % b)

        while (b != 0) {
            int remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }
}
