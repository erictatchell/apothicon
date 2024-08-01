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
     *
     * @param e zombie
     */
    public void checkPlayer(Entity e) {
        // get entities solid area position
        e.solidArea.x = e.worldX + e.solidArea.x;
        e.solidArea.y = e.worldY + e.solidArea.y;
        e.headSolidArea.x = e.worldX + e.headSolidArea.x;
        e.headSolidArea.y = e.worldY + e.headSolidArea.y;

        // get objects solid area position
        ap.gameManager.player.solidArea.x = ap.gameManager.player.worldX + ap.gameManager.player.solidArea.x;
        ap.gameManager.player.solidArea.y = ap.gameManager.player.worldY + ap.gameManager.player.solidArea.y;

        switch (e.direction) {
            case "up":
                e.solidArea.y -= e.speed;
                e.headSolidArea.y -= e.speed;
                handleSwing(e);
                break;
            case "down":
                e.solidArea.y += e.speed;
                e.headSolidArea.y += e.speed;
                handleSwing(e);
                break;
            case "left":
                e.solidArea.x -= e.speed;
                e.headSolidArea.x -= e.speed;
                handleSwing(e);
                break;
            case "right":
                e.solidArea.x += e.speed;
                e.headSolidArea.x += e.speed;
                handleSwing(e);
                break;
        }
        e.solidArea.x = e.solidAreaDefaultX;
        e.solidArea.y = e.solidAreaDefaultY;
        e.headSolidArea.x = e.headSolidAreaDefaultX;
        e.headSolidArea.y = e.headSolidAreaDefaultY;
        ap.gameManager.player.solidArea.x = ap.gameManager.player.solidAreaDefaultX;
        ap.gameManager.player.solidArea.y = ap.gameManager.player.solidAreaDefaultY;

    }

    private void handleSwing(Entity e) {
        if (e.solidArea.intersects(ap.gameManager.player.solidArea)) {
            if (e instanceof Zombie) {
                Zombie zombie = (Zombie) e;
                if (!zombie.hitting) {

                    zombie.dealDamage(ap.gameManager.player);
                }
            }
            e.collisionOn = true;
        }
    }

    public int checkObject(Entity e, boolean player) {
        int index = 999;

        for (int i = 0; i < ap.gameManager.obj.length; i++) {
            if (ap.gameManager.obj[i] != null) {
                // get entities solid area position
                e.solidArea.x = e.worldX + e.solidArea.x;
                e.solidArea.y = e.worldY + e.solidArea.y;
                e.headSolidArea.x = e.worldX + e.headSolidArea.x;
                e.headSolidArea.y = e.worldY + e.headSolidArea.y;

                // get objects solid area position
                ap.gameManager.obj[i].solidArea.x = ap.gameManager.obj[i].worldX + ap.gameManager.obj[i].solidArea.x;
                ap.gameManager.obj[i].solidArea.y = ap.gameManager.obj[i].worldY + ap.gameManager.obj[i].solidArea.y;

                switch (e.direction) {
                    case "up":
                        e.solidArea.y -= e.speed;
                        e.headSolidArea.y -= e.speed;
                        if (e.solidArea.intersects(ap.gameManager.obj[i].solidArea)) {
                            if (ap.gameManager.obj[i].collision) {
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
                        if (e.solidArea.intersects(ap.gameManager.obj[i].solidArea)) {
                            if (ap.gameManager.obj[i].collision) {
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
                        if (e.solidArea.intersects(ap.gameManager.obj[i].solidArea)) {
                            if (ap.gameManager.obj[i].collision) {
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
                        if (e.solidArea.intersects(ap.gameManager.obj[i].solidArea)) {
                            if (ap.gameManager.obj[i].collision) {
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
                ap.gameManager.obj[i].solidArea.x = ap.gameManager.obj[i].solidAreaDefaultX;
                ap.gameManager.obj[i].solidArea.y = ap.gameManager.obj[i].solidAreaDefaultY;
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
                tileNum1 = ap.gameManager.tileManager.mapTileNum[eRightCol][eTopRow];
                if (ap.gameManager.tileManager.tile[tileNum1].collision) {
                    e.collisionOn = true;
                    e.collisionIsWall = true;
                }

            } else if (upperLeft) {
                eTopRow = (eTopWorldY - e.speed) / ap.tileSize;
                eLeftCol = (eLeftWorldX + e.speed) / ap.tileSize;
                tileNum1 = ap.gameManager.tileManager.mapTileNum[eLeftCol][eTopRow];
                if (ap.gameManager.tileManager.tile[tileNum1].collision) {
                    e.collisionOn = true;
                    e.collisionIsWall = true;
                }
            } else if (lowerLeft) {
                eBottomRow = (eBottomWorldY - e.speed) / ap.tileSize;
                eLeftCol = (eLeftWorldX + e.speed) / ap.tileSize;
                tileNum1 = ap.gameManager.tileManager.mapTileNum[eLeftCol][eBottomRow];
                if (ap.gameManager.tileManager.tile[tileNum1].collision) {
                    e.collisionOn = true;
                    e.collisionIsWall = true;
                }
            } else if (lowerRight) {
                eBottomRow = (eBottomWorldY - e.speed) / ap.tileSize;
                eRightCol = (eRightWorldX - e.speed) / ap.tileSize;
                tileNum1 = ap.gameManager.tileManager.mapTileNum[eRightCol][eBottomRow];
                if (ap.gameManager.tileManager.tile[tileNum1].collision) {
                    e.collisionOn = true;
                    e.collisionIsWall = true;
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
            tileNum1 = ap.gameManager.tileManager.mapTileNum[eLeftCol][eTopRow];
            tileNum2 = ap.gameManager.tileManager.mapTileNum[eRightCol][eTopRow];
            if (ap.gameManager.tileManager.tile[tileNum1].collision || ap.gameManager.tileManager.tile[tileNum2].collision) {
                e.collisionOn = true;
            }

        }
        if ("down".equals(e.direction)) {
            eBottomRow = (eBottomWorldY + e.speed) / ap.tileSize;
            tileNum1 = ap.gameManager.tileManager.mapTileNum[eLeftCol][eBottomRow];
            tileNum2 = ap.gameManager.tileManager.mapTileNum[eRightCol][eBottomRow];
            if (ap.gameManager.tileManager.tile[tileNum1].collision || ap.gameManager.tileManager.tile[tileNum2].collision) {
                e.collisionOn = true;
            }

        }
        if ("left".equals(e.direction)) {
            eLeftCol = (eLeftWorldX - e.speed) / ap.tileSize;
            tileNum1 = ap.gameManager.tileManager.mapTileNum[eLeftCol][eTopRow];
            tileNum2 = ap.gameManager.tileManager.mapTileNum[eLeftCol][eBottomRow];
            if (ap.gameManager.tileManager.tile[tileNum1].collision || ap.gameManager.tileManager.tile[tileNum2].collision) {
                e.collisionOn = true;
            }

        }
        if ("right".equals(e.direction)) {
            eRightCol = (eRightWorldX + e.speed) / ap.tileSize;
            tileNum1 = ap.gameManager.tileManager.mapTileNum[eRightCol][eTopRow];
            tileNum2 = ap.gameManager.tileManager.mapTileNum[eRightCol][eBottomRow];
            if (ap.gameManager.tileManager.tile[tileNum1].collision || ap.gameManager.tileManager.tile[tileNum2].collision) {
                e.collisionOn = true;
            }

        }
    }

    public int bullet_checkEntity(Bullet e, Entity[] target) {
        int index = 999;

        int futureX = e.worldX + (int) (e.speed * Math.cos(Math.toRadians(e.directionAngle)));
        int futureY = e.worldY + (int) (e.speed * Math.sin(Math.toRadians(e.directionAngle)));

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
                        e.collisionIsHeadshot = true;
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
