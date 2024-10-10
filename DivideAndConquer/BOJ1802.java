import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ1802 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            String info = br.readLine();
            boolean isAvailable = solution(info, 0, info.length() - 1);
            String answer = isAvailable ? "YES" : "NO";
            sb.append(answer).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }

    private static boolean solution(String info, int start, int last) {
        if (start == last)  return true;

        int mid = (start + last) / 2;
        for (int i = start; i < mid; i++) {
            // 중앙을 기준으로 같은 offset만큼 떨어진 두 지점은 서로 달라야 동호의 규칙대로 접기 가능
            if (info.charAt(i) == info.charAt(last - (i - start)))    return false;
        }

        return solution(info, start, mid - 1) && solution(info, mid + 1, last);
    }
}