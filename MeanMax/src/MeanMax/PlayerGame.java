package MeanMax;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PlayerGame {
    private int score;
    private int rage;
    private Looter destroyer = new Looter();
    private Looter reaper = new Looter();
    private Looter doof = new Looter();

    public String getMoveReaper(List<Target> wrecks, List<Target> tankers, Map<Integer, PlayerGame> playersGame) {
        List<Target> sortWrecks = Util.sortTargets(reaper, wrecks, tankers, wrecks, playersGame);
        Target wreck = Util.findTarget(sortWrecks);
        if (wreck != null) {
            Coordinate coordinate = getBestPosition(wreck, wrecks);
            return getMove(coordinate, reaper.getCoordinate(), reaper.getSpeed());
        }
        return getMove(destroyer.getCoordinate(), reaper.getCoordinate(), reaper.getSpeed());
    }

    public String getMoveDestroyer(List<Target> tankers) {
        destroyer.nextTurn();
        if (rage > 60 && destroyer.availableSkill() && isNearReaper(destroyer.getCoordinate())) {
            destroyer.setSkill();
            return getSkill(reaper.getCoordinate());
        }
        List<Target> sortTankers = Util.sortTargets(destroyer, tankers, Collections.EMPTY_LIST, tankers, Collections.EMPTY_MAP);
        Target tanker = Util.findTarget(sortTankers);
        if (tanker != null) {
            Coordinate coordinate = tanker.getCoordinate();
            return getMove(coordinate, destroyer.getCoordinate(), destroyer.getSpeed());
        }
        return "WAIT";
    }

    public String getMoveDoof(Map<Integer, PlayerGame> playersGame, List<Target> wrecks) {
        doof.nextTurn();
        PlayerGame winner = getWinner(playersGame);
        Coordinate winnerCoordinate = winner.reaper.getCoordinate();
        if (rage > 30 && doof.availableSkill() && !isNearReaper(winnerCoordinate) && isWreck(wrecks, winnerCoordinate)) {
            doof.setSkill();
            return getSkill(winnerCoordinate);
        }
        Looter winnerReaper = winner.getReaper();
        Coordinate coordinate = winnerReaper.getCoordinate();
        return getMove(coordinate, doof.getCoordinate(), doof.getSpeed());
    }

    private Coordinate getBestPosition(Target wreck, List<Target> wrecks) {
        Coordinate wreckPosition = wreck.getCoordinate();
        for (Target target : wrecks) {
            Coordinate targetCoordinate = target.getCoordinate();
            if (wreck.getUnitId() != target.getUnitId() && Util.getDistance(wreckPosition, targetCoordinate) < (Math.abs(wreck.getRadius() + target.getRadius()))) {
                int x = (wreckPosition.getX() + targetCoordinate.getX()) / 2;
                int y = (wreckPosition.getY() + targetCoordinate.getY()) / 2;
                return new Coordinate(x, y);
            }
        }
        return wreck.getCoordinate();
    }

    private boolean isWreck(List<Target> wrecks, Coordinate coordinate) {
        for (Target wreck : wrecks) {
            if (Util.getDistance(coordinate, wreck.getCoordinate()) < wreck.getRadius() * 2) {
                return true;
            }
        }
        return false;
    }

    private boolean isNearReaper(Coordinate coordinate) {
        double distance = Util.getDistance(coordinate, reaper.getCoordinate());
        return distance < 1500 && distance > 0;
    }

    private PlayerGame getWinner(Map<Integer, PlayerGame> playersGame) {
        int maxScore = Integer.MIN_VALUE;
        PlayerGame winner = null;
        for (Map.Entry<Integer, PlayerGame> entry : playersGame.entrySet()) {
            int scorePlayer = entry.getValue().score;
            if (scorePlayer > maxScore && entry.getValue() != this) {
                winner = entry.getValue();
                maxScore = scorePlayer;
            }
        }
        return winner;
    }

    private String getSkill(Coordinate coordinate) {
        return "SKILL " + coordinate.getX() + " " + coordinate.getY();
    }

    private String getMove(Coordinate coordinate, Coordinate myCoordinate, Coordinate speed) {
        int x = coordinate.getX() - (int) 1.5 * speed.getX();
        int y = coordinate.getY() - (int) 1.5 * speed.getY();
        int throttle = (int) Util.getDistance(coordinate, myCoordinate);
        return x + " " + y + " " + throttle;
    }

    public Looter getDestroyer() {
        return destroyer;
    }

    public Looter getReaper() {
        return reaper;
    }

    public Looter getDoof() {
        return doof;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setRage(int rage) {
        this.rage = rage;
    }
}
