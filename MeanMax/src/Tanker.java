public class Tanker {
    private int unitId;
    private float mass;
    private int radius;
    private Position position;
    private Speed speed;
    private int extra;
    private int extra2;

    public Tanker(int unitId, float mass, int radius, Position position, Speed speed, int extra, int extra2) {
        this.unitId = unitId;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.speed = speed;
        this.extra = extra;
        this.extra2 = extra2;
    }

    public double getRate(Destroyer destroyer) {
        double fpositon = 1.1;
        double fradius = 0.1;
        double fextra = 0.6;
        double fextra2 = (extra2 == extra) ? 1.0 : 0.5;
        Position reaperPosition = destroyer.getPosition();
        Speed reaperSpeed = destroyer.getSpeed();
        double direction = (Util.isSameDirection(reaperPosition, reaperSpeed, position)) ? 1.0 : 0.5;
        double d = Math.sqrt(
                Math.pow(position.getX() - reaperPosition.getX(), 2) +
                        Math.pow(position.getY() - reaperPosition.getY(), 2)
        );

        return (fpositon * d + fradius * radius + fextra * extra) * direction * fextra2;
    }

    public Position getPosition() {
        return position;
    }
}
