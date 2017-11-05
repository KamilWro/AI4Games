package greatescape.graph;

import greatescape.movement.Move;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphUtil {
    public static int expectedDistance(Integer x, Integer y, Move finishLine) {
        switch (finishLine) {
            case UP:
                return y;
            case DOWN:
                return Math.abs(y - 8);
            case LEFT:
                return x;
            case RIGHT:
                return Math.abs(x - 8);
        }
        return 0;
    }

    public static Move getDirection(Node from, Node to) {
        int x = from.getX() - to.getX();
        int y = from.getY() - to.getY();

        if (y == 0 && x < 0) {
            return Move.RIGHT;
        } else if (y == 0 && x > 0) {
            return Move.LEFT;
        } else if (y < 0) {
            return Move.DOWN;
        }
        return Move.UP;
    }

    public static Node shift(Node node, int x, int y) {
        return new Node(node.getX() + x, node.getY() + y);
    }

    public static Map<Node, List<Node>> buildNeighbourList(int width, int height) {
        Map<Node, List<Node>> neighbours = new HashMap<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Node node = new Node(i, j);
                List<Node> neighbourList = new LinkedList<>();
                if (i != 0)
                    neighbourList.add(new Node(i - 1, j));
                if (i != (width - 1))
                    neighbourList.add(new Node(i + 1, j));
                if (j != 0)
                    neighbourList.add(new Node(i, j - 1));
                if (j != (height - 1))
                    neighbourList.add(new Node(i, j + 1));
                neighbours.put(node, neighbourList);
            }
        }
        return neighbours;
    }
}
