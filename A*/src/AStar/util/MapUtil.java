package AStar.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kamil Breczko
 */
public class MapUtil {
    /**
     * Sortowanie kolekcji map od najmniejszej wartości do największej. W przypadku gdy wartości
     * są równe pierwszym wystąpieniem powinna być para z mniejszym kluczem.
     */
    public static Map<Integer, Integer> sortByValue(Map<Integer, Integer> map) {
        return map.entrySet()
                .stream()
                .sorted(((entry1, entry2) -> {
                    int diff = entry1.getValue() - entry2.getValue();
                    if (diff == 0) {
                        return entry1.getKey() - entry2.getKey();
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
