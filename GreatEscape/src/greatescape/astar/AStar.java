package greatescape.astar;

import greatescape.astar.exception.AStarException;
import greatescape.astar.util.MapUtil;
import greatescape.graph.Edge;
import greatescape.graph.GraphUtil;
import greatescape.graph.Node;
import greatescape.minimax.ShortestPath;
import greatescape.movement.Move;

import java.util.*;

/**
 * @author Kamil Breczko
 */
public class AStar implements ShortestPath {
    private final List<Edge> edges;

    private Set<Node> closedSet = new LinkedHashSet<>();
    private Set<Node> openSet = new HashSet<>();

    private Map<Node, Integer> gScores = new HashMap<>();
    private Map<Node, Integer> fScores = new HashMap<>();

    private Map<Node, Node> predecessors = new HashMap<>();
    private Move finishLine;
    private Node goal;

    public AStar(List<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public void solution(Node start, Move finishLine) throws AStarException {
        this.finishLine = finishLine;
        int distance = getHScore(start);
        openSet.add(start);
        gScores.put(start, 0);
        fScores.put(start, getHScore(start));

        while (!openSet.isEmpty()) {
            Node current = findNodeWithMinimalFScore();
            openSet.remove(current);
            closedSet.add(current);
            if (getHScore(current) == (distance / 2)) {
                this.goal = current;
                return;
            }
            findNodesWithMinimalDistance(current);
        }

        throw new AStarException("Graph is incorrect");
    }

    private Node findNodeWithMinimalFScore() throws AStarException {
        Map<Node, Integer> sortedMap = MapUtil.sortByValue(fScores);
        for (Map.Entry<Node, Integer> entry : sortedMap.entrySet()) {
            if (openSet.contains(entry.getKey())) {
                return entry.getKey();
            }
        }

        throw new AStarException("No node in openSet");
    }

    private void findNodesWithMinimalDistance(Node node) throws AStarException {
        for (Node neighbor : getNeighbors(node)) {
            openSet.add(neighbor);
            Integer gScoreNeighbor = gScores.get(node) + getDistance(node, neighbor);
            if (gScoreNeighbor < getShortestDistance(neighbor)) {
                gScores.put(neighbor, gScoreNeighbor);
                fScores.put(neighbor, gScoreNeighbor + getHScore(neighbor));
                predecessors.put(neighbor, node);
            }
        }
    }

    private Iterable<Node> getNeighbors(Node node) {
        List<Node> neighbors = new LinkedList<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node) && !closedSet.contains(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            } else if (edge.getDestination().equals(node) && !closedSet.contains(edge.getSource())) {
                neighbors.add(edge.getSource());
            }
        }
        return neighbors;
    }

    private int getDistance(Node node, Node target) throws AStarException {
        Edge e = new Edge(node, target);
        for (Edge edge : edges) {
            if (edge.equals(e)) {
                return edge.getWeight();
            }
        }

        throw new AStarException("No edge from: " + node + "to: " + target);
    }

    private int getShortestDistance(Node target) {
        return Optional.ofNullable(gScores.get(target)).orElse(Integer.MAX_VALUE);
    }

    private Integer getHScore(Node x) {
        return GraphUtil.expectedDistance(x.getX(), x.getY(), finishLine);
    }

    @Override
    public List<Node> getPath() {
        Node step = goal;
        LinkedList<Node> path = new LinkedList<>();
        if (predecessors.get(step) == null) {
            return Collections.EMPTY_LIST;
        }
        path.addFirst(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.addFirst(step);
        }
        path.removeFirst();
        return path;
    }
}
