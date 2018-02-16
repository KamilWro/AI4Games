package MeanMax;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Util {

    public static boolean isSameDirection(Coordinate coordinate, Coordinate speed, Coordinate target) {
        if (coordinate.getX() < target.getX()) {
            if ((coordinate.getX() < 0 && speed.getX() > 0) || (coordinate.getX() > 0 && speed.getX() < 0)) {
                return true;
            }
        }
        if (coordinate.getX() > target.getX()) {
            if ((coordinate.getX() < 0 && speed.getX() < 0) || (coordinate.getX() > 0 && speed.getX() > 0)) {
                return true;
            }
        }
        if (coordinate.getY() < target.getY()) {
            if ((coordinate.getY() < 0 && speed.getY() > 0) || (coordinate.getY() > 0 && speed.getY() < 0)) {
                return true;
            }
        }
        if (coordinate.getY() > target.getY()) {
            if ((coordinate.getY() < 0 && speed.getY() < 0) || (coordinate.getY() > 0 && speed.getY() > 0)) {
                return true;
            }
        }
        return false;
    }

    public static double getDistance(Coordinate coordinate1, Coordinate coordinate2) {
        return Math.sqrt(
                Math.pow(coordinate1.getX() - coordinate2.getX(), 2) +
                        Math.pow(coordinate1.getY() - coordinate2.getY(), 2)
        );
    }

    public static List<Target> sortTargets(Looter looter, List<Target> targetsToSort, List<Target> wrecks, List<Target> tankers, Map<Integer, PlayerGame> playersGame) {
        List<Target> sortTargets = new LinkedList<>();
        sortTargets.addAll(targetsToSort);
        sortTargets.sort(Comparator.comparing(target2 -> target2.getRate(looter, wrecks, tankers, playersGame).negate()));
        return sortTargets;
    }

    public static Target findTarget(List<Target> targets) {
        return targets.stream().findFirst().orElse(null);
    }

    public static String getSkill(Coordinate coordinate) {
        return "SKILL " + coordinate.getX() + " " + coordinate.getY();
    }

    public static String getMove(Coordinate coordinate, Coordinate myCoordinate, Coordinate speed) {
        int x = coordinate.getX() - (int) (1.5 * speed.getX());
        int y = coordinate.getY() - (int) (1.5 * speed.getY());
        int throttle = (int) Util.getDistance(coordinate, myCoordinate);
        return x + " " + y + " " + throttle;
    }

    public static boolean isNear(Looter looter1, Looter looter2) {
        double distance = Util.getDistance(looter1.getCoordinate(), looter2.getCoordinate());
        return distance < 1500 && distance > 0;
    }
}
