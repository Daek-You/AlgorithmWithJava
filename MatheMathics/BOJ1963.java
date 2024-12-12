import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BOJ1963 {
 
    private static final int MAX_VALUE = 9999;

    public static void main(String[] args) throws Exception {

        // 1. 에라토스테네스의 체
        boolean[] isPrimeNumbers = new boolean[MAX_VALUE + 1];
        Arrays.fill(isPrimeNumbers, true);
        isPrimeNumbers[0] = isPrimeNumbers[1] = false;

        for (int i = 2; i <= MAX_VALUE; i++) {
            // 현재 수가 소수라면, 배수들은 모두 false 처리
            if (isPrimeNumbers[i]) {
                // 제곱수 미만의 수는 이전에 모두 검사했으므로 제곱수부터 체크
                for (int j = i * i; j <= MAX_VALUE; j += i) isPrimeNumbers[j] = false;
            }
        }

        // 2. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()), to = Integer.parseInt(st.nextToken());

            int result = bfs(isPrimeNumbers, from, to);
            sb.append((result == -1) ? "Impossible" : result).append("\n");
        }

        System.out.println(sb.toString());
        br.close();
    }

    private static int bfs(boolean[] isPrimeNumbers, int from, int to) {
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[MAX_VALUE + 1];
        int[] distance = new int[MAX_VALUE + 1];

        queue.add(from);
        visited[from] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            if (current == to)  return distance[current];

            for (int i = 0; i < 4; i++) {
                int digit = 1;
                for (int j = 0; j < i; j++) digit *= 10;

                int base = current - (current / digit % 10) * digit;  // 현재 자릿수를 0으로
                for (int j = 0; j <= 9; j++) {
                    if (i == 3 && j == 0) continue;                   // 천의 자리가 0일 경우 skip
                    int next = base + j * digit;

                    if (!visited[next] && isPrimeNumbers[next]) {
                        queue.add(next);
                        visited[next] = true;
                        distance[next] = distance[current] + 1;
                    }
                }           
            }
        }

        return -1;
    }
}