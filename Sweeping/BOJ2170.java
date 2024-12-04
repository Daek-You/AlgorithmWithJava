import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ2170 {

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[][] lines = new int[N][2];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            lines[i][0] = Integer.parseInt(st.nextToken());
            lines[i][1] = Integer.parseInt(st.nextToken());
        }
        
        // 2. 시작점을 기준으로 변경
        Arrays.sort(lines, (a, b) -> Integer.compare(a[0], b[0]));

        int totalLength = 0;
        int start = lines[0][0], end = lines[0][1];

        for (int i = 1; i < N; i++) {
            // 현재 선분이 이전 선분과 겹치거나 이어지는 경우
            if (lines[i][0] <= end) {
                end = Math.max(end, lines[i][1]);
            }

            // 겹치지 않는 경우, 이전까지의 길이를 더하고 새로운 선분 시작
            else {
                totalLength += end - start;
                start = lines[i][0];
                end = lines[i][1];
            }
        }

        // 마지막 선분 처리
        totalLength += end - start;
        System.out.println(totalLength);
        br.close();
    }
}