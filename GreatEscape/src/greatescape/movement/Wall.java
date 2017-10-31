package greatescape.movement;

/**
 * @author Kamil Breczko
 */
public enum Wall {
    VERTICAL("V"),
    HORIZONTAL("H");

    private String command;
    private int x = 0;
    private int y = 0;

    Wall(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + command;
    }

    public static Wall getHorizontal(int x, int y) {
        Wall wall = Wall.HORIZONTAL;
        wall.x = x;
        wall.y = y;
        return wall;
    }

    public static Wall getVertical(int x, int y) {
        Wall wall = Wall.VERTICAL;
        wall.x = x;
        wall.y = y;
        return wall;
    }
}
