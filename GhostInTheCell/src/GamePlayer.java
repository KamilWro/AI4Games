import java.util.*;

class GamePlayer {
    private List<Factory> myFactories = new LinkedList();
    private Factory largestFactory = null;
    private int iter = 0;

    public String getMove(List<Troop> troops) {
        iter++;
        StringBuilder result = new StringBuilder("WAIT");
        if (iter % 50 == 1 && largestFactory != null) {
            myFactories.sort(Comparator.comparing(factory -> factory.getDistance(largestFactory)));
            Factory factory = myFactories.get(0);
            result.append("; BOMB ")
                    .append(factory.getId()).append(" ")
                    .append(largestFactory.getId());
        }
        myFactories.forEach(factory -> factory.prepareTargets(troops));
        for (Factory myFactory : myFactories) {
            List<Factory> targets = myFactory.getTargets();
            if (myFactory.getCyborgs() >= 10 && myFactory.getProduction() < 3) {
                result.append("; INC ").append(myFactory.getId());
            }
            double cyborgs = 1;
            for (Factory target : targets) {
                cyborgs = cyborgs / 2;
                int value = (int) (myFactory.getCyborgs() * cyborgs);
                if (value == 0)
                    break;
                result.append("; MOVE ")
                        .append(myFactory.getId()).append(" ")
                        .append(target.getId()).append(" ")
                        .append(value);
            }
        }
        return result.toString();
    }

    public void setFactories(Map<Integer, Factory> factories) {
        findLargestFactory(factories);
        myFactories.clear();
        factories.entrySet().stream()
                .filter(factory -> factory.getValue().getOwner() == 1)
                .forEach(factory -> myFactories.add(factory.getValue()));
    }

    private void findLargestFactory(Map<Integer, Factory> factories) {
        Optional<Factory> factory1 = factories.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(factory -> factory.getOwner() == -1)
                .max(Comparator.comparing(Factory::getCyborgs));
        this.largestFactory = factory1.orElse(null);
    }
}