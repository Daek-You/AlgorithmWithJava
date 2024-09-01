import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ1826 {
    private static class GasStation {
        int position, oilAmount;

        public GasStation(int position, int oilAmount) {
            this.position = position;
            this.oilAmount = oilAmount;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        GasStation[] gasStations = new GasStation[N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int position = Integer.parseInt(st.nextToken());
            int oilAmount = Integer.parseInt(st.nextToken());
            gasStations[i] = new GasStation(position, oilAmount);
        }

        // 마을까지 가는 목표 거리와 현재 연료
        StringTokenizer st = new StringTokenizer(br.readLine());
        int villagePosition = Integer.parseInt(st.nextToken());
        int currentOilAmount = Integer.parseInt(st.nextToken());
        
        Arrays.sort(gasStations, (o1, o2) -> Integer.compare(o1.position, o2.position));                                // 주유소를 거리 순으로 정렬
        PriorityQueue<GasStation> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.oilAmount, o1.oilAmount));    // 가장 많은 연료를 줄 수 있는 주유소 순서로 담기는 우선순위 큐
        int currentPosition = 0, answer = 0, index = 0;

        while (currentPosition + currentOilAmount < villagePosition) {
            // 현재 연료로 갈 수 있는 주유소를 모두 우선순위 큐에 추가
            while (index < N && gasStations[index].position <= currentPosition + currentOilAmount) {
                pq.offer(gasStations[index]);
                index++;
            }

            // 현재 위치보다 이전에 있는 주유소들은 전부 제거 -> 이 코드를 넣으니 틀림. 아무래도 이전 주유소가 연료를 많이 채워준다면 그것 또한 고려해야 하나봄
            // while (!pq.isEmpty() && pq.peek().position < currentPosition)   pq.poll();

            // 더 이상 갈 수 있는 주유소가 없다면 도착 불가능
            if (pq.isEmpty()) {
                currentPosition = -1;
                break;
            }

            // 가장 많은 연료를 제공하는 주유소에서 연료를 충전
            GasStation nextStation = pq.poll();
            currentOilAmount -= (nextStation.position - currentPosition);   // 해당 주유소까지 가는 데 연료 소모
            currentOilAmount += nextStation.oilAmount;                      // 해당 주유소에서 주유 받기
            currentPosition = nextStation.position;                         // 현재 위치를 해당 주유소로 옮기기
            answer++;
        }

        answer = (currentPosition + currentOilAmount >= villagePosition) ? answer : -1;
        System.out.println(answer);
        br.close();
    }
}
