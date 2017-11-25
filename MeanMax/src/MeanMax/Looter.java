package MeanMax;

public class Looter {
    private int unitId;
    private float mass;
    private int radius;
    private Coordinate coordinate;
    private Coordinate speed;
    private int limitSkill;
    private int limit = 0;


    public void nextTurn() {
        if (limit > 0) {
            limit--;
        }
    }

    public boolean availableSkill() {
        return limit == 0;
    }

    public void setSkill() {
        limit = limitSkill;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getSpeed() {
        return speed;
    }

    public void setSpeed(Coordinate speed) {
        this.speed = speed;
    }

    public int getLimitSkill() {
        return limitSkill;
    }

    public void setLimitSkill(int limitSkill) {
        this.limitSkill = limitSkill;
    }
}
