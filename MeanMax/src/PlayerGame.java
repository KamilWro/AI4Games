import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class PlayerGame {
    private int score;
    private int rage;
    private Destroyer destroyer;
    private Reaper reaper;

    public PlayerGame(int score, int rage) {
        this.score = score;
        this.rage = rage;
    }

    public String getMoveReaper(List<Wreck> wrecks) {
        List<Wreck> sortWrecks = sortWrecks(wrecks);
        Position target = findTargetWreck(sortWrecks);
        if (target != null) {
            Speed speed = reaper.getSpeed();
            int x = target.getX() - (int) 1.5 * speed.getVx();
            int y = target.getY() - (int) 1.5 * speed.getVy();
            int throttle = (int) Util.getDistance(target, reaper.getPosition());
            return x + " " + y + " " + throttle;
        }
        Position position = destroyer.getPosition();
        return (position.getX() - 1000) + " " + (position.getY() - 1000) + " 200";
    }

    public String getMoveDestroyer(List<Tanker> tankers) {
        List<Tanker> sortTankers = sortTankers(tankers);
        Position target = findTargetTanker(sortTankers);
        if (target != null) {
            Speed speed = destroyer.getSpeed();
            int x = target.getX() - (int) 1.5 * speed.getVx();
            int y = target.getY() - (int) 1.5 * speed.getVy();
            int throttle = (int) Util.getDistance(target, destroyer.getPosition());
            return x + " " + y + " " + throttle;
        }
        return "WAIT";
    }

    private List<Wreck> sortWrecks(List<Wreck> wrecks) {
        List<Wreck> sortWrecks = new LinkedList<>();
        sortWrecks.addAll(wrecks);
        sortWrecks.sort(Comparator.comparingDouble(wreck -> wreck.getRate(reaper, wrecks)));
        return sortWrecks;
    }

    private List<Tanker> sortTankers(List<Tanker> tankers) {
        List<Tanker> sortTankers = new LinkedList<>();
        sortTankers.addAll(tankers);
        sortTankers.sort(Comparator.comparingDouble(tanker -> tanker.getRate(destroyer)));
        return sortTankers;
    }

    private Position findTargetWreck(List<Wreck> wrecks) {
        for (Wreck wreck : wrecks) {
            return wreck.getPosition();
        }
        return null;
    }

    private Position findTargetTanker(List<Tanker> tankers) {
        for (Tanker tanker : tankers) {
            return tanker.getPosition();
        }
        return null;
    }

    public void setReaper(Reaper reaper) {
        this.reaper = reaper;
    }

    public void setDestroyer(Destroyer destroyer) {
        this.destroyer = destroyer;
    }
}
