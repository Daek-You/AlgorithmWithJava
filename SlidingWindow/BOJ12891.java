import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ12891 {
    private static int S, P;
    private static String DNA;

    private static final char[] DNA_SEQUENCE = {'A', 'C', 'G', 'T'};
    private static int[] UsingCodintions;
    private static int[] CurrentUsedCounts;

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] arguments = br.readLine().split(" ");

        S = Integer.parseInt(arguments[0]);
        P = Integer.parseInt(arguments[1]);
        UsingCodintions = new int[DNA_SEQUENCE.length];
        CurrentUsedCounts = new int[DNA_SEQUENCE.length];
        
        DNA = br.readLine();
        arguments = br.readLine().split(" ");
        
        for (int i = 0; i < DNA_SEQUENCE.length; i++) {
            UsingCodintions[i] = Integer.parseInt(arguments[i]);
        }

        // 2. Sliding window
        int left = 0, answer = 0;

        for (int right = 0; right < S; right++) {
            int currentSubsetLength = right - left + 1;
            int rIdx = getIndex(DNA.charAt(right));
            CurrentUsedCounts[rIdx]++;

            // 부분 문자열의 길이가 되었다면
            if (currentSubsetLength >= P) {
                if (isConditionMet()) {     // 조건에 만족한다면 개수 추가
                    answer++;
                }

                // left도 똑같이 한 칸 이동하며 계속 sliding window 진행
                int idx = getIndex(DNA.charAt(left++));
                CurrentUsedCounts[idx]--;
            }
        }

        System.out.println(answer);
        br.close();
    }

    private static boolean isConditionMet() {
        for (int i = 0; i < DNA_SEQUENCE.length; i++) {
            if (UsingCodintions[i] == 0) {
                continue;
            }

            if (CurrentUsedCounts[i] < UsingCodintions[i]) {
                return false;
            }
        }

        return true;
    }

    private static int getIndex(char c) {
        switch(c) {
            case 'A':   return 0;
            case 'C':   return 1;
            case 'G':   return 2;
            case 'T':   return 3;
        }

        return -1;
    }
}