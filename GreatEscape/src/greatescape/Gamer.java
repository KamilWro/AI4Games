package greatescape;

import greatescape.graph.Node;
import greatescape.movement.Move;

import java.util.List;

/**
 * @author Kamil Breczko
 */
public class Gamer {
    private final int id;
    private final Node position;
    private final int numberOfWallsAvailable;
    private List<Node> shortestPath;
    private final Move finishLine;

    public Gamer(int id, Node position, int numberOfWallsAvailable, Move finishLine) {
        this.id = id;
        this.position = position;
        this.numberOfWallsAvailable = numberOfWallsAvailable;
        this.finishLine = finishLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gamer)) return false;

        Gamer gamer = (Gamer) o;

        if (getId() != gamer.getId()) return false;
        if (!getPosition().equals(gamer.getPosition())) return false;
        return getFinishLine() == gamer.getFinishLine();
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getPosition().hashCode();
        result = 31 * result + getFinishLine().hashCode();
        return result;
    }

    public Node getPosition() {
        return position;
    }

    public int getNumberOfWallsAvailable() {
        return numberOfWallsAvailable;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Move getFinishLine() {
        return finishLine;
    }

    private int getId() {
        return id;
    }
}
