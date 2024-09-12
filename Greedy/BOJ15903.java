import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ15903 {
    private static int N, M;

    public static void main(String[] args) throws Exception {

        // 카드 개수는 1000장, 연산은 15,000번
        // 카드 한 장당 최대 100만 숫자 -> 오버플로우 주의
        // 상식적으로는 가장 작은 숫자 2장씩 뽑아 더하고 뒤집어 쓰는 게 편할 것 -> 허프만 트리?
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        PriorityQueue<Long> pq = new PriorityQueue<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) pq.add(Long.parseLong(st.nextToken()));

        // M번 카드 합체 놀이 시작
        while (M-- > 0) {
            long minValue1 = pq.poll(), minValue2 = pq.poll();
            long sum = minValue1 + minValue2;
            pq.add(sum);
            pq.add(sum);
        }

        // 카드 놀이를 다했다면 점수 계산
        long answer = 0;
        for (long number : pq)  answer += number;

        System.out.println(answer);
        br.close();
    }
}
