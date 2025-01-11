import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ1461 {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
        int[] positions = new int[N];
        st = new StringTokenizer(br.readLine());
        int numOfNegativeNums = 0;

        for (int i = 0; i < N; i++) {
            positions[i] = Integer.parseInt(st.nextToken());
            if (positions[i] < 0)   numOfNegativeNums++;
        }

        // 오름차순 정렬
        Arrays.sort(positions);
        int answer = 0;

        // 음수 영역 왕복 거리 계산
        for (int i = 0; i < numOfNegativeNums; i += M)          answer += Math.abs(positions[i]) * 2;
        // 양수 영역 왕복 거리 계산
        for (int i = N - 1; i >= numOfNegativeNums; i -= M)     answer += positions[i] * 2;
        
        // 마지막 거리는 왕복한 거리 제외
        answer -= Math.max(Math.abs(positions[0]), positions[N - 1]);
        System.out.println(answer);
    }
}
