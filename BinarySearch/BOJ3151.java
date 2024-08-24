import java.util.Scanner;

public class BOJ3151 {
    
    private static int N;
    private static int[] Sequence;
    private static int[] Counts = new int[20001];

    public static void main(String[] args) throws Exception {
        initialize();
        long answer = 0L;

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                int sum = Sequence[i] + Sequence[j];

                // 세 번째 수로 존재할 수 없는 경우는 제외
                if (Math.abs(sum) > 10000)
                    continue;
                
                answer += Counts[-sum + 10000];

                // 현재 선택된 두 수가 세 번째 수로 중복 계산되는 경우 제거
                if (-sum == Sequence[i]) answer--;
                if (-sum == Sequence[j]) answer--;
            }
        }
        
        // 각 조합이 3번씩 중복 계산되기 때문에 3으로 나눠주기
        // (1, 2, -3)에서
        //  1. (1, 2)를 선택하고 -3을 찾는 경우
        //  2. (1, -3)을 선택하고 2를 찾는 경우
        //  3. (2, -3)을 선택하고 1을 찾는 경우
        // 위와 같이 계산되는데 이는 모두 (1, 2, -3)와 같은 조합을 찾는다.
        // 따라서, 중복 조합을 제거하기 위해 3을 나눈 것
        
        System.out.println(answer / 3);
    }

    private static void initialize() throws Exception {
        Scanner scanner = new Scanner(System.in);
        N = Integer.parseInt(scanner.nextLine());
        Sequence = new int[N];

        String[] args = scanner.nextLine().split(" ");

        for (int i = 0; i < N; i++) {
            Sequence[i] = Integer.parseInt(args[i]);
            Counts[Sequence[i] + 10000]++;
        }

        scanner.close();
    }
}
