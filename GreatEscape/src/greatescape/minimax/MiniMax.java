package greatescape.minimax;

import greatescape.Gamer;
import greatescape.astar.AStar;
import greatescape.graph.GraphUtil;
import greatescape.graph.Node;
import greatescape.movement.Move;
import greatescape.movement.Wall;

import java.util.*;
import java.util.function.Function;

public class MiniMax {
    private Map<Node, List<Node>> neighbours;
    private List<Gamer> gamers = new LinkedList<>();
    private Gamer myGamer;

    public Enum execute() throws Exception {
        Enum result = null;
        Gamer winner = expectedResult(x -> y -> x > y);
        Gamer loser = expectedResult(x -> y -> x <= y);
        if (loser.equals(myGamer) && !winner.equals(myGamer) && myGamer.getNumberOfWallsAvailable() > 0) {
            result = addWall(winner);
        }
        return Optional.ofNullable(result).orElse(getMovement());
    }

    private Move getMovement() throws Exception {
        List<Node> shortestPath = getShortestPath(myGamer);
        Node myMovement = shortestPath.get(0);
        return GraphUtil.getDirection(myGamer.getPosition(), myMovement);
    }

    private Gamer expectedResult(Function<Integer, Function<Integer, Boolean>> compare) {
        Node position = myGamer.getPosition();
        int expectedDistance = GraphUtil.expectedDistance(position.getX(), position.getY(), myGamer.getFinishLine());
        Gamer expectedGamer = myGamer;
        for (Gamer gamer : gamers) {
            position = gamer.getPosition();
            if (compare.apply(expectedDistance).apply(GraphUtil.expectedDistance(position.getX(), position.getY(), gamer.getFinishLine()))) {
                expectedGamer = gamer;
                expectedDistance = GraphUtil.expectedDistance(position.getX(), position.getY(), gamer.getFinishLine());
            }
        }
        return expectedGamer;
    }

    private List<Node> getShortestPath(Gamer gamer) throws Exception {
        ShortestPath shortestPath = new AStar(neighbours);
        shortestPath.solution(gamer.getPosition(), gamer.getFinishLine());
        return shortestPath.getPath();
    }

    private Wall addWall(Gamer winner) throws Exception {
        List<Node> shortestPath = getShortestPath(winner);
        Iterator<Node> iterator = shortestPath.iterator();
        Wall result;
        iterator.next();
        for (int i = 0; i < 4 && iterator.hasNext(); i++) {
            Node node = iterator.next();
            result = addWallForGamer(winner.getFinishLine(), node);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private Wall addWallForGamer(Move move, Node node) {
        Wall result = null;
        switch (move) {
            case RIGHT:
                result = addWallForGamerWhoGoesRight(node);
                break;
            case LEFT:
                result = addWallForGamerWhoGoesLeft(node);
                break;
            case DOWN:
                result = addWallForGamerWhoGoesDown(node);
                break;
            case UP:
                result = addWallForGamerWhoGoesUp(node);
                break;
        }
        return result;
    }

    private Wall addWallForGamerWhoGoesLeft(Node node) {
        if (isPlaceForWallV(node) && isPlaceForWallH(GraphUtil.shift(node, -1, 1))) {
            return Wall.getVertical(node.getX(), node.getY());
        } else if (isPlaceForWallV(GraphUtil.shift(node, 0, -1)) && isPlaceForWallH(GraphUtil.shift(node, -1, 0))) {
            return Wall.getVertical(node.getX(), node.getY() - 1);
        }
        return null;
    }

    private Wall addWallForGamerWhoGoesRight(Node node) {
        if (isPlaceForWallV(GraphUtil.shift(node, 1, 0)) && isPlaceForWallH(GraphUtil.shift(node, 0, 1))) {
            return Wall.getVertical(node.getX() + 1, node.getY());
        } else if (isPlaceForWallV(GraphUtil.shift(node, 1, -1)) && isPlaceForWallH(node)) {
            return Wall.getVertical(node.getX() + 1, node.getY() - 1);
        }
        return null;
    }

    private Wall addWallForGamerWhoGoesDown(Node node) {
        if (isPlaceForWallH(GraphUtil.shift(node, 0, 1)) && isPlaceForWallV(GraphUtil.shift(node, 1, 0))) {
            return Wall.getHorizontal(node.getX(), node.getY() + 1);
        } else if (isPlaceForWallH(GraphUtil.shift(node, -1, 1)) && isPlaceForWallV(node)) {
            return Wall.getHorizontal(node.getX() - 1, node.getY() + 1);
        }
        return null;
    }

    private Wall addWallForGamerWhoGoesUp(Node node) {
        if (isPlaceForWallH(node) && isPlaceForWallV(GraphUtil.shift(node, 1, -1))) {
            return Wall.getHorizontal(node.getX(), node.getY());
        } else if (isPlaceForWallH(GraphUtil.shift(node, -1, 0)) && isPlaceForWallV(GraphUtil.shift(node, 0, -1))) {
            return Wall.getHorizontal(node.getX() - 1, node.getY());
        }
        return null;
    }

    private boolean isPlaceForWallH(Node node) {
        return isEdge(GraphUtil.shift(node, 0, -1), node) && isEdge(GraphUtil.shift(node, 1, -1), GraphUtil.shift(node, 1, 0));
    }

    private boolean isPlaceForWallV(Node node) {
        return isEdge(GraphUtil.shift(node, -1, 0), node) && isEdge(GraphUtil.shift(node, -1, 1), GraphUtil.shift(node, 0, 1));
    }

    private boolean isEdge(Node nodeA, Node nodeB) {
        List<Node> neighbourListA = Optional.ofNullable(neighbours.get(nodeA)).orElse(Collections.EMPTY_LIST);
        List<Node> neighbourListB = Optional.ofNullable(neighbours.get(nodeB)).orElse(Collections.EMPTY_LIST);
        return neighbourListA.contains(nodeB) && neighbourListB.contains(nodeA);
    }

    public void setGamers(List<Gamer> gamers) {
        this.gamers = gamers;
    }

    public void setMyGamer(Gamer myGamer) {
        this.myGamer = myGamer;
    }

    public void setNeighbours(Map<Node, List<Node>> neighbours) {
        this.neighbours = neighbours;
    }
}
