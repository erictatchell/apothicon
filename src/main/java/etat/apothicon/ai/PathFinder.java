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

        int col = 0;
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

    public void resetNodes() {
        int col = 0;
        int row = 0;
        while (col < ap.maxWorldCol && row < ap.maxWorldRow) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if (col == ap.maxWorldCol) {
                col = 0;
                row++;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);
        int col = 0;
        int row = 0;
        while (col < ap.maxWorldCol && row < ap.maxWorldRow) {
            int tileNum = ap.tileManager.mapTileNum[col][row];
            if (ap.tileManager.tile[tileNum].collision) {
                node[col][row].solid = true;
            }
            // check interactive tiles
            // for (int i = 0; i < ap.iTile[i].length; i++) {
            // if (ap.iTile[gp.currentMap][i] != null &&
            // ap.iTile[ap.currentMap][i].destructible) {
            // int itCol = ap.iTile[ap.currentMap][i].worldX / ap.tileSize;
            // int itRow = ap.iTile[ap.currentMap][i].worldY / ap.tileSize;
            // node[itCol][itRow].solid = true;
            // }
            // }
            // get costs
            getCost(node[col][row]);
            col++;
            if (col == ap.maxWorldCol) {
                col = 0;
                row++;
            }
        }

    }

    public void getCost(Node node) {
        // G
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F
        node.fCost = node.gCost + node.hCost;

        

    }

    public boolean search() {
        while (!goalReached && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);

            //open node
            if (row - 1 >= 0) {
                openNode(node[col][row-1]);
            }
            if (col - 1 >= 0) {
                openNode(node[col-1][row]);
            }
            if (row + 1 < ap.maxWorldRow) {
                openNode(node[col][row+1]);
            }
            if (col + 1 < ap.maxWorldCol) {
                openNode(node[col+1][row]);
            }

            // find best path
            int bestNodeIndex = 0;
            int bestNodefCost = 999;
            for (int i = 0; i < openList.size(); i++ ) {
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                // if fcosts are equal check gcost
                else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            // if no node in open list, endwhile
            if (openList.size() == 0) {
                break;
            }

            currentNode = openList.get(bestNodeIndex);
            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }

        return goalReached;
    }

    public void trackThePath() {
        Node current = goalNode;

        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;

        }
    }

    public void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
}
