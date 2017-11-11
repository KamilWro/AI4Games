import java.util.HashMap;
import java.util.Map;

public class MCTSTree {
    Map<Character, MCTSTree> children = new HashMap<>();
    private int visited;
    private double score;

    public MCTSTree(int visited, double score) {
        this.visited = visited;
        this.score = score;
    }

    public void addChild(String path, float score) {
        this.visited += 1;
        this.score += score;

        if (path.isEmpty()) {
            return;
        }

        Character node = path.charAt(0);
        String restPath = path.substring(1);

        if (children.containsKey(node)) {
            MCTSTree child = children.get(node);
            child.addChild(restPath, score);
        } else {
            MCTSTree child = new MCTSTree(1, score);
            children.put(node, child);
        }
    }

    public String getSequence(float c) {
        Character node = ' ';
        double max = Double.NEGATIVE_INFINITY;
        MCTSTree mctsTree = null;

        for (Map.Entry<Character, MCTSTree> entry : children.entrySet()) {
            double value = countUCB1(c, entry.getValue());
            if (value > max || (value == max && entry.getKey() < node)) {
                max = value;
                node = entry.getKey();
                mctsTree = entry.getValue();
            }
        }
        return mctsTree == null ? "" : node + mctsTree.getSequence(c);
    }

    public double countUCB1(double c, MCTSTree child) {
        return child.getScore() / child.getVisited() + c * Math.sqrt(Math.log(visited) / child.getVisited());
    }

    public int getVisited() {
        return visited;
    }

    public double getScore() {
        return score;
    }
}


