import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ1504 {
	private static class Edge implements Comparable<Edge> {
		int node, distance;
		
		public Edge(int node, int distance) {
			this.node = node;
			this.distance = distance;
		}
		
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.distance, o.distance);
		}
	}
	
	private static final int INF = 1000_000_000;
	private static int NumOfNodes, NumOfEdges;
	private static ArrayList<Edge>[] Edges;
	private static int U, V;	// 통과해야 하는 두 정점
	
	private static int[] DistancesFromNode1;
	private static int[] DistancesFromU;
	private static int[] DistanceFromV;
	
	public static void main(String[] args) throws Exception {
		initialize();

		// 1. 시작지점에서 U, V 경유지로의 최단 경로 구하기
		dijkstra(DistancesFromNode1, 1);
		int startToU = DistancesFromNode1[U], startToV = DistancesFromNode1[V];
		
		// 2. U에서 V 및 N까지의 최단 경로 구하기
		dijkstra(DistancesFromU, U);
		int uToV = DistancesFromU[V], uToN = DistancesFromU[NumOfNodes];
		
		// 3. V에서 U 및 N까지의 최단 경로 구하기
		dijkstra(DistanceFromV, V);
		int vToU = DistanceFromV[U], vToN = DistanceFromV[NumOfNodes];
		
		// 4. 거리 계산
		int uRoute = startToU + uToV + vToN;
		int vRoute = startToV + vToU + uToN;
		int answer = Math.min(uRoute, vRoute);
		
		answer = (uToV == INF || vToU == INF || answer >= INF) ? -1 : answer;
		System.out.println(answer);
	}

	public static void dijkstra(int[] distances, int startNode) {
		// 거리 배열을 무한대 값으로 초기화
		Arrays.fill(distances, INF);

		// 시작 노드의 거리 값은 0으로 설정하고 우선순위 큐에 추가
		distances[startNode] = 0;
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.add(new Edge(startNode, 0));
		
		while (!pq.isEmpty()) {
			Edge current = pq.poll();

			// 기존에 더 작은 값으로 갈 수 있는 경로가 있다면 skip
			if (distances[current.node] < current.distance) {
				continue;
			}
			
			for (Edge other : Edges[current.node]) {
				int nextNode = other.node;
				int newDistance = current.distance + other.distance;
				
				// 기존 경로보다 더 적은 코스트로 갈 수 있는 경로가 있다면
				if (newDistance < distances[nextNode]) {
					distances[nextNode] = newDistance;
					pq.add(new Edge(nextNode, newDistance));
				}
			}
		}
	}
	
	private static void initialize() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		NumOfNodes = Integer.parseInt(st.nextToken());
		NumOfEdges = Integer.parseInt(st.nextToken());
		
		DistancesFromNode1 = new int[NumOfNodes + 1];
		DistancesFromU = new int[NumOfNodes + 1];
		DistanceFromV = new int[NumOfNodes + 1];
		
		Edges = new ArrayList[NumOfNodes + 1];
		for (int i = 1; i <= NumOfNodes; i++)	Edges[i] = new ArrayList<>();
		
		// 간선 정보 추가
		for (int i = 0; i < NumOfEdges; i++) {
			st = new StringTokenizer(br.readLine());
			
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int distance = Integer.parseInt(st.nextToken());
			
			// 양방항 간선이므로 둘 다 추가
			Edges[from].add(new Edge(to, distance));
			Edges[to].add(new Edge(from, distance));
		}
		
		// 반드시 거쳐야 하는 경유지 노드 입력 받기
		st = new StringTokenizer(br.readLine());
		U = Integer.parseInt(st.nextToken());
		V = Integer.parseInt(st.nextToken());
		
		br.close();
	}
}