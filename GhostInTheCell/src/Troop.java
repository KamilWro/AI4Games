class Troop {
    private int id;
    private int owner;
    private int fromFactory;
    private int targetFactory;
    private int cyborgs;
    private int distance;

    public Troop(int id, int owner, int fromFactory, int targetFactory, int cyborgs, int distance) {
        this.id = id;
        this.owner = owner;
        this.fromFactory = fromFactory;
        this.targetFactory = targetFactory;
        this.cyborgs = cyborgs;
        this.distance = distance;
    }

    public int getTargetFactory() {
        return targetFactory;
    }

    public int getCyborgs() {
        return cyborgs;
    }

    public int getOwner() {
        return owner;
    }
}