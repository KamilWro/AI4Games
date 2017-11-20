import java.util.List;

public class Wreck {
    private int unitId;
    private int radius;
    private Position position;
    private Speed speed;
    private int extra;

    public Wreck(int unitId, int radius, Position position, Speed speed, int extra) {
        this.unitId = unitId;
        this.radius = radius;
        this.position = position;
        this.extra = extra;
        this.speed = speed;
    }

    public Position getPosition() {
        return position;
    }

    public double getRate(Reaper reaper, List<Wreck> wrecks) {
        Position reaperPosition = reaper.getPosition();
        Speed reaperSpeed = reaper.getSpeed();

        // all results are range from 0 to 100
        double rPositon = 0.02;
        double rRadius = 0.1;
        double rExtra = 2;
        double rDirection = getRDirection(reaperPosition, reaperSpeed);
        double rCollection = getRCollection(wrecks);
        double distance = Util.getDistance(position, reaperPosition);

        return (rPositon * distance + rRadius * radius + rExtra * extra) * rDirection * rCollection;
        //result [0,100]
    }

    private double getRCollection(List<Wreck> wrecks) {
        int x = position.getX();
        int y = position.getY();
        int count = 0;
        for (Wreck wreck : wrecks) {
            Position position = wreck.position;
            if (Math.abs(x - position.getX()) < radius && Math.abs(y - position.getY()) < radius) {
                count++;
            }
        }
        return count * 0.3;
    }

    private double getRDirection(Position reaperPosition, Speed reaperSpeed) {
        return (Util.isSameDirection(reaperPosition, reaperSpeed, position)) ? 1.0 : 0.7;
    }
}
