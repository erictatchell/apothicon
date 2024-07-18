package etat.apothicon.main;

import java.awt.Rectangle;

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

    public void bullet_checkTile(Entity e) {
        int eLeftWorldX = e.worldX + e.solidArea.x;
        int eRightWorldX = e.worldX + e.solidArea.x + e.solidArea.width;
        int eTopWorldY = e.worldY + e.solidArea.y;
        int eBottomWorldY = e.worldY + e.solidArea.y + e.solidArea.height;

        int eLeftCol = eLeftWorldX / ap.tileSize;
        int eRightCol = eRightWorldX / ap.tileSize;
        int eTopRow = eTopWorldY / ap.tileSize;
        int eBottomRow = eBottomWorldY / ap.tileSize;

        int tileNum1;
        // upper right quadrant: case (dir < 0 && dir > -90)
        // upper left quadrant: case (dir < -90 && dir > -180)
        // lower right quadrant: case (dir > 0 && dir < 90)
        // lower left quadrant: case (dir > 90 && dir < 180)
        int dir = e.directionAngle;
        boolean upperRight = (dir <= 0 && dir > -90);
        boolean upperLeft = (dir < -90 && dir >-180);
        boolean lowerRight = (dir > 0 && dir < 90);
        boolean lowerLeft = (dir > 90 && dir <= 180);
        if (eTopRow < ap.maxWorldRow && eRightCol < ap.maxWorldCol && eBottomRow < ap.maxWorldRow
                && eLeftCol < ap.maxWorldCol) {
            if (upperRight) {
                eTopRow = (eTopWorldY - e.speed) / ap.tileSize;
                tileNum1 = ap.tileManager.mapTileNum[eRightCol][eTopRow];
                if (ap.tileManager.tile[tileNum1].collision) {
                    e.collisionOn = true;
                }

            } else if (upperLeft) {
                eTopRow = (eTopWorldY - e.speed) / ap.tileSize;
                tileNum1 = ap.tileManager.mapTileNum[eLeftCol][eTopRow];
                if (ap.tileManager.tile[tileNum1].collision) {
                    e.collisionOn = true;
                }
            } else if (lowerLeft) {
                eBottomRow = (eBottomWorldY + e.speed) / ap.tileSize;
                tileNum1 = ap.tileManager.mapTileNum[eLeftCol][eBottomRow];
                if (ap.tileManager.tile[tileNum1].collision) {
                    e.collisionOn = true;
                }
            }
            else if (lowerRight) {
                eBottomRow = (eBottomWorldY - e.speed) / ap.tileSize;
                tileNum1 = ap.tileManager.mapTileNum[eLeftCol][eBottomRow];
                if (ap.tileManager.tile[tileNum1].collision) {
                    e.collisionOn = true;
                }
            }
        }
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

    public int checkOmnidirectionalEntity(Entity e, Entity[] target) {
        int index = 999;

        int futureX = e.worldX + (int) (e.speed * Math.cos(Math.toRadians(e.directionAngle)));
        int futureY = e.worldY + (int) (e.speed * Math.sin(Math.toRadians(e.directionAngle)));

        // THANKS CHATGPT!!!
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // Get entities' solid area positions
                Rectangle eSolidArea = new Rectangle(futureX + e.solidArea.x, futureY + e.solidArea.y,
                        e.solidArea.width, e.solidArea.height);
                Rectangle targetSolidArea = new Rectangle(target[i].worldX + target[i].solidArea.x,
                        target[i].worldY + target[i].solidArea.y, target[i].solidArea.width,
                        target[i].solidArea.height);

                if (eSolidArea.intersects(targetSolidArea)) {
                    e.collisionOn = true;
                    index = i;
                    break; // Exit the loop as soon as a collision is detected
                }
            }
        }
        return index;
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
