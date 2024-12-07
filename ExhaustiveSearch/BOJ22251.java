import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ22251 {
    private static int N, K, P, X;
    private static int[] LED_DIGITS;
    private static int[] Display;

    public static void main(String[] args) throws Exception {
        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        // 2. 각 숫자별 LED 비트열 등록
        LED_DIGITS = new int[10];
        for (int i = 0; i < 10; i++)    setDigit(LED_DIGITS, i, i);

        // 3. 현재 층(X)으로 LED 숫자 바꾸기
        Display = new int[K];
        int num = X;
        for (int i = K - 1; i >= 0; i--) {
            setDigit(Display, i, num % 10);
            num /= 10;
        }

        // 4. 1부터 N까지의 모든 숫자에 대해 변경 가능 여부 확인
        int answer = 0;
        for (int floor = 1; floor <= N; floor++) {
            // 현재 층은 건너뛰기
            if (floor == X) continue;  
            
            // 변경하려는 숫자의 LED 상태 계산
            int[] targetDisplay = new int[K];
            num = floor;
            for (int i = K - 1; i >= 0; i--) {
                setDigit(targetDisplay, i, num % 10);
                num /= 10;
            }
            
            // 현재 상태와 목표 상태의 차이를 계산
            int difference = 0;
            for (int j = 0; j < K; j++) {
                int xor = Display[j] ^ targetDisplay[j];    // 두 상태의 XOR 연산으로 다른 비트 수 계산
                difference += Integer.bitCount(xor);        // 켜져있는 비트 수 세기
            }
            
            // 차이가 P 이하면 가능한 경우
            if (difference > 0 && difference <= P)  answer++;
        }

        // 5. 정답 출력
        System.out.println(answer);
        br.close();
    }

    private static void setDigit(int[] LED, int idx, int num) {
        LED[idx] = 0;

        // 각 숫자에 맞는 LED 켜기
        switch (num) {
            case 0:
                LED[idx] |= (1 << 0);
                LED[idx] |= (1 << 1);
                LED[idx] |= (1 << 2);
                LED[idx] |= (1 << 4);
                LED[idx] |= (1 << 5);
                LED[idx] |= (1 << 6);
                break;
            case 1:
                LED[idx] |= (1 << 2);
                LED[idx] |= (1 << 5);
                break;
            case 2:
                LED[idx] |= (1 << 0);
                LED[idx] |= (1 << 2);
                LED[idx] |= (1 << 3);
                LED[idx] |= (1 << 4);
                LED[idx] |= (1 << 6);
                break;
            case 3:
                LED[idx] |= (1 << 0);
                LED[idx] |= (1 << 2);
                LED[idx] |= (1 << 3);
                LED[idx] |= (1 << 5);
                LED[idx] |= (1 << 6);
                break;
            case 4:
                LED[idx] |= (1 << 1);
                LED[idx] |= (1 << 2);
                LED[idx] |= (1 << 3);
                LED[idx] |= (1 << 5);
                break;
            case 5:
                LED[idx] |= (1 << 0);
                LED[idx] |= (1 << 1);
                LED[idx] |= (1 << 3);
                LED[idx] |= (1 << 5);
                LED[idx] |= (1 << 6);
                break;
            case 6:
                LED[idx] |= (1 << 0);
                LED[idx] |= (1 << 1);
                LED[idx] |= (1 << 3);
                LED[idx] |= (1 << 4);
                LED[idx] |= (1 << 5);
                LED[idx] |= (1 << 6);
                break;
            case 7:
                LED[idx] |= (1 << 0);
                LED[idx] |= (1 << 2);
                LED[idx] |= (1 << 5);
                break;
            case 8:
                LED[idx] |= (1 << 0);
                LED[idx] |= (1 << 1);
                LED[idx] |= (1 << 2);
                LED[idx] |= (1 << 3);
                LED[idx] |= (1 << 4);
                LED[idx] |= (1 << 5);
                LED[idx] |= (1 << 6);
                break;
            case 9:
                LED[idx] |= (1 << 0);
                LED[idx] |= (1 << 1);
                LED[idx] |= (1 << 2);
                LED[idx] |= (1 << 3);
                LED[idx] |= (1 << 5);
                LED[idx] |= (1 << 6);
                break;
        }
    }
}