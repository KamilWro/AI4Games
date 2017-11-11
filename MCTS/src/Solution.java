import java.util.Scanner;

public class Solution {

    public static void main(String args[]) {
        MCTSTree root = new MCTSTree(0, 0);
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        float c = in.nextFloat();
        for (int i = 0; i < n; i++) {
            String playout = in.next();
            float score = in.nextFloat();
            root.addChild(playout, score);
        }
        System.out.println(root.getSequence(c));
    }
}
