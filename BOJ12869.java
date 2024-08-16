import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

class SCVGroup {
    int scv1, scv2, scv3;
    int attackCount;

    public SCVGroup(int scv1, int scv2, int scv3, int attackCount) {
        this.scv1 = scv1;
        this.scv2 = scv2;
        this.scv3 = scv3;
        this.attackCount = attackCount;
    }
}

public class BOJ12869 {
    private static int N;
    private static int[] Health;
    private static boolean[][][] Visited;
    private static ArrayList<ArrayList<Integer>> DamagePermuatations;

    public static void main(String[] args) throws Exception {
        initialize();
        findMinAttackCount();
    }

    private static void findMinAttackCount() {
        Queue<SCVGroup> SCVQueue = new LinkedList<>();
        SCVQueue.add(new SCVGroup(0, 0, 0, 0));

        while (!SCVQueue.isEmpty()) {
            SCVGroup group = SCVQueue.poll();

            for (ArrayList<Integer> element : DamagePermuatations) {
                int nextSCV1 = Math.min(Health[0], group.scv1 + element.get(0));
                int nextSCV2 = Math.min(Health[1], group.scv2 + element.get(1));
                int nextSCV3 = Math.min(Health[2], group.scv3 + element.get(2));

                if (Visited[nextSCV1][nextSCV2][nextSCV3]) {
                    continue;
                }

                if (nextSCV1 >= Health[0] && nextSCV2 >= Health[1] && nextSCV3 >= Health[2]) {
                    System.out.println(group.attackCount + 1);
                    return;
                }

                Visited[nextSCV1][nextSCV2][nextSCV3] = true;
                SCVQueue.add(new SCVGroup(nextSCV1, nextSCV2, nextSCV3, group.attackCount + 1));
            }
        }
    }

    private static void initialize() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        Health = new int[3];

        String[] args = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            Health[i] = Integer.parseInt(args[i]);
        }

        Visited = new boolean[Health[0] + 1][Health[1] + 1][Health[2] + 1];
        DamagePermuatations = new ArrayList<>();

        ArrayList<Integer> damages = new ArrayList<>() {{
            add(9);
            add(3);
            add(1);
        }};

        permutation(damages, 0);
        br.close();
    }

    private static void permutation(ArrayList<Integer> damages, int start) {
        if (start == damages.size() - 1) {
            DamagePermuatations.add(new ArrayList<>(damages));
            return;
        }

        for (int i = start; i < damages.size(); i++) {
            Collections.swap(damages, start, i);
            permutation(damages, start + 1);
            Collections.swap(damages, i, start);
        }
    }
}

