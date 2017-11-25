package MeanMax.targets;

import MeanMax.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Tanker extends Target {
    private int extra2;
    private float mass;

    public Tanker(int unitId, float mass, int radius, Coordinate coordinate, Coordinate speed, int extra, int extra2) {
        super(unitId, radius, coordinate, speed, extra);
        this.extra2 = extra2;
        this.mass = mass;
    }

    @Override
    public BigDecimal getRate(Looter destroyer, List<Target> wrecks, List<Target> tankers, Map<Integer, PlayerGame> playersGame) {
        Coordinate destroyerCoordinate = destroyer.getCoordinate();
        Coordinate destroyerSpeed = destroyer.getSpeed();

        BigDecimal rateDistance = divide("-1", "120");
        BigDecimal rateRadius = divide("0.01", "10");
        BigDecimal rateExtra = divide("100", "8").multiply(new BigDecimal("0.4"));
        BigDecimal rateEmptySpace = new BigDecimal("-100").divide(BigDecimal.valueOf(extra2), 10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("0.6"));
        BigDecimal rateDirection = getRDirection(destroyerCoordinate, destroyerSpeed);
        BigDecimal rateCollection = new BigDecimal("1.1");
        BigDecimal rateCenter = new BigDecimal("-0.01").divide(new BigDecimal("60"), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal ratePopularity = new BigDecimal("-2");
        BigDecimal rateTankersNumber = new BigDecimal("2");

        BigDecimal collection = new BigDecimal(getCollection(wrecks));
        BigDecimal distance = new BigDecimal(Util.getDistance(coordinate, destroyerCoordinate));
        BigDecimal center = new BigDecimal(Util.getDistance(new Coordinate(0, 0), coordinate));
        int popularity = getLootersNumber(coordinate, playersGame);
        int tankersNumber = getTankersNumber(coordinate, tankers);
        int emptySpace = extra2 - extra;

        BigDecimal result = rateDistance.multiply(distance);
        result = result.add(rateRadius.multiply(BigDecimal.valueOf(radius)));
        result = result.add(rateExtra.multiply(BigDecimal.valueOf(extra)));
        result = result.add(rateEmptySpace.multiply(BigDecimal.valueOf(emptySpace)));
        result = result.add(center.multiply(rateCenter));
        result = result.add(ratePopularity.multiply(BigDecimal.valueOf(popularity)));
        result = result.add(rateTankersNumber.multiply(BigDecimal.valueOf(tankersNumber)));
        result = result.add(collection.multiply(rateCollection));
        return result.multiply(rateDirection);
    }

    private BigDecimal getRDirection(Coordinate reaperCoordinate, Coordinate reaperSpeed) {
        return BigDecimal.valueOf((Util.isSameDirection(reaperCoordinate, reaperSpeed, coordinate)) ? 1.0 : 0.8);
    }

    public float getMass() {
        return mass;
    }
}
