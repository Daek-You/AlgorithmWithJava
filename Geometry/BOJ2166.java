import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ2166 {

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        long[] x = new long[N + 1];
        long[] y = new long[N + 1];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            x[i] = Long.parseLong(st.nextToken());
            y[i] = Long.parseLong(st.nextToken());
        }

        // 다각형을 닫기 위해, 처음 점과 마지막 점은 같게 설정
        x[N] = x[0];
        y[N] = y[0];

        // 2. 신발끈 공식 적용
        long sum = 0;
        for (int i = 0; i < N; i++) {
            sum += x[i] * y[i + 1] - x[i + 1] * y[i];
        }

        double answer = Math.abs(sum) / 2.0;
        System.out.printf("%.1f", answer);
        br.close();
    }
}