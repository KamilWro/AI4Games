package MeanMax;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public abstract class Target {
    protected int unitId;
    protected int radius;
    protected Coordinate coordinate;
    protected Coordinate speed;
    protected int extra;

    public Target(int unitId, int radius, Coordinate coordinate, Coordinate speed, int extra) {
        this.unitId = unitId;
        this.radius = radius;
        this.coordinate = coordinate;
        this.extra = extra;
        this.speed = speed;
    }

    protected int getCollection(List<Target> wrecks) {
        int count = 0;
        for (Target wreck : wrecks) {
            Coordinate coordinateWreck = wreck.getCoordinate();
            if (Util.getDistance(coordinate, coordinateWreck) < (Math.abs(radius + wreck.getRadius())) * 2) {
                count += wreck.getExtra();
            }
        }
        return count;
    }

    protected int getLootersNumber(Coordinate coordinate, Map<Integer, PlayerGame> playersGame) {
        int count = 0;
        for (Map.Entry<Integer, PlayerGame> entry : playersGame.entrySet()) {
            if (entry.getKey() != 0) {
                Looter looters[] = new Looter[3];
                looters[0] = entry.getValue().getDestroyer();
                looters[1] = entry.getValue().getDoof();
                looters[2] = entry.getValue().getReaper();
                for (int i = 0; i < 3; i++) {
                    if (Util.getDistance(looters[i].getCoordinate(), coordinate) < 1000) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    protected int getTankersNumber(Coordinate coordinate, List<Target> tankers) {
        int count = 0;
        for (Target tanker : tankers) {
            if (Util.getDistance(tanker.getCoordinate(), coordinate) < 1000) {
                count++;
            }
        }
        return count;
    }

    protected BigDecimal divide(String x, String y) {
        return new BigDecimal(x).divide(new BigDecimal(y), 10, BigDecimal.ROUND_HALF_UP);
    }

    public abstract BigDecimal getRate(Looter destroyer, List<Target> wrecks, List<Target> tankers, Map<Integer, PlayerGame> playersGame);

    public int getRadius() {
        return radius;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getUnitId() {
        return unitId;
    }

    public int getExtra() {
        return extra;
    }
}
