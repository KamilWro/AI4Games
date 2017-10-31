package greatescape.minimax;

import greatescape.graph.Node;
import greatescape.movement.Move;

import java.util.List;

public interface ShortestPath {
    void solution(Node start, Move target) throws Exception;

    List getPath();
}
