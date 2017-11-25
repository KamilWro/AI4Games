package MeanMax.targets;

import MeanMax.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Wreck extends Target {

    public Wreck(int unitId, int radius, Coordinate coordinate, Coordinate speed, int extra) {
        super(unitId, radius, coordinate, speed, extra);
    }

    @Override
    public BigDecimal getRate(Looter reaper, List<Target> wrecks, List<Target> tankers, Map<Integer, PlayerGame> playersGame) {
        Coordinate reaperCoordinate = reaper.getCoordinate();
        Coordinate reaperSpeed = reaper.getSpeed();

        BigDecimal rateDistance = divide("-1", "120");
        BigDecimal rateRadius = divide("0.01", "10");
        BigDecimal rateExtra = divide("100", "8").multiply(new BigDecimal("0.3"));
        BigDecimal rateDirection = getRDirection(reaperCoordinate, reaperSpeed);
        BigDecimal rateCollection = new BigDecimal("0.8");
        BigDecimal rateCenter = divide("-0.01", "60");
        BigDecimal ratePopularity = new BigDecimal("-2");

        BigDecimal collection = new BigDecimal(getCollection(wrecks));
        BigDecimal distance = new BigDecimal(Util.getDistance(coordinate, reaperCoordinate));
        BigDecimal center = new BigDecimal(Util.getDistance(new Coordinate(0, 0), coordinate));
        int popularity = getPopularity(coordinate, wrecks, tankers, playersGame);

        BigDecimal result = rateDistance.multiply(distance);
        result = result.add(rateRadius.multiply(BigDecimal.valueOf(radius)));
        result = result.add(rateExtra.multiply(BigDecimal.valueOf(extra)));
        result = result.add(center.multiply(rateCenter));
        result = result.add(ratePopularity.multiply(BigDecimal.valueOf(popularity)));
        result = result.multiply(rateDirection);
        result = result.add(collection.multiply(rateCollection));
        return result;

    }

    private int getPopularity(Coordinate coordinate, List<Target> wrecks, List<Target> tankers, Map<Integer, PlayerGame> playersGame) {
        return getLootersNumber(coordinate, playersGame) + 2 * getTankersNumber(coordinate, tankers);
    }

    private BigDecimal getRDirection(Coordinate reaperCoordinate, Coordinate reaperSpeed) {
        return BigDecimal.valueOf((Util.isSameDirection(reaperCoordinate, reaperSpeed, coordinate)) ? 1.0 : 0.9);
    }
}
