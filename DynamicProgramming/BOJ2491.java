import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ2491 {
    private static int N;
    private static int[] Sequence;

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        N = Integer.parseInt(br.readLine());
        Sequence = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            Sequence[i] = Integer.parseInt(st.nextToken());
        }

        // 2. DP를 위한 세팅
        int[] Ascendings = new int[N];
        Ascendings[0] = 1;
        int ascedingCount = 1;

        int[] Descendings = new int[N];
        Descendings[0] = 1;
        int descedingCount = 1;

        // 3. 오름차순이나 내림차순이면 누적해서 갯수 더하고, 아닐 경우에는 다시 세기
        for (int i = 1; i < N; i++) {

            // 오름차순 로직
            if (Sequence[i - 1] <= Sequence[i]) {
                Ascendings[i] = Ascendings[i - 1] + 1;
                ascedingCount = Math.max(ascedingCount, Ascendings[i]);
            } else {
                Ascendings[i] = 1;
            }

            // 내림차순 로직
            if (Sequence[i - 1] >= Sequence[i]) {
                Descendings[i] = Descendings[i - 1] + 1;
                descedingCount = Math.max(descedingCount, Descendings[i]);
            } else {
                Descendings[i] = 1;
            }
        }

        System.out.println(Math.max(ascedingCount, descedingCount));
        br.close();
    }
}
