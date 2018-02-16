package MeanMax.controllers;

import MeanMax.*;

import java.util.List;
import java.util.Map;

public class DoofController implements LooterController {

    @Override
    public String getMove(PlayerGame myPlayerGame, List<Target> wrecks, List<Target> tankers, Map<Integer, PlayerGame> playersGame) {
        Looter doof = myPlayerGame.getDoof();
        Looter reaper = myPlayerGame.getReaper();
        int rage = myPlayerGame.getRage();
        PlayerGame winner = getWinner(myPlayerGame, playersGame);
        Coordinate winnerCoordinate = winner.getReaper().getCoordinate();

        doof.nextTurn();

        if (rage > 30 && doof.availableSkill() && !Util.isNear(winner.getReaper(), reaper) && isWreck(wrecks, winnerCoordinate)) {
            doof.setSkill();
            return Util.getSkill(winnerCoordinate);
        }

        Looter winnerReaper = winner.getReaper();
        Coordinate coordinate = winnerReaper.getCoordinate();
        return Util.getMove(coordinate, doof.getCoordinate(), doof.getSpeed());
    }

    private boolean isWreck(List<Target> wrecks, Coordinate coordinate) {
        for (Target wreck : wrecks) {
            if (Util.getDistance(coordinate, wreck.getCoordinate()) < wreck.getRadius() * 2) {
                return true;
            }
        }
        return false;
    }

    private PlayerGame getWinner(PlayerGame myPlayerGame, Map<Integer, PlayerGame> playersGame) {
        int maxScore = Integer.MIN_VALUE;
        PlayerGame winner = null;
        for (Map.Entry<Integer, PlayerGame> entry : playersGame.entrySet()) {
            int scorePlayer = entry.getValue().getScore();
            if (scorePlayer > maxScore && !entry.getValue().equals(myPlayerGame)) {
                winner = entry.getValue();
                maxScore = scorePlayer;
            }
        }
        return winner;
    }
}
