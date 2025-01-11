import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class BOJ24337 {

    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int A = Integer.parseInt(st.nextToken()), B = Integer.parseInt(st.nextToken());
        ArrayDeque<Integer> buildings = new ArrayDeque<>();

        // 가희가 볼 수 있는 건물(왼쪽 건물들)부터 오름차순으로 채우기
        for (int i = 1; i < A; i++)         buildings.add(i);

        // 가장 높은 건물 추가하기
        buildings.add(Math.max(A, B));

        // 단비가 볼 수 있는 건물들 채우기 (가장 높은 건물은 서로 공유하는 것이기에 1개를 빼고 사전순으로 제일 빠른 B-1부터 시작)
        for (int i = B - 1; i >= 1; i--)    buildings.add(i);

        StringBuilder answer = new StringBuilder();
        
        // 만든 건물들이 전체 건물 개수보다 많으면 불가능함
        if (buildings.size() > N) {
            answer.append(-1);
        } else {
            // 첫 번째 건물은 잠시 빼두기
            int firstBuilding = buildings.removeFirst();
            int size = buildings.size();

            // 필요한 건물 개수만큼 높이 1인 건물을 앞 쪽에 추가
            for (int i = 0; i < N - size - 1; i++)  buildings.addFirst(1);

            // 첫 번쨰 건물 다시 맨 앞에 넣기
            buildings.addFirst(firstBuilding);

            // 정답 세팅
            for (int height : buildings)    answer.append(height).append(" ");
        }

        System.out.println(answer);
        br.close();
    }
}
