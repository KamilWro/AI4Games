package MiniMax;

import java.util.Deque;

/**
 * Algorytm przeszukujący, redukujący liczbę węzłów, które muszą być rozwiązywane w drzewach
 * przeszukujących przez algorytm min-max(algorytm maksymalizowania minimalnego zysku).
 * Ta technika pozwala zaoszczędzić czas poszukiwania bez zmiany wyniku działania algorytmu.
 * Algorytm jest oparty na odcięciach alfa i beta.
 * <p>
 * Odcięcia beta: przeszukiwanie można zakończyć poniżej dowolnego wierzchołka typu MAX o wartości
 * większej lub równej wartości beta dowolnego  jego poprzedników (typu MIN).
 * <p>
 * Odcięcia alfa: przeszukiwanie można zakończyć poniżej dowolnego wierzchołka typu MIN o wartości
 * mniejszej lub równej wartości alfa dowolnego z jego poprzedników (typu MAX).
 */

public class TreeMiniMax {
    private int sizeTree;
    private final int numberOfBranches;
    private Deque<Integer> heuristicValues;

    public TreeMiniMax(Integer numberOfBranches) {
        this.numberOfBranches = numberOfBranches;
    }

    public Integer alfaBeta(Integer alfa, Integer beta, Player player, Integer depth) {
        sizeTree++;
        if (depth == 0) {
            return heuristicValues.removeFirst();
        }
        if (player.equals(Player.maximizingPlayer)) {
            return getAlfa(alfa, beta, depth);
        } else if (player.equals(Player.minimizingPlayer)) {
            return getBeta(alfa, beta, depth);
        }

        throw new RuntimeException("MiniMax.Player not recognized");
    }

    private Integer getAlfa(Integer alfa, Integer beta, Integer depth) {
        for (int branch = 0; branch < numberOfBranches; branch++) {
            alfa = Math.max(alfa, alfaBeta(alfa, beta, Player.minimizingPlayer, depth - 1));
            if (alfa >= beta) {
                cutOfBranches(numberOfBranches - (branch + 1), depth - 1);
                break;
            }
        }
        return alfa;
    }

    private Integer getBeta(Integer alfa, Integer beta, Integer depth) {
        for (int branch = 0; branch < numberOfBranches; branch++) {
            beta = Math.min(beta, alfaBeta(alfa, beta, Player.maximizingPlayer, depth - 1));
            if (alfa >= beta) {
                cutOfBranches(numberOfBranches - (branch + 1), depth - 1);
                break;
            }
        }
        return beta;
    }

    private void cutOfBranches(int restBranch, int depth) {
        for (int branch = 0; branch < Math.pow(numberOfBranches, depth) * restBranch; branch++) {
            heuristicValues.removeFirst();
        }
    }

    public int getSizeTree() {
        return sizeTree;
    }

    public void setHeuristicValues(Deque<Integer> heuristicValues) {
        this.heuristicValues = heuristicValues;
    }
}
