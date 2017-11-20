public class Util {

    public static boolean isSameDirection(Position position, Speed speed, Position target) {
        if (position.getX() < target.getX()) {
            if ((position.getX() < 0 && speed.getVx() > 0) || (position.getX() > 0 && speed.getVx() < 0)) {
                return true;
            }
        }
        if (position.getX() > target.getX()) {
            if ((position.getX() < 0 && speed.getVx() < 0) || (position.getX() > 0 && speed.getVx() > 0)) {
                return true;
            }
        }
        if (position.getY() < target.getY()) {
            if ((position.getY() < 0 && speed.getVy() > 0) || (position.getY() > 0 && speed.getVy() < 0)) {
                return true;
            }
        }
        if (position.getY() > target.getY()) {
            if ((position.getY() < 0 && speed.getVy() < 0) || (position.getY() > 0 && speed.getVy() > 0)) {
                return true;
            }
        }
        return false;
    }

    public static double getDistance(Position position1, Position position2) {
        return Math.sqrt(
                Math.pow(position1.getX() - position2.getX(), 2) +
                        Math.pow(position2.getY() - position1.getY(), 2)
        );
    }

}
