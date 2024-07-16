package etat.apothicon.ai;

import java.util.ArrayList;

import etat.apothicon.main.Apothicon;

public class PathFinder {
    Apothicon ap;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;
    public PathFinder(Apothicon ap) {
        this.ap = ap;
        instantiateNode();
    }
    public void instantiateNode() {
        node = new Node[ap.maxWorldCol][ap.maxWorldRow];
     
        int col =0;
        int row = 0;
        while (col < ap.maxWorldCol && row < ap.maxWorldRow) {
            node[col][row] = new Node(col, row);
            col++;
            if (col == ap.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }
}
