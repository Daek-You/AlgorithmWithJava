import java.io.*;
import java.util.*;

public class BOJ7453 {
    private static int N;
    private static int[] A, B, C, D;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        N = Integer.parseInt(br.readLine());
        A = new int[N];
        B = new int[N];
        C = new int[N];
        D = new int[N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            A[i] = Integer.parseInt(st.nextToken());
            B[i] = Integer.parseInt(st.nextToken());
            C[i] = Integer.parseInt(st.nextToken());
            D[i] = Integer.parseInt(st.nextToken());
        }

        int size = N * N;
        int[] sum1 = new int[size];
        int[] sum2 = new int[size];
        int idx = 0;
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum1[idx] = A[i] + B[j];
                sum2[idx] = C[i] + D[j];
                idx++;
            }
        }

        Arrays.sort(sum1);  // 캐시 히트율 적중을 위해
        Arrays.sort(sum2);

        long answer = 0;
        for (int value : sum1) {
            int targetValue = -value;
            int lowerIdx = lowerBound(sum2, targetValue);
            int upperIdx = upperBound(sum2, targetValue);
            answer += upperIdx - lowerIdx;
        }

        System.out.println(answer);
        br.close();
    }

    private static int lowerBound(int[] arr, int key) {
        int left = 0, right = arr.length;

        while (left < right) {
            int mid = (left + right) >> 1;
            if (arr[mid] >= key) right = mid;
            else left = mid + 1;
        }

        return right;
    }

    private static int upperBound(int[] arr, int key) {
        int left = 0, right = arr.length;

        while (left < right) {
            int mid = (left + right) >> 1;
            if (arr[mid] > key) right = mid;
            else left = mid + 1;
        }

        return right;
    }
}