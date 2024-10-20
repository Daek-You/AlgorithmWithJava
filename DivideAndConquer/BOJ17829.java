import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 메모리: 90864KB
 * 시간: 564ms
 */

public class BOJ17829 {
	private static int N;
	private static int[][] Map;
	
	public static void main(String[] args) throws Exception {

		// 1. 입력 받기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		Map = new int[N][N];
		
		for (int row = 0; row < N; row++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int col = 0; col < N; col++)	Map[row][col] = Integer.parseInt(st.nextToken());
		}
		
		int length = N;
		int[][] origin = Map;
		
		while (length > 2) {
			int halfLength = length / 2;
			int[][] result = new int[halfLength][halfLength];

			divide(origin, result, length, 0, 0);
			origin = result;
			length = halfLength;
		}
		
		// 최종적으로 2x2 행렬이 만들어졌다면, 이 중에서 두 번째로 큰 값을 찾아 출력
		int answer = getSecondMaxValue(origin, 0, 0);
		System.out.println(answer);
		br.close();
	}
	
	private static void divide(int[][] origin, int[][] result, int length, int startRow, int startCol) {
		// 2x2 행렬이 됐다면
	    if (length == 2) {
	        int resultRow = startRow / 2, resultCol = startCol / 2;
	        result[resultRow][resultCol] = getSecondMaxValue(origin, startRow, startCol);
	        return;
	    }
		
		int half = length / 2;
		divide(origin, result, half, startRow, startCol);				        // 왼쪽 위
		divide(origin, result, half, startRow, startCol + half);		    // 오른쪽 위
		divide(origin, result, half, startRow + half, startCol);		    // 왼쪽 아래
		divide(origin, result, half, startRow + half, startCol + half);	// 오른쪽 아래
	}
	
	private static int getSecondMaxValue(int[][] arr, int leftUpRow, int leftUpCol) {
		// 최솟값이 -10,000이라서 그냥 좀 더 여유있게 작게 줌
		int maxValue = -100_000, secondMaxValue = -100_001;
		
		for (int row = leftUpRow; row < leftUpRow + 2; row++) {
			for (int col = leftUpCol; col < leftUpCol + 2; col++) {
				// 기존 최댓값보다 더 큰 값을 발견했다면 -> 갱신
				if (maxValue < arr[row][col]) {
					secondMaxValue = maxValue;		// 현재 값이 두 번째로 큰 값이 되고
					maxValue = arr[row][col];		  // 새로운 값이 제일 큰 값이 됨
				}
				
				// 기존 최댓값보단 작지만, 기존에 있던 두 번째 값보다 큰 경우 -> 갱신
				else if (secondMaxValue < arr[row][col]) {
					secondMaxValue = arr[row][col];
				}
			}
		}

		return secondMaxValue;
	}
}