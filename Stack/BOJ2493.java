import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ2493 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        ArrayList<int[]> stack = new ArrayList<>();     // [index, height]
        int[] answers = new int[n];

        // 스택에는 항상 내림차순으로 정렬
        for (int i = 0; i < n; i++) {
            int height = Integer.parseInt(st.nextToken());

            // 현재 탑보다 큰 탑이 나올 때까지 스택에서 빼기
            while (!stack.isEmpty() && stack.get(stack.size() - 1)[1] < height)    stack.remove(stack.size() - 1);

            // 그렇게 해서 나보다 큰 탑을 찾았다면 해당 탑이 현재 탑의 신호를 수신
            int towerNumber = (stack.isEmpty()) ? 0 : stack.get(stack.size() - 1)[0];
            answers[i] = towerNumber;
            stack.add(new int[] {i + 1, height});
        }

        // 정답 출력
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < n; i++) answer.append(answers[i]).append(" ");

        System.out.println(answer);
    }
}