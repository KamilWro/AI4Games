package AStar.graph;

/**
 * @author Kamil Breczko
 */
public class Edge {
    private final Integer source;
    private final Integer destination;
    private final int weight;

    public Edge(Integer source, Integer destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Integer getDestination() {
        return destination;
    }

    public Integer getSource() {
        return source;
    }

    public int getWeight() {
        return weight;
    }
}
