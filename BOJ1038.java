import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BOJ1038 {
    private static int N;
    private static Queue<Long> NumPool;
    private static List<Long> DescreasingNumbers;

    public static void main(String[] args) throws Exception {
        initialize(new BufferedReader(new InputStreamReader(System.in)));
        bfs();

        if (DescreasingNumbers.size() <= N)
            System.out.println(-1);
        else
            System.out.println(DescreasingNumbers.get(N));
    }

    private static void bfs() {
        for (long i = 0; i < 10; i++) {
            NumPool.add(i);
        }

        while (!NumPool.isEmpty()) {
            long number = NumPool.poll();
            DescreasingNumbers.add(number);

            long lastDigit = number % 10;
            for (long i = 0; i < lastDigit; i++) {
                NumPool.add(number * 10 + i);
            }
        }
    }

    private static void initialize(BufferedReader br) throws Exception {
        N = Integer.parseInt(br.readLine());
        NumPool = new LinkedList<>();
        DescreasingNumbers = new ArrayList<>();
    }
}