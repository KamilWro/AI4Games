public class Reaper {
    private int unitId;
    private float mass;
    private int radius;
    private Position position;
    private Speed speed;

    public Reaper(int unitId, float mass, int radius, Position position, Speed speed) {
        this.unitId = unitId;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.speed = speed;
    }

    public Position getPosition() {
        return position;
    }

    public Speed getSpeed() {
        return speed;
    }
}
