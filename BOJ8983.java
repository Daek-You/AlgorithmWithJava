import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ8983 {
    private static int M, N, L;
    private static long[] ShootingPositions;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        String[] inputs = br.readLine().split(" ");

        M = Integer.parseInt(inputs[0]);
        N = Integer.parseInt(inputs[1]);
        L = Integer.parseInt(inputs[2]);

        inputs = br.readLine().split(" ");
        ShootingPositions = new long[M];

        for (int i = 0; i < M; i++) {
            ShootingPositions[i] = Long.parseLong(inputs[i]);
        }

        Arrays.sort(ShootingPositions);
        int answer = 0;

        for (int i = 0; i < N; i++) {
            inputs = br.readLine().split(" ");
            long x = Long.parseLong(inputs[0]), y = Long.parseLong(inputs[1]);

            answer += binarySearch(0, M - 1, x, y);
        }

        System.out.println(answer);
        br.close();
    }

    public static int binarySearch(int left, int right, long animalX, long animalY) {
        boolean canHunt = false;

        while (left <= right) {
            int mid = (left + right) / 2;
            long turretX = ShootingPositions[mid];
            long distance = Math.abs(turretX - animalX) + animalY;

            // 사냥 가능한 거리이면 이분 탐색 중단
            if (distance <= L) {
                canHunt = true;
                break;
            }

            if (turretX < animalX) left = mid + 1;
            else                   right = mid - 1;
        }

        return canHunt ? 1 : 0;
    }
}
