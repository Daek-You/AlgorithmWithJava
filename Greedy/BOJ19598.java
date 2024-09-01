import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ19598 {
    private static class Meeting implements Comparable<Meeting> {
        int startTime, endTime;

        public Meeting(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        // 1)시작 시간, 2)종료 시간을 기준으로 오름차순 정렬
        @Override
        public int compareTo(Meeting o) {
            if (this.startTime == o.startTime) {
                return Integer.compare(this.endTime, o.endTime);
            }
            return Integer.compare(this.startTime, o.startTime);
        }
    }

    public static void main(String[] args) throws Exception {
        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        Meeting[] meetings = new Meeting[N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int startTime = Integer.parseInt(st.nextToken());
            int endTime = Integer.parseInt(st.nextToken());
            meetings[i] = new Meeting(startTime, endTime);
        }

        // 2. 정렬
        Arrays.sort(meetings);
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(meetings[0].endTime); // 첫 회의의 종료 시간을 힙에 추가

        for (int i = 1; i < N; i++) {
            // 현재 회의의 시작 시간보다 이전에 끝나는 회의실을 힙에서 제거
            if (minHeap.peek() <= meetings[i].startTime)    minHeap.poll();

            // 현재 회의의 종료 시간을 힙에 추가
            minHeap.offer(meetings[i].endTime);
        }

        // 힙에 남아있는 요소의 개수가 필요한 최소 회의실 수
        int minRooms = minHeap.size();
        System.out.println(minRooms);

        br.close();
    }
}