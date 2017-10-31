package greatescape.graph;

import greatescape.movement.Move;

import java.util.LinkedList;
import java.util.List;

public class GraphUtil {
    public static Edge getEdge(int x1, int y1, int x2, int y2) {
        return new Edge(new Node(x1, y1), new Node(x2, y2));
    }

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

    public static Move goTo(Node from, Node to) {
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

    public static List<Edge> buildBoard(int width, int height) {
        List<Edge> edges = new LinkedList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height - 1; j++) {
                edges.add(GraphUtil.getEdge(i, j, i, j + 1));
            }
        }
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width; j++) {
                edges.add(GraphUtil.getEdge(i, j, i + 1, j));
            }
        }
        return edges;
    }
}
