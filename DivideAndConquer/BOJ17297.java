import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

// 2^{30}-1 = 1,073,741,823 (대략 10억)
public class BOJ17297 {
    private static final String str = "Messi Gimossi";

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int M = Integer.parseInt(br.readLine());
        ArrayList<Integer> lengths = new ArrayList<>();
        lengths.add(5);     // Messi
        lengths.add(13);    // Messi Gimossi
        
        int index = 2, length = 13;
        while (length < M) {
            length = lengths.get(index - 1) + 1 + lengths.get(index - 2);   // messi(n) = messi(n-1) + 공백 + messi(n-2)
            lengths.add(length);
            index++;
        }

        divide(lengths, index - 1, M);
        br.close();
    }
    
    private static void divide(ArrayList<Integer> lengths, int N, int M) {
        if (N == 0) {   // "Messi"
            System.out.println(str.charAt(M - 1));
            return;
        }

        if (N == 1) {   // "Messi Gimossi"
            if (M == lengths.get(0) + 1)  System.out.println("Messi Messi Gimossi");    // 공백인 부분일 경우
            else                                System.out.println(str.charAt(M - 1));
            return;
        }

        // messi(N-1)에 M이 존재할 경우 -> 즉, 왼쪽 영역
        if (M <= lengths.get(N - 1)) {
            divide(lengths, N - 1, M);
        }

        // 공백에 해당할 경우
        else if (lengths.get(N - 1) + 1 == M) {
            System.out.println("Messi Messi Gimossi");
            return;
        }

        // messi(N-2)에 M이 존재하는 경우 -> 즉, 오른쪽 영역
        else {
            divide(lengths, N - 2, M - lengths.get(N - 1) - 1);     // 왼쪽 영역 + 공백을 빼서 M 인덱스 조정
        }
    }
}