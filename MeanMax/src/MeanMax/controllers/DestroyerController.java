package MeanMax.controllers;

import MeanMax.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DestroyerController implements LooterController {

    @Override
    public String getMove(PlayerGame myPlayerGame, List<Target> wrecks, List<Target> tankers, Map<Integer, PlayerGame> playersGame) {
        Looter destroyer = myPlayerGame.getDestroyer();
        Looter reaper = myPlayerGame.getReaper();

        destroyer.nextTurn();

        if (myPlayerGame.getRage() > 60 && destroyer.availableSkill() && Util.isNear(destroyer, reaper)) {
            destroyer.setSkill();
            return Util.getSkill(reaper.getCoordinate());
        }

        List<Target> sortTankers = Util.sortTargets(destroyer, tankers, Collections.EMPTY_LIST, tankers, Collections.EMPTY_MAP);
        Target tanker = Util.findTarget(sortTankers);

        if (tanker != null) {
            Coordinate coordinate = tanker.getCoordinate();
            return Util.getMove(coordinate, destroyer.getCoordinate(), destroyer.getSpeed());
        }

        return "WAIT";
    }

}
