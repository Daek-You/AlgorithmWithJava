import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ21278 {
    private static final int INF = 1_000_000_000;
    private static int N, M;
    private static int[][] Distances;
    private static int TotalMinRoundTripTime = INF;                         // 선택한 치킨집 2개와 모든 건물과의 왕복 시간 합의 최솟값
    private static int[] Buildings = new int[2];                            // 조합에 사용할 임시 배열
    private static int[] SelectedBuildings = new int[]{INF, INF};           // 답에 출력할 배열 ([작은 번호 건물, 큰 번호 건물])

    public static void main(String[] args) throws Exception {

        // 건물 X의 접근성 = X에서 가장 가까운 호석이 두마리 치킨집까지 왕복하는 최단 시간
        // 모든 건물에서 가장 가까운 치킨집까지 왕복하는 최단 시간의 총합을 최소화 할 수 있는 건물 2개를 골라야 함
        // 건물 2개의 번호와 최단 시간을 출력 -> 건물 조합이 여러 개라면, 1)건물 번호 중 작은 게 더 작을 수록, 2)작은 번호가 같다면 큰 번호가 더 작을수록 좋은 건물 조합
        // N <= 100이고, 모든 건물과 치킨집 간의 거리를 봐야 하므로 플로이드-워셜 알고리즘을 적용

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Distances = new int[N + 1][N + 1];

        // 2. Distances 배열을 무한대로 초기화
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                if (row != col) Distances[row][col] = INF;
            }
        }

        // 3. 도로 입력받기
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            Distances[from][to] = Distances[to][from] = 1;
        }
        
        // 4. 플로이드 워셜 알고리즘 수행 후, 건물 2개씩을 뽑아 모든 도시 건물과의 왕복 시간을 계산
        FloydWarshall();
        combination(1, 0);

        System.out.printf("%d %d %d", SelectedBuildings[0], SelectedBuildings[1], TotalMinRoundTripTime);
        br.close();
    }

    public static void combination(int idx, int count) {
        if (count == 2) {
            // 모든 도시와의 왕복 거리 합 구하기
            int rountTripDistance = 0;
            
            for (int i = 1; i <= N; i++) {

                // 2개의 치킨집 중에 현재 건물에서 가까운 치킨집을 골라 왕복 거리 더하기
                int chicken1 = Buildings[0];
                int checken2 = Buildings[1];
                
                if (Distances[i][chicken1] < Distances[i][checken2]) {
                    rountTripDistance += Distances[i][chicken1] + Distances[chicken1][i];
                } else {
                    rountTripDistance += Distances[i][checken2] + Distances[checken2][i];
                }
            }

            // 더 짧은 거리가 나타났다면 전부 갱신
            if (rountTripDistance < TotalMinRoundTripTime) {
                TotalMinRoundTripTime = rountTripDistance;
                SelectedBuildings[0] = Buildings[0];
                SelectedBuildings[1] = Buildings[1];
            } 
            
            // 같은 후보군을 만났다면
            else if (rountTripDistance == TotalMinRoundTripTime) {
                // 작은 번호 건물의 숫자가 같다면 큰 번호가 작은 걸로
                if (SelectedBuildings[0] == Buildings[0]) {
                    if (Buildings[1] < SelectedBuildings[1]) {
                        SelectedBuildings[0] = Buildings[0];
                        SelectedBuildings[1] = Buildings[1];
                    }
                } 
                // 작은 번호 건물이 더 작다면
                else if (Buildings[0] < SelectedBuildings[0]) {
                    SelectedBuildings[0] = Buildings[0];
                    SelectedBuildings[1] = Buildings[1];
                }
            }

            return;
        }


        for (int i = idx; i <= N; i++) {
            Buildings[count] = i;
            combination(i + 1, count + 1);
        }
    }

    public static void FloydWarshall() {
        for (int mid = 1; mid <= N; mid++) {
            for (int from = 1; from <= N; from++) {
                for (int to = 1; to <= N; to++) {
                    Distances[from][to] = Math.min(Distances[from][to], Distances[from][mid] + Distances[mid][to]);
                }
            }
        }
    }
}
