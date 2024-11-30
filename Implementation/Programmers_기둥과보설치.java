package Implementation;
import java.util.ArrayList;
import java.util.Arrays;

public class Programmers_기둥과보설치 {

    public static int[][] solution(int n, int[][] build_frame) {
        boolean[][][] map = new boolean[n + 1][n + 1][2];

        for (int[] frame : build_frame) {
            int x = frame[0], y = frame[1];
            int building = frame[2], operation = frame[3];

            if (operation == 0) { // 삭제
                map[y][x][building] = false;
                if (!isValid(map, n)) map[y][x][building] = true;
            } 
            
            else { // 설치
                map[y][x][building] = true;
                if (!isValid(map, n)) map[y][x][building] = false;
            }
        }

        // 설치된 건물 정보 얻어오기
        ArrayList<int[]> list = new ArrayList<>();
        for (int y = 0; y <= n; y++) {
            for (int x = 0; x <= n; x++) {
                if (map[y][x][0]) list.add(new int[]{x, y, 0});
                if (map[y][x][1]) list.add(new int[]{x, y, 1});
            }
        }

        // 정답 제출을 위한 2차원 배열 형성
        int[][] answer = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) answer[i] = list.get(i);

        // 정렬
        Arrays.sort(answer, (a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            else if (a[1] != b[1]) return Integer.compare(a[1], b[1]);
            else return Integer.compare(a[2], b[2]);
        });

        return answer;
    }

    private static boolean isValid(boolean[][][] map, int n) {
        for (int y = 0; y <= n; y++) {
            for (int x = 0; x <= n; x++) {
                if (map[y][x][0] && !canPlaceColumn(map, x, y)) return false;
                if (map[y][x][1] && !canPlaceBeam(map, x, y)) return false;
            }
        }
        return true;
    }

    // 기둥 설치 조건
    private static boolean canPlaceColumn(boolean[][][] map, int x, int y) {
        // 바닥에 설치 or 다른 기둥 위에 있거나 or 보의 왼쪽 또는 오른쪽 위에 있거나
        return y == 0 || (y > 0 && map[y - 1][x][0]) || (x > 0 && map[y][x - 1][1]) || map[y][x][1];
    }

    // 보 설치 조건
    private static boolean canPlaceBeam(boolean[][][] map, int x, int y) {
        // 보의 왼쪽 부분이 기둥 위에 or 보의 오른쪽 부분이 기둥 위에 or 양 옆에 보가 있는 경우
        return (y > 0 && map[y - 1][x][0]) || (y > 0 && map[y - 1][x + 1][0]) || (x > 0 && map[y][x - 1][1] && map[y][x + 1][1]);
    }
}
