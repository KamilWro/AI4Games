package MeanMax.controllers;

import MeanMax.PlayerGame;
import MeanMax.Target;

import java.util.List;
import java.util.Map;

public interface LooterController {
    String getMove(PlayerGame myPlayerGame, List<Target> wrecks, List<Target> tankers, Map<Integer, PlayerGame> playersGame);
}
