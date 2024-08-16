import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ16472 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        int N = Integer.parseInt(br.readLine());
        String Sequence = br.readLine();

        int left = 0, right = 0;
        int[] alphabetCounts = new int[26];
        int size = Sequence.length(), kindOfAlphabets = 0;
        int answer = 0;

        while (right < size) {
        	int idx = getIndex(Sequence.charAt(right));
        	
        	if (alphabetCounts[idx] == 0) {
        		kindOfAlphabets++;
        	}
        	
        	alphabetCounts[idx]++;
        	right++;
        	
        	while (kindOfAlphabets > N) {
        		int leftIdx = getIndex(Sequence.charAt(left));
        		alphabetCounts[leftIdx]--;
        		
        		if (alphabetCounts[leftIdx] == 0) {
        			kindOfAlphabets--;
        		}
        		
        		left++;
        	}
        	
        	answer = Math.max(answer, right - left);
        }
        
        System.out.println(answer);
        br.close();
    }

    private static int getIndex(char c) {
        return c - 'a';
    }
}