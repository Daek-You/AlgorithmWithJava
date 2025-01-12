import java.io.BufferedReader;
import java.io.InputStreamReader;

// 1000 x 1000 = 1,000,000 이므로 충분히 가능
public class BOJ2195 {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        String s = br.readLine(), p = br.readLine();
        int pIdx = p.length() - 1;
        int answer = 0;

        while (pIdx >= 0) {
            pIdx = findLongestCommonIndex(s, p, pIdx);
            answer++;
        }

        System.out.println(answer);
    }

    private static int findLongestCommonIndex(String s, String p, final int pIdx) {
        int index = pIdx, commons = 0;
        int sIdx = s.length() - 1;

        while (sIdx >= 0) {
            // 뒤에서부터 같은 문자로 시작하는 위치를 찾았다면
            if (s.charAt(sIdx) == p.charAt(pIdx)) {
                int currentCommons = 0;
                int tempPIdx = pIdx;

                // 같은 문자가 몇 개까지 이어지는지 검사
                while (sIdx >= 0 && tempPIdx >= 0 && s.charAt(sIdx) == p.charAt(tempPIdx)) {
                    sIdx--;
                    tempPIdx--;
                    currentCommons++;
                }

                // 이전에 찾았던 공통 문자 개수보다 많다면 갱신
                if (currentCommons > commons) {
                    commons = currentCommons;
                    index = tempPIdx;
                }

            }

            // 같은 문자를 못 찾았다면 continue
            else {
                sIdx--;
            }
        }

        return index;
    }
}
