package greatescape;

import greatescape.graph.Edge;
import greatescape.graph.GraphUtil;
import greatescape.graph.Node;
import greatescape.minimax.MiniMax;
import greatescape.movement.Move;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class Player {
    public static void main(String args[]) throws Exception {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt();
        int height = in.nextInt();
        int playerCount = in.nextInt();
        int myId = in.nextInt();

        MiniMax miniMax = new MiniMax(width, height, myId);
        List<Gamer> gamers = new LinkedList<>();

        while (true) {
            gamers.clear();
            for (int i = 0; i < playerCount; i++) {
                int x = in.nextInt(); // x-coordinate of the player
                int y = in.nextInt(); // y-coordinate of the player
                int wallsLeft = in.nextInt(); // number of walls available for the player
                if (x != -1 && y != -1 && wallsLeft != -1) {
                    if (i == myId) {
                        miniMax.setMyGamer(new Gamer(i, new Node(x, y), wallsLeft, getFinishLine(i)));
                    }
                    Gamer gamer = new Gamer(i, new Node(x, y), wallsLeft, getFinishLine(i));
                    gamers.add(gamer);
                }
            }
            miniMax.setGamers(gamers);
            List<Edge> walls = readWalls(in);
            miniMax.addWalls(walls);
            String result = miniMax.execute().toString();
            System.out.println(result);
        }

    }

    private static List<Edge> readWalls(Scanner in) {
        List<Edge> walls = new LinkedList<>();
        int wallCount = in.nextInt(); // number of walls on the board

        for (int i = 0; i < wallCount; i++) {
            int wallX = in.nextInt(); // x-coordinate of the wall
            int wallY = in.nextInt(); // y-coordinate of the wall
            String wallOrientation = in.next(); // wall orientation ('HORIZONTAL' or 'VERTICAL')
            if (wallOrientation.equals("V")) {
                walls.add(GraphUtil.getEdge(wallX - 1, wallY, wallX, wallY));
                walls.add(GraphUtil.getEdge(wallX - 1, wallY + 1, wallX, wallY + 1));
            } else {
                walls.add(GraphUtil.getEdge(wallX, wallY - 1, wallX, wallY));
                walls.add(GraphUtil.getEdge(wallX + 1, wallY - 1, wallX + 1, wallY));
            }
        }

        return walls;
    }

    private static Move getFinishLine(int id) {
        switch (id) {
            case 0: {
                return Move.RIGHT;
            }
            case 1: {
                return Move.LEFT;
            }
            case 2: {
                return Move.DOWN;
            }
        }
        return Move.UP;
    }
}