import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

class Factory {
    private Map<Factory, Integer> neighbours = new HashMap<>();
    private int production;
    private int cyborgs;
    private int owner;
    private int id;

    private List<Factory> targets = new LinkedList<>();

    public Factory(int id) {
        this.id = id;
    }

    public void prepareTargets(List<Troop> troops) {
        targets = findHostileFactory();
        targets = targets.stream()
                .filter(factory -> isMyTarget(factory, troops))
                .sorted(Comparator.comparing(factory -> calc(factory, troops).negate()))
                .collect(Collectors.toList());
    }

    private boolean isMyTarget(Factory factory, List<Troop> troops) {
        int sumCyborgs = -factory.getCyborgs();
        for (Troop troop : findTroopsByTargetFactory(factory.getId(), troops)) {
            sumCyborgs += (troop.getOwner() == 1) ? troop.getCyborgs() : -troop.getCyborgs();
        }
        return sumCyborgs <= 0;
    }

    private BigDecimal calc(Factory factory, List<Troop> troops) {
        BigDecimal rateDistance = BigDecimal.valueOf(2).divide(BigDecimal.valueOf(neighbours.get(factory)), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal result = factory.rateTarget(troops).multiply(rateDistance);
        return result;
    }

    public BigDecimal rateTarget(List<Troop> troops) {
        int sumCyborgs = -cyborgs;
        BigDecimal rateProduction = BigDecimal.valueOf(10)
                .multiply(BigDecimal.valueOf(production))
                .divide(BigDecimal.valueOf(3), 10, BigDecimal.ROUND_HALF_UP);

        sumCyborgs += findTroopsByTargetFactory(id, troops).stream()
                .mapToInt(troop -> (troop.getOwner() == 1) ? troop.getCyborgs() : -troop.getCyborgs())
                .sum();

        BigDecimal result = new BigDecimal(sumCyborgs).add(rateProduction);

        return result.compareTo(BigDecimal.valueOf(0)) > 0 ? result.multiply(BigDecimal.valueOf(2.0)) : result.multiply(BigDecimal.valueOf(1.5));
    }


    private List<Troop> findTroopsByTargetFactory(int id, List<Troop> troops) {
        return troops.stream()
                .filter(troop -> troop.getTargetFactory() == id)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<Factory> findHostileFactory() {
        return neighbours.entrySet().stream()
                .map(Map.Entry::getKey)
                .filter(factory -> factory.getOwner() != 1)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public Integer getDistance(Factory largestFactory) {
        return neighbours.get(largestFactory);
    }

    public void addNeighbour(Factory factory, int distance) {
        neighbours.put(factory, distance);
    }

    public int getCyborgs() {
        return cyborgs;
    }

    public void setCyborgs(int cyborgs) {
        this.cyborgs = cyborgs;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public List<Factory> getTargets() {
        return targets;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Factory)) return false;

        Factory factory = (Factory) o;

        return getId() == factory.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}