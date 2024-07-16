package etat.apothicon.main;

import etat.apothicon.entity.Entity;
import etat.apothicon.object.weapon.gun.Bullet;

public class CollisionChecker {
    Apothicon ap;

    public CollisionChecker(Apothicon ap) {
        this.ap = ap;
    }
  

    public void checkPlayer(Entity e) {
        // get entities solid area position
        e.solidArea.x = e.worldX + e.solidArea.x;
        e.solidArea.y = e.worldY + e.solidArea.y;

        // get objects solid area position
        ap.player.solidArea.x = ap.player.worldX + ap.player.solidArea.x;
        ap.player.solidArea.y = ap.player.worldY + ap.player.solidArea.y;

        switch (e.direction) {
            case "up":
                e.solidArea.y -= e.speed;
                if (e.solidArea.intersects(ap.player.solidArea)) {
                    e.collisionOn = true;
                }
                break;
            case "down":
                e.solidArea.y += e.speed;
                if (e.solidArea.intersects(ap.player.solidArea)) {
                    e.collisionOn = true;
                }
                break;
            case "left":
                e.solidArea.x -= e.speed;
                if (e.solidArea.intersects(ap.player.solidArea)) {
                    e.collisionOn = true;
                }
                break;
            case "right":
                e.solidArea.x += e.speed;
                if (e.solidArea.intersects(ap.player.solidArea)) {
                    e.collisionOn = true;
                }
                break;
        }
        e.solidArea.x = e.solidAreaDefaultX;
        e.solidArea.y = e.solidAreaDefaultY;
        ap.player.solidArea.x = ap.player.solidAreaDefaultX;
        ap.player.solidArea.y = ap.player.solidAreaDefaultY;

    }

    public int checkObject(Entity e, boolean player) {
        int index = 999;

        for (int i = 0; i < ap.obj.length; i++) {
            if (ap.obj[i] != null) {
                // get entities solid area position
                e.solidArea.x = e.worldX + e.solidArea.x;
                e.solidArea.y = e.worldY + e.solidArea.y;

                // get objects solid area position
                ap.obj[i].solidArea.x = ap.obj[i].worldX + ap.obj[i].solidArea.x;
                ap.obj[i].solidArea.y = ap.obj[i].worldY + ap.obj[i].solidArea.y;

                switch (e.direction) {
                    case "up":
                        e.solidArea.y -= e.speed;
                        if (e.solidArea.intersects(ap.obj[i].solidArea)) {
                            if (ap.obj[i].collision) {
                                e.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        e.solidArea.y += e.speed;
                        if (e.solidArea.intersects(ap.obj[i].solidArea)) {
                            if (ap.obj[i].collision) {
                                e.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        e.solidArea.x -= e.speed;
                        if (e.solidArea.intersects(ap.obj[i].solidArea)) {
                            if (ap.obj[i].collision) {
                                e.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        e.solidArea.x += e.speed;
                        if (e.solidArea.intersects(ap.obj[i].solidArea)) {
                            if (ap.obj[i].collision) {
                                e.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
                e.solidArea.x = e.solidAreaDefaultX;
                e.solidArea.y = e.solidAreaDefaultY;
                ap.obj[i].solidArea.x = ap.obj[i].solidAreaDefaultX;
                ap.obj[i].solidArea.y = ap.obj[i].solidAreaDefaultY;
            }
        }
        return index;
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
        if (e.direction == "up") {
            eTopRow = (eTopWorldY - e.speed) / ap.tileSize;
            tileNum1 = ap.tileManager.mapTileNum[eLeftCol][eTopRow];
            tileNum2 = ap.tileManager.mapTileNum[eRightCol][eTopRow];
            if (ap.tileManager.tile[tileNum1].collision || ap.tileManager.tile[tileNum2].collision) {
                e.collisionOn = true;
            }

        }
        if (e.direction == "down") {
            eBottomRow = (eBottomWorldY + e.speed) / ap.tileSize;
            tileNum1 = ap.tileManager.mapTileNum[eLeftCol][eBottomRow];
            tileNum2 = ap.tileManager.mapTileNum[eRightCol][eBottomRow];
            if (ap.tileManager.tile[tileNum1].collision || ap.tileManager.tile[tileNum2].collision) {
                e.collisionOn = true;
            }

        }
        if (e.direction == "left") {
            eLeftCol = (eLeftWorldX - e.speed) / ap.tileSize;
            tileNum1 = ap.tileManager.mapTileNum[eLeftCol][eTopRow];
            tileNum2 = ap.tileManager.mapTileNum[eLeftCol][eBottomRow];
            if (ap.tileManager.tile[tileNum1].collision || ap.tileManager.tile[tileNum2].collision) {
                e.collisionOn = true;
            }

        }
        if (e.direction == "right") {
            eRightCol = (eRightWorldX + e.speed) / ap.tileSize;
            tileNum1 = ap.tileManager.mapTileNum[eRightCol][eTopRow];
            tileNum2 = ap.tileManager.mapTileNum[eRightCol][eBottomRow];
            if (ap.tileManager.tile[tileNum1].collision || ap.tileManager.tile[tileNum2].collision) {
                e.collisionOn = true;
            }

        }
    }

    // zombie collision
    public int checkEntity(Entity e, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // get entities solid area position
                e.solidArea.x = e.worldX + e.solidArea.x;
                e.solidArea.y = e.worldY + e.solidArea.y;

                // get objects solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (e.direction) {
                    case "up":
                        e.solidArea.y -= e.speed;
                        if (e.solidArea.intersects(target[i].solidArea)) {
                            e.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        e.solidArea.y += e.speed;
                        if (e.solidArea.intersects(target[i].solidArea)) {
                            e.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        e.solidArea.x -= e.speed;
                        if (e.solidArea.intersects(target[i].solidArea)) {
                            e.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        e.solidArea.x += e.speed;
                        if (e.solidArea.intersects(target[i].solidArea)) {
                            e.collisionOn = true;
                            index = i;
                        }
                        break;
                }
                e.solidArea.x = e.solidAreaDefaultX;
                e.solidArea.y = e.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }
}
