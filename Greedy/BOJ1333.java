import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1333 {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());
        int totalDuration = N * (L + 5) - 5;    // 마지막 무음 구간을 제외한 전체 시간

        for (int time = 0; time <= totalDuration; time += D) {
            boolean isRingingDuringSong = false;

            // 전체 구간 중 노래가 나오는 구간들 확인
            for (int i = 0; i < N; i++) {
                int songStart = i * (L + 5);
                int songEnd = songStart + L;

                // 노래가 나오는 구간에 전화벨이 울린다면 못 받으므로 stop
                if (time >= songStart && time < songEnd) {
                    isRingingDuringSong = true;
                    break;
                }
            }

            // 노래가 나오는 구간에 전화벨이 울리지 않는다면 해당 시간대에 전화 가능
            if (!isRingingDuringSong) {
                System.out.println(time);
                return;
            }
        }

        // N번 전화를 걸었는데도 못 받았다면,
        // (전체 시간동안 전화벨 울리는 횟수 + 1) * 전화벨 간격이 전화를 받는 제일 빠른 시간
        System.out.println((totalDuration / D + 1) * D);
        br.close();
    }
}