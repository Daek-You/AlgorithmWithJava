import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ2179 {
    private static class Node {
        Node[] next = new Node[26];
        int index;  // 이 노드에 도달하는 단어의 인덱스
        
        Node(int index) {
            this.index = index;
        }
    }

    private static String[] Words;
    private static int Max;
    private static int[] answer = new int[2];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Words = new String[N];
        Node root = new Node(-1);
        
        for (int i = 0; i < N; i++) {
            Words[i] = br.readLine();
            Node current = root;
            
            // 각 문자에 대해 트라이 구성하면서 동시에 최대 접두사 검사
            for (int j = 0; j < Words[i].length(); j++) {
                int charIndex = Words[i].charAt(j) - 'a';
                
                if (current.next[charIndex] == null) {
                    current.next[charIndex] = new Node(i);
                } else {
                    // 현재까지의 깊이가 최대 접두사 길이와 같거나 큰 경우
                    if (j + 1 >= Max && !Words[i].equals(Words[current.next[charIndex].index])) {
                        if (j + 1 > Max) {
                            Max = j + 1;
                            answer[0] = current.next[charIndex].index;
                            answer[1] = i;
                        } 

                        // 같은 길이일 때는 더 앞선 순서 선택
                        else if (j + 1 == Max) {
                            if (current.next[charIndex].index < answer[0]) {
                                answer[0] = current.next[charIndex].index;
                                answer[1] = i;
                            }
                        }
                    }
                }

                current = current.next[charIndex];
            }
        }
        
        // 아무 접두사도 없는 경우 처리
        if (Max == 0) {
            answer[0] = 0;
            answer[1] = 1;
        }
        
        System.out.println(Words[answer[0]]);
        System.out.println(Words[answer[1]]);
        br.close();
    }
}