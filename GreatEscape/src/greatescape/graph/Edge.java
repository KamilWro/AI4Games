package greatescape.graph;

/**
 * @author Kamil Breczko
 */
public class Edge {
    private final Node source;
    private final Node destination;
    private int weight = 1;

    public Edge(Node source, Node destination) {
        this.source = source;
        this.destination = destination;
    }

    public Node getDestination() {
        return destination;
    }

    public Node getSource() {
        return source;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;

        Edge edge = (Edge) o;

        if (!getSource().equals(edge.getSource()) && !getDestination().equals(edge.getSource()))
            return false;
        return getDestination().equals(edge.getDestination()) || getSource().equals(edge.getDestination());
    }

    @Override
    public int hashCode() {
        return getSource().hashCode() + getDestination().hashCode();
    }
}

