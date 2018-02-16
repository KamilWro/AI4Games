package MeanMax;

import MeanMax.controllers.DestroyerController;
import MeanMax.controllers.DoofController;
import MeanMax.controllers.ReaperController;
import MeanMax.targets.Tanker;
import MeanMax.targets.Wreck;

import java.util.*;

class Player {
    private static Map<Integer, PlayerGame> playersGame = new HashMap<>();
    private static List<Target> wrecks = new LinkedList<>();
    private static List<Target> tankers = new LinkedList<>();

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        ReaperController reaperController = new ReaperController();
        DestroyerController destroyerController = new DestroyerController();
        DoofController doofController = new DoofController();

        addPlayersGame();

        while (true) {
            tankers.clear();
            wrecks.clear();
            int score[] = new int[3];
            score[0] = in.nextInt();
            score[1] = in.nextInt();
            score[2] = in.nextInt();
            int rage[] = new int[3];
            rage[0] = in.nextInt();
            rage[1] = in.nextInt();
            rage[2] = in.nextInt();
            addStatistics(score, rage);
            int unitCount = in.nextInt();
            for (int i = 0; i < unitCount; i++) {
                int unitId = in.nextInt();
                int unitType = in.nextInt();
                int playerId = in.nextInt();
                float mass = in.nextFloat();
                int radius = in.nextInt();
                int x = in.nextInt();
                int y = in.nextInt();
                Coordinate coordinate = new Coordinate(x, y);
                int vx = in.nextInt();
                int vy = in.nextInt();
                Coordinate speed = new Coordinate(vx, vy);
                int extra = in.nextInt();
                int extra2 = in.nextInt();
                switch (unitType) {
                    case 0:
                        addReaper(unitId, playerId, mass, radius, coordinate, speed);
                        break;
                    case 1:
                        addDestroyer(unitId, playerId, mass, radius, coordinate, speed);
                        break;
                    case 2:
                        addDoof(unitId, playerId, mass, radius, coordinate, speed);
                        break;
                    case 3:
                        addTanker(unitId, mass, radius, coordinate, speed, extra, extra2);
                        break;
                    case 4:
                        addWreck(unitId, radius, coordinate, speed, extra);
                        break;
                }
            }

            System.out.println(playersGame.get(0).getLooterMove(reaperController, wrecks, tankers, playersGame));
            System.out.println(playersGame.get(0).getLooterMove(destroyerController, wrecks, tankers, playersGame));
            System.out.println(playersGame.get(0).getLooterMove(doofController, wrecks, tankers, playersGame));
        }
    }

    private static void addStatistics(int[] score, int[] rage) {
        for (int i = 0; i < 3; i++) {
            PlayerGame playerGame = playersGame.get(i);
            playerGame.setScore(score[i]);
            playerGame.setRage(rage[i]);
        }
    }

    private static void addDoof(int unitId, int playerId, float mass, int radius, Coordinate coordinate, Coordinate speed) {
        PlayerGame playerGame = playersGame.get(playerId);
        Looter doof = playerGame.getDoof();
        doof.setLimitSkill(3);
        doof.setCoordinate(coordinate);
        doof.setUnitId(unitId);
        doof.setSpeed(speed);
        doof.setMass(mass);
        doof.setRadius(radius);
    }

    private static void addWreck(int unitId, int radius, Coordinate coordinate, Coordinate speed, int extra) {
        Target wreck = new Wreck(unitId, radius, coordinate, speed, extra);
        wrecks.add(wreck);
    }

    private static void addTanker(int unitId, float mass, int radius, Coordinate coordinate, Coordinate speed, int extra, int extra2) {
        Target tanker = new Tanker(unitId, mass, radius, coordinate, speed, extra, extra2);
        tankers.add(tanker);
    }

    private static void addDestroyer(int unitId, int playerId, float mass, int radius, Coordinate coordinate, Coordinate speed) {
        PlayerGame playerGame = playersGame.get(playerId);
        Looter destroyer = playerGame.getDestroyer();
        destroyer.setLimitSkill(5);
        destroyer.setCoordinate(coordinate);
        destroyer.setUnitId(unitId);
        destroyer.setSpeed(speed);
        destroyer.setMass(mass);
        destroyer.setRadius(radius);
    }

    private static void addReaper(int unitId, int playerId, float mass, int radius, Coordinate coordinate, Coordinate speed) {
        PlayerGame playerGame = playersGame.get(playerId);
        Looter reaper = playerGame.getReaper();
        reaper.setLimitSkill(3);
        reaper.setCoordinate(coordinate);
        reaper.setUnitId(unitId);
        reaper.setSpeed(speed);
        reaper.setMass(mass);
        reaper.setRadius(radius);
    }

    private static void addPlayersGame() {
        for (int i = 0; i < 3; i++) {
            playersGame.put(i, new PlayerGame());
        }
    }


}