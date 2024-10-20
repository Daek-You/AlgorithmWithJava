import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// h(50) = 4,503,599,627,370,493
public class BOJ16974 {
    private static long N, X;
    private static long[] Patties, Lengths;

    public static void main(String[] args) throws Exception {
        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Long.parseLong(st.nextToken());
        X = Long.parseLong(st.nextToken());
        Patties = new long[(int)N + 1];
        Lengths = new long[(int)N + 1];

        Patties[0] = 1;     // 처음엔 패티 1개
        Lengths[0] = 1;     // "P"

        for (int i = 1; i <= N; i++) {
            Lengths[i] = 2 * Lengths[i - 1] + 3;    // "B" + f(i-1) + "P" + f(i-1) + "B"
            Patties[i] = 2 * Patties[i - 1] + 1;    // f(i-1) + "P" + f(i-1)

            /*
            if (Lengths[i] > X) break;

            위 코드를 넣으면 틀림. 이유는 잘 모르겠음..
             */
        }

        long answer = solution(N, X);
        System.out.println(answer);
        br.close();
    }

    private static long solution(long L, long X) {
        // Level-0 버거는 "P"
        if (L == 0) return X == 1 ? 1 : 0;

        // Level-N 버거의 첫 문자는 "B"이므로 패티 X
        if (X == 1) return 0;

        // 왼쪽 f(l-1) 부분일 경우
        if (X <= 1 + Lengths[(int)L - 1])   return solution(L - 1, X - 1);      // 맨 앞 "B" 개수 1개를 빼서 X 인덱스 조정

        // 중간 "P" 패티일 경우
        if (X == 2 + Lengths[(int)L - 1])   return Patties[(int)L - 1] + 1;

        // 오른쪽 f(l-1) 부분일 경우
        if (X <= 2 + 2 * Lengths[(int)L - 1])   return Patties[(int)L - 1] + 1 + solution(L - 1, X - 2 - Lengths[(int)L - 1]);  // 맨 앞 "B"와 중간 "P", 왼쪽 f(l-1)을 빼서 X 인덱스 조정

        // 마지막 "B"는 패티가 아니므로 그 앞까지의 패티 개수 리턴
        return Patties[(int)L - 1] * 2 + 1;
    }
}