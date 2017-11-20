import java.util.*;

class Player {
    private static Map<Integer, PlayerGame> playersGame = new HashMap<>();
    private static List<Wreck> wrecks = new LinkedList<>();
    private static List<Tanker> tankers = new LinkedList<>();

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        while (true) {
            tankers.clear();
            wrecks.clear();
            playersGame.clear();
            int score[] = new int[3];
            score[0] = in.nextInt();
            score[1] = in.nextInt();
            score[2] = in.nextInt();
            int rage[] = new int[3];
            rage[0] = in.nextInt();
            rage[1] = in.nextInt();
            rage[2] = in.nextInt();
            addPlayersGame(score, rage);
            int unitCount = in.nextInt();
            for (int i = 0; i < unitCount; i++) {
                int unitId = in.nextInt();
                int unitType = in.nextInt();
                int playerId = in.nextInt();
                float mass = in.nextFloat();
                int radius = in.nextInt();
                int x = in.nextInt();
                int y = in.nextInt();
                Position position = new Position(x, y);
                int vx = in.nextInt();
                int vy = in.nextInt();
                Speed speed = new Speed(vx, vy);
                int extra = in.nextInt();
                int extra2 = in.nextInt();
                switch (unitType) {
                    case 0:
                        addReaper(unitId, playerId, mass, radius, position, speed);
                        break;
                    case 1:
                        addDestroyer(unitId, playerId, mass, radius, position, speed);
                        break;
                    case 2:
                        break;
                    case 3:
                        addTanker(unitId, mass, radius, position, speed, extra, extra2);
                        break;
                    case 4:
                        addWreck(unitId, radius, position, speed, extra);
                        break;
                }
            }

            System.out.println(playersGame.get(0).getMoveReaper(wrecks));
            System.out.println(playersGame.get(0).getMoveDestroyer(tankers));
            System.out.println("WAIT");
        }
    }

    private static void addWreck(int unitId, int radius, Position position, Speed speed, int extra) {
        Wreck wreck = new Wreck(unitId, radius, position, speed, extra);
        wrecks.add(wreck);
    }

    private static void addTanker(int unitId, float mass, int radius, Position position, Speed speed, int extra, int extra2) {
        Tanker tanker = new Tanker(unitId, mass, radius, position, speed, extra, extra2);
        tankers.add(tanker);
    }

    private static void addDestroyer(int unitId, int playerId, float mass, int radius, Position position, Speed speed) {
        Destroyer destroyer = new Destroyer(unitId, mass, radius, position, speed);
        PlayerGame playerGame = playersGame.get(playerId);
        playerGame.setDestroyer(destroyer);
    }

    private static void addReaper(int unitId, int playerId, float mass, int radius, Position position, Speed speed) {
        Reaper reaper = new Reaper(unitId, mass, radius, position, speed);
        PlayerGame playerGame = playersGame.get(playerId);
        playerGame.setReaper(reaper);
    }

    private static void addPlayersGame(int[] score, int[] rage) {
        for (int i = 0; i < 3; i++) {
            playersGame.put(i, new PlayerGame(score[i], rage[i]));
        }
    }


}