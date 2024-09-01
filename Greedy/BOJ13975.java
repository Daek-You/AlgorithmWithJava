import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ13975 {
    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder answers = new StringBuilder();

        while (T-- > 0) {
            int K = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            // 허프만 트리(Huffman tree) 구성하기
            PriorityQueue<Long> pq = new PriorityQueue<>();
            for (int i = 0; i < K; i++) pq.add(Long.parseLong(st.nextToken()));

            long answer = 0;
            while (pq.size() > 1) {
                long file1 = pq.poll(),file2 = pq.poll();
                long sum = file1 + file2;

                answer += sum;
                pq.add(sum);
            }

            answers.append(answer).append("\n");
        }

        System.out.println(answers.toString());
        br.close();
    }
}