package MeanMax;

import MeanMax.controllers.LooterController;

import java.util.List;
import java.util.Map;

public class PlayerGame {
    private int score;
    private int rage;
    private Looter destroyer = new Looter();
    private Looter reaper = new Looter();
    private Looter doof = new Looter();

    public String getLooterMove(LooterController looterController, List<Target> wrecks, List<Target> tankers, Map<Integer, PlayerGame> playersGame) {
        return looterController.getMove(this, wrecks, tankers, playersGame);
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRage() {
        return rage;
    }

    public void setRage(int rage) {
        this.rage = rage;
    }
}
