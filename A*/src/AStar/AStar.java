package AStar;

import AStar.exception.AStarException;
import AStar.graph.Edge;
import AStar.util.MapUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Kamil Breczko
 * Algorytm A* jest heurystycznym algorytmem służącym do znajdowania najkrótszej
 * ścieżki w grafie. Jest to algorytm zupełny i optymalny. Działanie algorytmu
 * oparte jest na minimalizacji funkcji celu, zdefiniowanej jako suma funkcji
 * kosztu g(x) oraz funkcji heurystycznej h(x). W każdym kroku algorytm A*
 * przedłuża już utworzoną ścieżkę o kolejny wierzchołek grafu, wybierając taki,
 * w którym wartość funkcji będzie najmniejsza.
 */

public class AStar {
    private final List<Edge> edges;

    private Set<Integer> closedSet = new LinkedHashSet<>();
    private Set<Integer> openSet = new HashSet<>();

    private Map<Integer, Integer> gScores = new HashMap<>();
    private Map<Integer, Integer> hScores = new HashMap<>();
    private Map<Integer, Integer> fScores = new HashMap<>();

    public AStar(List<Edge> edges) {
        this.edges = edges;
    }

    public void solution(Integer start, Integer goal) throws AStarException {
        openSet.add(start);
        gScores.put(start, 0);
        fScores.put(start, hScores.get(start));

        while (!openSet.isEmpty()) {
            Integer current = findNodeWithMinimalFScore();
            openSet.remove(current);
            closedSet.add(current);
            if (current.equals(goal)) {
                return;
            }
            findNodesWithMinimalDistance(current);
        }

        throw new AStarException("Graph is incorrect");
    }

    private Integer findNodeWithMinimalFScore() throws AStarException {
        Map<Integer, Integer> sortedMap = MapUtil.sortByValue(fScores);
        for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
            if (openSet.contains(entry.getKey())) {
                return entry.getKey();
            }
        }

        throw new AStarException("No node in openSet");
    }

    private void findNodesWithMinimalDistance(Integer node) throws AStarException {
        for (Integer neighbor : getNeighbors(node)) {
            openSet.add(neighbor);
            Integer gScoreNeighbor = gScores.get(node) + getDistance(node, neighbor);
            if (gScoreNeighbor < getShortestDistance(neighbor)) {
                gScores.put(neighbor, gScoreNeighbor);
                fScores.put(neighbor, gScoreNeighbor + hScores.get(neighbor));
            }
        }
    }

    private Iterable<Integer> getNeighbors(Integer node) {
        List<Integer> neighbors = new LinkedList<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node) && !closedSet.contains(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            } else if (edge.getDestination().equals(node) && !closedSet.contains(edge.getSource())) {
                neighbors.add(edge.getSource());
            }
        }
        return neighbors;
    }

    private int getDistance(Integer node, Integer target) throws AStarException {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
                return edge.getWeight();
            } else if (edge.getSource().equals(target) && edge.getDestination().equals(node)) {
                return edge.getWeight();
            }
        }

        throw new AStarException("No edge from: " + node + "to: " + target);
    }

    private int getShortestDistance(Integer target) {
        return Optional.ofNullable(gScores.get(target)).orElse(Integer.MAX_VALUE);
    }

    public void setHScore(Map<Integer, Integer> hScore) {
        this.hScores = hScore;
    }

    public Map<Integer, Integer> getClosedSetWithFScore() {
        return closedSet.stream().collect(Collectors.toMap(node -> node, node -> fScores.get(node), (a, b) -> b, LinkedHashMap::new));
    }
}
