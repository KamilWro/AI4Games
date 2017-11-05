package greatescape.astar.util;

import greatescape.graph.Node;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kamil Breczko
 */
public class MapUtil {
    public static Map<Node, Integer> sortByValue(Map<Node, Integer> map) {
        return map.entrySet()
                .stream()
                .sorted(((entry1, entry2) -> {
                    int diff = entry1.getValue() - entry2.getValue();
                    if (diff == 0) {
                        return entry1.getKey().hashCode() - entry2.getKey().hashCode();
                    }
                    return diff;
                }))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
