package etat.apothicon.main;

import etat.apothicon.entity.Entity;
import etat.apothicon.entity.Zombie;
import etat.apothicon.object.weapon.gun.Bullet;
import java.awt.Rectangle;

public class CollisionChecker {
    Apothicon ap;

    public CollisionChecker(Apothicon ap) {
        this.ap = ap;
    }

    /**
     * for zombies colliding with player
     * @param e zombie
     */
    public void checkPlayer(Entity e) {
        // get entities solid area position
        e.solidArea.x = e.worldX + e.solidArea.x;
        e.solidArea.y = e.worldY + e.solidArea.y;
        e.headSolidArea.x = e.worldX + e.headSolidArea.x;
        e.headSolidArea.y = e.worldY + e.headSolidArea.y;

        // get objects solid area position
        ap.gameState.player.solidArea.x = ap.gameState.player.worldX + ap.gameState.player.solidArea.x;
        ap.gameState.player.solidArea.y = ap.gameState.player.worldY + ap.gameState.player.solidArea.y;

        switch (e.direction) {
            case "up":
                e.solidArea.y -= e.speed;
                e.headSolidArea.y -= e.speed;
                if (e.solidArea.intersects(ap.gameState.player.solidArea)) {
                    e.collisionOn = true;
                }
                break;
            case "down":
                e.solidArea.y += e.speed;
                e.headSolidArea.y += e.speed;
                if (e.solidArea.intersects(ap.gameState.player.solidArea)) {
                    e.collisionOn = true;
                }
                break;
            case "left":
                e.solidArea.x -= e.speed;
                e.headSolidArea.x -= e.speed;
                if (e.solidArea.intersects(ap.gameState.player.solidArea)) {
                    e.collisionOn = true;
                }
                break;
            case "right":
                e.solidArea.x += e.speed;
                e.headSolidArea.x += e.speed;
                if (e.solidArea.intersects(ap.gameState.player.solidArea)) {
                    e.collisionOn = true;
                }
                break;
        }
        e.solidArea.x = e.solidAreaDefaultX;
        e.solidArea.y = e.solidAreaDefaultY;
        e.headSolidArea.x = e.headSolidAreaDefaultX;
        e.headSolidArea.y = e.headSolidAreaDefaultY;
        ap.gameState.player.solidArea.x = ap.gameState.player.solidAreaDefaultX;
        ap.gameState.player.solidArea.y = ap.gameState.player.solidAreaDefaultY;

    }

    public int checkObject(Entity e, boolean player) {
        int index = 999;

        for (int i = 0; i < ap.gameState.obj.length; i++) {
            if (ap.gameState.obj[i] != null) {
                // get entities solid area position
                e.solidArea.x = e.worldX + e.solidArea.x;
                e.solidArea.y = e.worldY + e.solidArea.y;
                e.headSolidArea.x = e.worldX + e.headSolidArea.x;
                e.headSolidArea.y = e.worldY + e.headSolidArea.y;

                // get objects solid area position
                ap.gameState.obj[i].solidArea.x = ap.gameState.obj[i].worldX + ap.gameState.obj[i].solidArea.x;
                ap.gameState.obj[i].solidArea.y = ap.gameState.obj[i].worldY + ap.gameState.obj[i].solidArea.y;

                switch (e.direction) {
                    case "up":
                        e.solidArea.y -= e.speed;
                        e.headSolidArea.y -= e.speed;
                        if (e.solidArea.intersects(ap.gameState.obj[i].solidArea)) {
                            if (ap.gameState.obj[i].collision) {
                                e.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        e.solidArea.y += e.speed;
                        e.headSolidArea.y += e.speed;
                        if (e.solidArea.intersects(ap.gameState.obj[i].solidArea)) {
                            if (ap.gameState.obj[i].collision) {
                                e.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        e.solidArea.x -= e.speed;
                        e.headSolidArea.y -= e.speed;
                        if (e.solidArea.intersects(ap.gameState.obj[i].solidArea)) {
                            if (ap.gameState.obj[i].collision) {
                                e.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        e.solidArea.x += e.speed;
                        e.headSolidArea.x += e.speed;
                        if (e.solidArea.intersects(ap.gameState.obj[i].solidArea)) {
                            if (ap.gameState.obj[i].collision) {
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
                e.headSolidArea.x = e.headSolidAreaDefaultX;
                e.headSolidArea.y = e.headSolidAreaDefaultY;
                ap.gameState.obj[i].solidArea.x = ap.gameState.obj[i].solidAreaDefaultX;
                ap.gameState.obj[i].solidArea.y = ap.gameState.obj[i].solidAreaDefaultY;
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

        int tileNum1, tileNum2;
        // upper right quadrant: case (dir < 0 && dir > -90)
        // upper left quadrant: case (dir < -90 && dir > -180)
        // lower right quadrant: case (dir > 0 && dir < 90)
        // lower left quadrant: case (dir > 90 && dir < 180)
        int dir = e.directionAngle;
        boolean upperRight = (dir <= 0 && dir >= -90);
        boolean upperLeft = (dir <= -90 && dir >= -180);
        boolean lowerRight = (dir >= 0 && dir <= 90);
        boolean lowerLeft = (dir >= 90 && dir <= 180);
        if (eTopRow < ap.maxWorldRow && eRightCol < ap.maxWorldCol && eBottomRow < ap.maxWorldRow
                && eLeftCol < ap.maxWorldCol) {
            if (upperRight) {
                eTopRow = (eTopWorldY - e.speed) / ap.tileSize;
                eRightCol = (eRightWorldX - e.speed) / ap.tileSize;
                tileNum1 = ap.gameState.tileManager.mapTileNum[eRightCol][eTopRow];
                if (ap.gameState.tileManager.tile[tileNum1].collision) {
                    e.collisionOn = true;
                }

            } else if (upperLeft) {
                eTopRow = (eTopWorldY - e.speed) / ap.tileSize;
                eLeftCol = (eLeftWorldX + e.speed) / ap.tileSize;
                tileNum1 = ap.gameState.tileManager.mapTileNum[eLeftCol][eTopRow];
                if (ap.gameState.tileManager.tile[tileNum1].collision) {
                    e.collisionOn = true;
                }
            } else if (lowerLeft) {
                eBottomRow = (eBottomWorldY - e.speed) / ap.tileSize;
                eLeftCol = (eLeftWorldX + e.speed) / ap.tileSize;
                tileNum1 = ap.gameState.tileManager.mapTileNum[eLeftCol][eBottomRow];
                if (ap.gameState.tileManager.tile[tileNum1].collision) {
                    e.collisionOn = true;
                }
            }
            else if (lowerRight) {
                eBottomRow = (eBottomWorldY - e.speed) / ap.tileSize;
                eRightCol = (eRightWorldX - e.speed) / ap.tileSize;
                tileNum1 = ap.gameState.tileManager.mapTileNum[eRightCol][eBottomRow];
                if (ap.gameState.tileManager.tile[tileNum1].collision) {
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
        if ("up".equals(e.direction)) {
            eTopRow = (eTopWorldY - e.speed) / ap.tileSize;
            tileNum1 = ap.gameState.tileManager.mapTileNum[eLeftCol][eTopRow];
            tileNum2 = ap.gameState.tileManager.mapTileNum[eRightCol][eTopRow];
            if (ap.gameState.tileManager.tile[tileNum1].collision || ap.gameState.tileManager.tile[tileNum2].collision) {
                e.collisionOn = true;
            }

        }
        if ("down".equals(e.direction)) {
            eBottomRow = (eBottomWorldY + e.speed) / ap.tileSize;
            tileNum1 = ap.gameState.tileManager.mapTileNum[eLeftCol][eBottomRow];
            tileNum2 = ap.gameState.tileManager.mapTileNum[eRightCol][eBottomRow];
            if (ap.gameState.tileManager.tile[tileNum1].collision || ap.gameState.tileManager.tile[tileNum2].collision) {
                e.collisionOn = true;
            }

        }
        if ("left".equals(e.direction)) {
            eLeftCol = (eLeftWorldX - e.speed) / ap.tileSize;
            tileNum1 = ap.gameState.tileManager.mapTileNum[eLeftCol][eTopRow];
            tileNum2 = ap.gameState.tileManager.mapTileNum[eLeftCol][eBottomRow];
            if (ap.gameState.tileManager.tile[tileNum1].collision || ap.gameState.tileManager.tile[tileNum2].collision) {
                e.collisionOn = true;
            }

        }
        if ("right".equals(e.direction)) {
            eRightCol = (eRightWorldX + e.speed) / ap.tileSize;
            tileNum1 = ap.gameState.tileManager.mapTileNum[eRightCol][eTopRow];
            tileNum2 = ap.gameState.tileManager.mapTileNum[eRightCol][eBottomRow];
            if (ap.gameState.tileManager.tile[tileNum1].collision || ap.gameState.tileManager.tile[tileNum2].collision) {
                e.collisionOn = true;
            }

        }
    }

    public int bullet_checkEntity(Bullet e, Entity[] target) {
        int index = 999;

        int futureX = e.worldX + (int) (e.speed * Math.cos(Math.toRadians(e.directionAngle)));
        int futureY = e.worldY + (int) (e.speed * Math.sin(Math.toRadians(e.directionAngle)));

        // THANKS CHATGPT!!!
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // Get entities' solid area positions
                Rectangle eSolidArea = new Rectangle(futureX + e.zombieSolidArea.x, futureY + e.zombieSolidArea.y,
                        e.zombieSolidArea.width, e.zombieSolidArea.height);
                Rectangle targetSolidArea = new Rectangle(target[i].worldX + target[i].solidArea.x,
                        target[i].worldY + target[i].solidArea.y, target[i].solidArea.width,
                        target[i].solidArea.height);
                Rectangle targetHeadSolidArea = new Rectangle(target[i].worldX + target[i].headSolidArea.x,
                        target[i].worldY + target[i].headSolidArea.y, target[i].headSolidArea.width,
                        target[i].headSolidArea.height);

                if (eSolidArea.intersects(targetSolidArea)) {
                    if (eSolidArea.intersects(targetHeadSolidArea)) {
                        System.out.println("headshot");
                    }
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
                e.headSolidArea.x = e.worldX + e.headSolidArea.x;
                e.headSolidArea.y = e.worldY + e.headSolidArea.y;

                // get objects solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
                target[i].headSolidArea.x = target[i].worldX + target[i].headSolidArea.x;
                target[i].headSolidArea.y = target[i].worldY + target[i].headSolidArea.y;

                switch (e.direction) {
                    case "up":
                        e.solidArea.y -= e.speed;
                        e.headSolidArea.y -= e.speed;
                        if (e.solidArea.intersects(target[i].solidArea)) {
                            e.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        e.solidArea.y += e.speed;
                        e.headSolidArea.y += e.speed;
                        if (e.solidArea.intersects(target[i].solidArea)) {
                            e.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        e.solidArea.x -= e.speed;
                        e.headSolidArea.x -= e.speed;
                        if (e.solidArea.intersects(target[i].solidArea)) {
                            e.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        e.solidArea.x += e.speed;
                        e.headSolidArea.x += e.speed;
                        if (e.solidArea.intersects(target[i].solidArea)) {
                            e.collisionOn = true;
                            index = i;
                        }
                        break;
                }
                e.solidArea.x = e.solidAreaDefaultX;
                e.solidArea.y = e.solidAreaDefaultY;
                e.headSolidArea.x = e.headSolidAreaDefaultX;
                e.headSolidArea.y = e.headSolidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
                target[i].headSolidArea.x = target[i].headSolidAreaDefaultX;
                target[i].headSolidArea.y = target[i].headSolidAreaDefaultY;
            }
        }
        return index;
    }
}
