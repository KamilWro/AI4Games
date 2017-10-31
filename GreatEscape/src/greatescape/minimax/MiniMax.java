package greatescape.minimax;

import greatescape.Gamer;
import greatescape.astar.AStar;
import greatescape.graph.Edge;
import greatescape.graph.GraphUtil;
import greatescape.graph.Node;
import greatescape.movement.Move;
import greatescape.movement.Wall;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MiniMax {
    private final List<Edge> edges;
    private List<Edge> currentEdges = new LinkedList<>();
    private List<Gamer> gamers = new LinkedList<>();
    private Gamer myGamer;

    public MiniMax(int width, int height, int myId) {
        this.edges = GraphUtil.buildBoard(width, height);
    }

    public Enum execute() throws Exception {
        Gamer winner = expectedResult(x -> y -> x > y);
        Enum result = null;
        if (gamers.size() == 1) {
            winner = currentWinner();
        }
        Gamer loser = expectedResult(x -> y -> x <= y);
        if (loser.equals(myGamer) && myGamer.getNumberOfWallsAvailable() > 0) {
            return insertWall(winner);
        }
        return Optional.ofNullable(result).orElse(movement());
    }

    private Move movement() throws Exception {
        List<Node> shortestPath = Optional.ofNullable(myGamer.getShortestPath()).orElse(getShortestPath(myGamer));
        Node myMovement = shortestPath.get(0);
        return GraphUtil.goTo(myGamer.getPosition(), myMovement);
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

    private Gamer currentWinner() throws Exception {
        List<Node> shortestPath = getShortestPath(myGamer);
        myGamer.setShortestPath(shortestPath);
        int shortestDistance = shortestPath.size();
        Gamer theBestGamer = myGamer;
        for (Gamer gamer : gamers) {
            shortestPath = getShortestPath(gamer);
            gamer.setShortestPath(shortestPath);
            if (shortestDistance > shortestPath.size()) {
                shortestDistance = shortestPath.size();
                theBestGamer = gamer;
            }
        }
        return theBestGamer;
    }

    private List<Node> getShortestPath(Gamer gamer) throws Exception {
        ShortestPath shortestPath = new AStar(currentEdges);
        shortestPath.solution(gamer.getPosition(), gamer.getFinishLine());
        return shortestPath.getPath();
    }

    private Wall insertWall(Gamer winner) throws Exception {
        List<Node> shortestPath = Optional.ofNullable(winner.getShortestPath()).orElse(getShortestPath(winner));
        Iterator<Node> iterator = shortestPath.iterator();
        Wall result;
        for (int i = 0; i < 3 && iterator.hasNext(); i++) {
            Node node = iterator.next();
            result = putWallForGamer(winner.getFinishLine(), node);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private Wall putWallForGamer(Move move, Node node) {
        Wall result = null;
        switch (move) {
            case RIGHT:
                result = putWallForGamerWhoGoesRight(node);
                break;
            case LEFT:
                result = putWallForGamerWhoGoesLeft(node);
                break;
            case DOWN:
                result = putWallForGamerWhoGoesDown(node);
                break;
            case UP:
                result = putWallForGamerWhoGoesUp(node);
                break;
        }
        return result;
    }

    private Wall putWallForGamerWhoGoesLeft(Node node) {
        if (isWallV(node) && isWallH(GraphUtil.shift(node, -1, 1))) {
            return Wall.getVertical(node.getX(), node.getY());
        } else if (isWallV(GraphUtil.shift(node, 0, -1)) && isWallH(GraphUtil.shift(node, -1, 0))) {
            return Wall.getVertical(node.getX(), node.getY() - 1);
        }
        return null;
    }

    private Wall putWallForGamerWhoGoesRight(Node node) {
        if (isWallV(GraphUtil.shift(node, 1, 0)) && isWallH(GraphUtil.shift(node, 0, 1))) {
            return Wall.getVertical(node.getX() + 1, node.getY());
        } else if (isWallV(GraphUtil.shift(node, 1, -1)) && isWallH(node)) {
            return Wall.getVertical(node.getX() + 1, node.getY() - 1);
        }
        return null;
    }

    private Wall putWallForGamerWhoGoesDown(Node node) {
        if (isWallH(GraphUtil.shift(node, 0, 1)) && isWallV(GraphUtil.shift(node, 1, 0))) {
            return Wall.getHorizontal(node.getX(), node.getY() + 1);
        } else if (isWallH(GraphUtil.shift(node, -1, 1)) && isWallV(node)) {
            return Wall.getHorizontal(node.getX() - 1, node.getY() + 1);
        }
        return null;
    }

    private Wall putWallForGamerWhoGoesUp(Node node) {
        if (isWallH(node) && isWallV(GraphUtil.shift(node, 1, -1))) {
            return Wall.getHorizontal(node.getX(), node.getY());
        } else if (isWallH(GraphUtil.shift(node, -1, 0)) && isWallV(GraphUtil.shift(node, 0, -1))) {
            return Wall.getHorizontal(node.getX() - 1, node.getY());
        }
        return null;
    }

    private boolean isWallH(Node node) {
        return currentEdges.contains(GraphUtil.getEdge(node.getX(), node.getY() - 1, node.getX(), node.getY()))
                && currentEdges.contains(GraphUtil.getEdge(node.getX() + 1, node.getY() - 1, node.getX() + 1, node.getY()));
    }

    private boolean isWallV(Node node) {
        return currentEdges.contains(GraphUtil.getEdge(node.getX() - 1, node.getY(), node.getX(), node.getY()))
                && currentEdges.contains(GraphUtil.getEdge(node.getX() - 1, node.getY() + 1, node.getX(), node.getY() + 1));
    }

    public void addWalls(List<Edge> walls) {
        currentEdges.clear();
        currentEdges.addAll(edges);
        currentEdges.removeAll(walls);
    }

    public void setGamers(List<Gamer> gamers) {
        this.gamers = gamers;
    }

    public void setMyGamer(Gamer myGamer) {
        this.myGamer = myGamer;
    }
}
