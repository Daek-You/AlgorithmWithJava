import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1083 {
    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) arr[i] = Integer.parseInt(st.nextToken());
        
        int S = Integer.parseInt(br.readLine());

        // 2. 버블 정렬 (S 범위 이내의) 시작
        for (int start = 0; start < N && S > 0; start++) {
            int maxIndex = start;

            // 현재 이동 가능한 범위 내에서 최댓값 위치 찾기
            int lastIndex = Math.min(N - 1, start + S);
            for (int i = start; i <= lastIndex; i++) {
                if (arr[maxIndex] < arr[i]) maxIndex = i;
            }

            // 찾은 최댓값을 앞으로 교환해가며 끌고 오기
            for (int i = maxIndex; i > start; i--) {
                swap(arr, i, i - 1);
                S--;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) sb.append(arr[i]).append(" ");

        System.out.println(sb.toString());
        br.close();
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}