import java.util.*;

class Player {
    private static Map<Integer, Factory> factories = new HashMap<>();

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int factoryCount = in.nextInt(); // the number of factories
        int linkCount = in.nextInt(); // the number of links between factories

        for (int i = 0; i < factoryCount; i++) {
            factories.put(i, new Factory(i));
        }

        for (int i = 0; i < linkCount; i++) {
            int factory1 = in.nextInt();
            int factory2 = in.nextInt();
            int distance = in.nextInt();
            addNeighbour(factory1, factory2, distance);
            addNeighbour(factory2, factory1, distance);
        }
        List<Troop> troops = new LinkedList<>();
        GamePlayer gamePlayer = new GamePlayer();
        // game loop
        while (true) {
            troops.clear();
            int entityCount = in.nextInt(); // the number of entities (e.g. factories and troops)
            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                String entityType = in.next();
                int arg1 = in.nextInt();
                int arg2 = in.nextInt();
                int arg3 = in.nextInt();
                int arg4 = in.nextInt();
                int arg5 = in.nextInt();
                switch (entityType) {
                    case "FACTORY":
                        Factory factory = factories.get(entityId);
                        factory.setOwner(arg1);
                        factory.setCyborgs(arg2);
                        factory.setProduction(arg3);
                        break;
                    case "TROOP":
                        troops.add(new Troop(entityId, arg1, arg2, arg3, arg4, arg5));
                        break;
                }
            }
            gamePlayer.setFactories(factories);
            System.out.println(gamePlayer.getMove(troops));
        }
    }

    private static void addNeighbour(int factory1, int factory2, int distance) {
        Factory factory = factories.get(factory1);
        Factory factoryNeighbour = factories.get(factory2);
        factory.addNeighbour(factoryNeighbour, distance);
    }
}