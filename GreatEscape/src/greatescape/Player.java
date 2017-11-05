package greatescape;

import greatescape.graph.GraphUtil;
import greatescape.graph.Node;
import greatescape.minimax.MiniMax;
import greatescape.movement.Move;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Player {
    private int width;
    private int height;
    private Map<Node, List<Node>> neighbours;
    private List<Gamer> gamers;
    private Gamer myGamer;
    private int myID;

    public Player(int width, int height, int myId) {
        this.width = width;
        this.height = height;
        this.myID = myId;
        gamers = new LinkedList<>();
    }

    public void addGamer(int id, Node node, int wallsLeft) {
        Gamer gamer = new Gamer(id, node, wallsLeft, getFinishLine(id));
        if (id == myID) {
            myGamer = gamer;
        } else {
            gamers.add(gamer);
        }
    }

    private Move getFinishLine(int id) {
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

    public void init() {
        gamers.clear();
        neighbours = GraphUtil.buildNeighbourList(width, height);
    }

    public void addWall(int wallX, int wallY, String wallOrientation) {
        if (wallOrientation.equals("V")) {
            delEdgeV(wallX, wallY);
            delEdgeV(wallX, wallY + 1);
        } else {
            delEdgeH(wallX, wallY);
            delEdgeH(wallX + 1, wallY);
        }
    }

    private void delEdgeV(int wallX, int wallY) {
        Node nodeA = new Node(wallX, wallY);
        Node nodeB = new Node(wallX - 1, wallY);
        delNeighbour(nodeA, nodeB);
        delNeighbour(nodeB, nodeA);
    }

    private void delEdgeH(int wallX, int wallY) {
        Node nodeA = new Node(wallX, wallY - 1);
        Node nodeB = new Node(wallX, wallY);
        delNeighbour(nodeA, nodeB);
        delNeighbour(nodeB, nodeA);
    }

    private void delNeighbour(Node node, Node neighbour) {
        List<Node> neighbourList = neighbours.get(node);
        neighbourList.remove(neighbour);
        neighbours.put(node, neighbourList);
    }

    public String getNextMovement() throws Exception {
        MiniMax miniMax = new MiniMax();
        miniMax.setGamers(gamers);
        miniMax.setMyGamer(myGamer);
        miniMax.setNeighbours(neighbours);
        Enum result = miniMax.execute();
        return result.toString();
    }

    public static void main(String args[]) throws Exception {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt();
        int height = in.nextInt();
        int playerCount = in.nextInt();
        int myId = in.nextInt();

        Player player = new Player(width, height, myId);
        while (true) {
            player.init();
            for (int i = 0; i < playerCount; i++) {
                int x = in.nextInt(); // x-coordinate of the player
                int y = in.nextInt(); // y-coordinate of the player
                int wallsLeft = in.nextInt(); // number of walls available for the player

                if (x != -1 && y != -1 && wallsLeft != -1) {
                    player.addGamer(i, new Node(x, y), wallsLeft);
                }
            }

            int wallCount = in.nextInt(); // number of walls on the board
            for (int i = 0; i < wallCount; i++) {
                int wallX = in.nextInt(); // x-coordinate of the wall
                int wallY = in.nextInt(); // y-coordinate of the wall
                String wallOrientation = in.next(); // wall orientation ('HORIZONTAL' or 'VERTICAL')
                player.addWall(wallX, wallY, wallOrientation);
            }
            System.out.println(player.getNextMovement());
        }
    }
}