import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ1263 {
    
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] list = new int[n][2];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken()), deadline = Integer.parseInt(st.nextToken());
            list[i] = new int[] {time, deadline};
        }

        Arrays.sort(list, (a, b) -> {
            if (a[1] == b[1]) return Integer.compare(b[0], a[0]);
            return Integer.compare(b[1], a[1]);
        });

        int latestStartTime = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            latestStartTime = Math.min(latestStartTime, list[i][1]) - list[i][0];
            if (latestStartTime < 0) {
                latestStartTime = -1;
                break;
            }
        }

        System.out.println(latestStartTime);
        br.close();
    }
}
