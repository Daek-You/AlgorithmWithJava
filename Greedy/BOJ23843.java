import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ23843 {

    public static void main(String[] args) throws Exception {
        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
        
        Integer[] times = new Integer[N];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++)  times[i] = Integer.parseInt(st.nextToken());
        
        // 2. 내림차순 정렬
        Arrays.sort(times, Collections.reverseOrder());
        
        // 각 콘센트의 현재 충전 완료 시간을 관리
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int i = 0; i < M; i++)  pq.offer(0);    // M개의 콘센트 초기화
        
        // 3. 시간이 가장 긴 기기부터 처리 -> 가장 빨리 끝나는 콘센트를 선택하여 기기 추가
        for(int time : times) {
            int current = pq.poll();
            pq.offer(current + time);
        }
        
        // 4. 가장 늦게 끝나는 시간이 정답
        while(pq.size() > 1)    pq.poll();
        System.out.println(pq.poll());
    }
}