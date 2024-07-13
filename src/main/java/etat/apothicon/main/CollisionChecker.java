package etat.apothicon.main;

import etat.apothicon.entity.Entity;

public class CollisionChecker {
    Apothicon ap;

    public CollisionChecker(Apothicon ap) {
        this.ap = ap;
    }

    public void checkTile(Entity e) {
        int eLeftWorldX = e.worldX + e.solidArea.x;
        int eRightWorldX = e.worldX + e.solidArea.x + e.solidArea.width;
        int eTopWorldY = e.worldY + e.solidArea.y;
        int eBottomWorldY = e.worldY + e.solidArea.y + e.solidArea.height;

        int eLeftCol = eLeftWorldX / ap.tileSize;
        int eRightCol = eRightWorldX / ap.tileSize;
        int eTopRow = eTopWorldY / ap.tileSize;
        int eBottomRow = eBottomWorldY / ap.tileSize;

        int tileNum1, tileNum2;
        switch (e.direction) {
            case "up":
                eTopRow = (eTopWorldY - e.speed) / ap.tileSize;
                tileNum1 = ap.tileManager.mapTileNum[eLeftCol][eTopRow];
                tileNum2 = ap.tileManager.mapTileNum[eRightCol][eTopRow];
                if (ap.tileManager.tile[tileNum1].collision || ap.tileManager.tile[tileNum2].collision) {
                    e.collisionOn = true;
                }
                break;
            case "down":
                eBottomRow = (eBottomWorldY + e.speed) / ap.tileSize;
                tileNum1 = ap.tileManager.mapTileNum[eLeftCol][eBottomRow];
                tileNum2 = ap.tileManager.mapTileNum[eRightCol][eBottomRow];
                if (ap.tileManager.tile[tileNum1].collision || ap.tileManager.tile[tileNum2].collision) {
                    e.collisionOn = true;
                }
                break;
            case "left":
                eLeftCol = (eLeftWorldX - e.speed) / ap.tileSize;
                tileNum1 = ap.tileManager.mapTileNum[eLeftCol][eTopRow];
                tileNum2 = ap.tileManager.mapTileNum[eLeftCol][eBottomRow];
                if (ap.tileManager.tile[tileNum1].collision || ap.tileManager.tile[tileNum2].collision) {
                    e.collisionOn = true;
                }
                break;
            case "right":
                eRightCol = (eRightWorldX + e.speed) / ap.tileSize;
                tileNum1 = ap.tileManager.mapTileNum[eRightCol][eTopRow];
                tileNum2 = ap.tileManager.mapTileNum[eRightCol][eBottomRow];
                if (ap.tileManager.tile[tileNum1].collision || ap.tileManager.tile[tileNum2].collision) {
                    e.collisionOn = true;
                }
                break;
        }
    }
}
