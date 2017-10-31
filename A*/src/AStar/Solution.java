package AStar;

import AStar.exception.AStarException;
import AStar.graph.Edge;

import java.util.*;

/**
 * @author Kamil Breczko
 */
class Solution {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Map<Integer, Integer> hScores = new HashMap<>();
        List<Edge> edges = new ArrayList<>();

        int n = in.nextInt();
        int e = in.nextInt();
        int s = in.nextInt();
        int g = in.nextInt();

        for (int i = 0; i < n; i++) {
            int h = in.nextInt();
            hScores.put(i, h);
        }

        for (int i = 0; i < e; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int c = in.nextInt();
            Edge edge = new Edge(x, y, c);
            edges.add(edge);
        }

        AStar aStar = new AStar(edges);
        aStar.setHScore(hScores);

        try {
            trySolutionAndPrint(aStar, s, g);
        } catch (AStarException e1) {
            e1.printStackTrace();
        }
    }

    private static void trySolutionAndPrint(AStar aStar, Integer s, Integer g) throws AStarException {
        aStar.solution(s, g);
        Map<Integer, Integer> result = aStar.getClosedSetWithFScore();

        for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}