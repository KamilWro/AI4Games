package MiniMax;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Kamil Breczo
 */
class Solution {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int d = in.nextInt();
        int b = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        Deque<Integer> leafs = new LinkedList<>();
        for (int i = 0; i < Math.pow(b, d); i++) {
            leafs.add(in.nextInt());
        }
        TreeMiniMax miniMax = new TreeMiniMax(b);
        miniMax.setHeuristicValues(leafs);
        Integer score = miniMax.alfaBeta(Integer.MIN_VALUE, Integer.MAX_VALUE, Player.maximizingPlayer, d);
        Integer size = miniMax.getSizeTree();
        System.out.println(score + " " + size);
    }

}