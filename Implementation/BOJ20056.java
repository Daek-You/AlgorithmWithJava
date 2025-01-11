import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BOJ20056 {
    private static class FireBall {
        int row, col, mass, velocity, direction;

        public FireBall(int row, int col, int mass, int velocity, int direction) {
            this.row = row;
            this.col = col;
            this.mass = mass;
            this.velocity = velocity;
            this.direction = direction;
        }
    }

    private static int N, M;
    private static LinkedList<FireBall>[][] Map;

    private static final int DIRECTION_COUNT = 8;
    private static final int[] ROW_DIRS = {-1, -1, 0, 1, 1,  1,  0, -1};
    private static final int[] COL_DIRS = { 0,  1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws Exception {

        // 1. 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        LinkedList<FireBall> balls = new LinkedList<>();

        Map = new LinkedList[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                Map[r][c] = new LinkedList<>();
            }
        }

        // 파이어볼 정보 등록
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken()) - 1, col = Integer.parseInt(st.nextToken()) - 1;
            int mass = Integer.parseInt(st.nextToken()), velocity = Integer.parseInt(st.nextToken());
            int direction = Integer.parseInt(st.nextToken());

            FireBall fireBall = new FireBall(row, col, mass, velocity, direction);
            balls.add(fireBall);
        }

        // 2. 시뮬레이션 시작
        while (K-- > 0) {
            // 이동
            move(balls);

            // 합치기
            merge(balls);
        }

        int answer = 0;
        for (FireBall ball : balls) answer += ball.mass;
        System.out.println(answer);
    }

    private static void move(LinkedList<FireBall> balls) {
        int size = balls.size();
        
        for (int i = 0; i < size; i++) {
            FireBall ball = balls.poll();
            int v = ball.velocity % N;
            int nextRow = (N + ball.row + ROW_DIRS[ball.direction] * v) % N;
            int nextCol = (N + ball.col + COL_DIRS[ball.direction] * v) % N;
            Map[nextRow][nextCol].add(new FireBall(nextRow, nextCol, ball.mass, ball.velocity, ball.direction));
        }
    }

    private static void merge(LinkedList<FireBall> balls) {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                // 한 개만 존재하는 칸에 대해서는 뽑아서 다시 넣기만
                if (Map[r][c].size() == 1) {
                    balls.add(Map[r][c].poll());
                    continue;
                }

                // 같은 칸에 2개 이상 존재하는 파이어볼에 관해
                if (Map[r][c].size() >= 2) {
                    int size = Map[r][c].size();
                    int totalMass = 0, totalVelocity = 0;
                    boolean isOdd = false, isEven = false;

                    while (!Map[r][c].isEmpty()) {
                        FireBall fireBall = Map[r][c].poll();
                        totalMass += fireBall.mass;
                        totalVelocity += fireBall.velocity;

                        if (fireBall.direction % 2 == 0)    isEven = true;
                        else                                isOdd = true;
                    }

                    int newMass = totalMass / 5;
                    if (newMass == 0)   continue;

                    int newVelocity = totalVelocity / size;
                    int d = (isOdd && !isEven) || (!isOdd && isEven) ? 0 : 1;

                    // 파이어볼 4개 생성
                    for (; d < DIRECTION_COUNT; d += 2) {
                        balls.add(new FireBall(r, c, newMass, newVelocity, d));
                    }
                }
            }
        }
    }
}
