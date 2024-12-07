import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ14465 {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()), k = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());

        // true: 고장난 신호등, false: 멀쩡한 신호등
        boolean[] trafficLights = new boolean[n + 1];
        for (int i = 0; i < b; i++) {
            int idx = Integer.parseInt(br.readLine());
            trafficLights[idx] = true;
        }

        int count = 0;
        for (int i = 1; i <= k; i++) {
            // 멀쩡한 신호등일 경우
            if (!trafficLights[i]) count++;
        }

        int minCount = k - count;
        for (int left = 1, right = k + 1; right <= n; left++, right++) {
            if (!trafficLights[right]) count++;
            if (!trafficLights[left]) count--;
            minCount = Math.min(minCount, k - count);
        }

        System.out.println(minCount);
        br.close();
    }
}