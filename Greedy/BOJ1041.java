import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1041 {
    private static long N;
    private static long[] Dice = new long[6];
    private static final int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5;

    public static void main(String[] args) throws Exception {
        // 시간 제한: 2초, 메모리: 128MB
        // N이 최대 100만개 -> 정육면체를 이루는 주사위의 개수는 10^{18}개이므로 하나하나 시뮬레이션 불가능 -> long 타입으로 받아야함
        // 한 면만 보이는 주사위는 테두리를 제외한 주사위들 -> (1, 1) ~ (N - 1, N - 2)까의 누적합 5개 -> 주사위의 6면 중 가장 작은 숫자를 선택해 계산하면 됨
        // 세 면이 보이는 주사위는 윗 꼭짓점에 해당하는 4개 -> 주사위의 6면 중 가장 작은 숫자 상, 좌, 우 조합으로 선택
        // 두 면만 보이는 주사위는 위의 두 가지 종류를 제외한 나머지 주사위들 (꼭짓점을 제외한 테두리) -> (n - 1) * 4 + (n - 2) * 4  -> 주사위의 6면 중 가장 작은 숫자 좌, 우 조합을 선택

        // 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        long maxFaceValue = 0;
        long sum = 0;

        for (int i = 0; i < 6; i++) {
            Dice[i] = Long.parseLong(st.nextToken());
            sum += Dice[i];
            maxFaceValue = Math.max(maxFaceValue, Dice[i]);
        }

        // N = 1일 경우에는 5면이 보이므로, 가장 큰 숫자만 제외한 합을 출력
        if (N == 1) {
            System.out.println(sum - maxFaceValue);
            return;
        }

        long answer = 0;

        // N > 1일 경우에는 1면만 보이는 주사위, 2면만 보이는 주사위, 3면만 보이는 주사위를 각각 계산
        // 1면만 보이는 주사위 -> 윗면 1개((N - 2) * (N - 2)) + 옆면 4개((N - 1) * (N - 2) * 4)
        long minOne = findMinOneFace();
        long oneFaceCount = (N - 2) * (N - 2) + (4 * (N - 1) * (N - 2));

        // 2면만 보이는 주사위 -> 기둥 4개((N - 1) * 4) + 윗면 테두리(꼭짓점 제외)((N - 2) * 4)
        long minTwo = findMinTwoFace();
        long twoFaceCount = ((N - 1) * 4) + ((N - 2) * 4);

        // 3면만 보이는 주사위 -> 윗 면 꼭짓점 4개
        long minThree = findMinThreeFace();
        long threeFaceCount = 4;

        answer += minOne * oneFaceCount + minTwo * twoFaceCount + minThree * threeFaceCount;
        System.out.println(answer);
        br.close();
    }
    
    private static long findMinOneFace() {
        long minValue = Long.MAX_VALUE;

        for (int i = 0; i < 6; i++) {
            minValue = Math.min(minValue, Dice[i]);
        }

        return minValue;
    }

    private static long findMinTwoFace() {
        long minValue = Long.MAX_VALUE;

        minValue = Math.min(minValue, Dice[A] + Dice[B]);
        minValue = Math.min(minValue, Dice[A] + Dice[C]);
        minValue = Math.min(minValue, Dice[A] + Dice[D]);
        minValue = Math.min(minValue, Dice[A] + Dice[E]);
        
        minValue = Math.min(minValue, Dice[B] + Dice[C]);
        minValue = Math.min(minValue, Dice[B] + Dice[D]);
        minValue = Math.min(minValue, Dice[B] + Dice[F]);

        minValue = Math.min(minValue, Dice[C] + Dice[E]);
        minValue = Math.min(minValue, Dice[C] + Dice[F]);
        
        minValue = Math.min(minValue, Dice[D] + Dice[E]);
        minValue = Math.min(minValue, Dice[D] + Dice[F]);

        minValue = Math.min(minValue, Dice[E] + Dice[F]);

        return minValue;
    }

    private static long findMinThreeFace() {
        long minValue = Long.MAX_VALUE;

        minValue = Math.min(minValue, Dice[A] + Dice[B] + Dice[C]);
        minValue = Math.min(minValue, Dice[A] + Dice[B] + Dice[D]);
        minValue = Math.min(minValue, Dice[A] + Dice[C] + Dice[E]);
        minValue = Math.min(minValue, Dice[A] + Dice[D] + Dice[E]);

        minValue = Math.min(minValue, Dice[B] + Dice[C] + Dice[F]);
        minValue = Math.min(minValue, Dice[B] + Dice[D] + Dice[F]);

        minValue = Math.min(minValue, Dice[C] + Dice[E] + Dice[F]);
        minValue = Math.min(minValue, Dice[D] + Dice[E] + Dice[F]);
        
        return minValue;
    }
}