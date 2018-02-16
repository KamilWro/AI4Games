package MeanMax.controllers;

import MeanMax.*;

import java.util.List;
import java.util.Map;

public class ReaperController implements LooterController {

    @Override
    public String getMove(PlayerGame myPlayerGame, List<Target> wrecks, List<Target> tankers, Map<Integer, PlayerGame> playersGame) {
        Looter reaper = myPlayerGame.getReaper();
        Looter destroyer = myPlayerGame.getDestroyer();

        List<Target> sortWrecks = Util.sortTargets(reaper, wrecks, tankers, wrecks, playersGame);
        Target wreck = Util.findTarget(sortWrecks);
        if (wreck != null) {
            Coordinate coordinate = getBestPosition(wreck, wrecks);
            return Util.getMove(coordinate, reaper.getCoordinate(), reaper.getSpeed());
        }
        return Util.getMove(destroyer.getCoordinate(), reaper.getCoordinate(), reaper.getSpeed());
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
}
