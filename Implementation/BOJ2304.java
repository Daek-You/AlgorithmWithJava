import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ2304 {
    private static final int MAX_HEIGHT = 1000;
    private static int N;
    private static int[] Heights;

    public static void main(String[] args) throws Exception {
        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        Heights = new int[MAX_HEIGHT + 1];
        int maxHeight = 0, maxHeightIdx = 0;
        int leftmost = 1000, rightmost = 0;     // 가장 왼쪽 기둥 인덱스와 가장 오른쪽 기둥 인덱스
        
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int L = Integer.parseInt(st.nextToken());
            int H = Integer.parseInt(st.nextToken());
            Heights[L] = H;
            
            // 최대 높이와 그 위치 갱신
            if (H > maxHeight) {
                maxHeight = H;
                maxHeightIdx = L;
            }
            
            // 가장 왼쪽, 오른쪽 기둥 위치 갱신
            leftmost = Math.min(leftmost, L);
            rightmost = Math.max(rightmost, L);
        }
        
        // 2. 왼쪽에서 오른쪽으로 계산
        int answer = 0, currentHeight = 0;
        for (int i = leftmost; i <= maxHeightIdx; i++) {
            currentHeight = Math.max(currentHeight, Heights[i]);
            answer += currentHeight;
        }
        
        // 3. 오른쪽에서 왼쪽으로 계산
        currentHeight = 0;
        for (int i = rightmost; i > maxHeightIdx; i--) {
            currentHeight = Math.max(currentHeight, Heights[i]);
            answer += currentHeight;
        }
        
        // 4. 정답 출력
        System.out.println(answer);
    }
}