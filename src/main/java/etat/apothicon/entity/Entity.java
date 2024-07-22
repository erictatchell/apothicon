package etat.apothicon.entity;

import etat.apothicon.main.Apothicon;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Entity {
    // TODO: oop-ify this!
    public Apothicon ap;
    public int worldX;
    public int worldY;
    public int speed = 20;
    public int health = 100;

    public BufferedImage up1, up2, left1, left2, right1, right2, down1, down2;
    public String direction;
    public int directionAngle;

    public boolean alive;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean onPath = true;
    public Rectangle solidArea = new Rectangle();

    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;

    public Entity(Apothicon ap) {

        this.ap = ap;
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

    public BufferedImage setup(String url) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/main/resources/" + url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction() {

    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / ap.tileSize;
        int startRow = (worldY + solidArea.y) / ap.tileSize;

        ap.gameState.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
        if (ap.gameState.pFinder.search() == true) {
            int nextX = ap.gameState.pFinder.pathList.get(0).col * ap.tileSize;
            int nextY = ap.gameState.pFinder.pathList.get(0).row * ap.tileSize;
            // get entities solid area
            int eLeftX = worldX + solidArea.x;
            int eRightX = worldX + solidArea.x + solidArea.width;
            int eTopY = worldY + solidArea.y;
            int eBottomY = worldY + solidArea.y + solidArea.height;

            // wtf
            if (eTopY > nextY && eLeftX >= nextX && eRightX < nextX + ap.tileSize) {
                direction = "up";
            } else if (eTopY < nextY && eLeftX >= nextX && eRightX < nextX + ap.tileSize) {
                direction = "down";
            } else if (eTopY >= nextY && eBottomY < nextY + ap.tileSize) {
                // either left or right
                if (eLeftX > nextX) {
                    direction = "left";
                }
                if (eLeftX < nextX) {
                    direction = "right";
                }

            } else if (eTopY > nextY && eLeftX > nextX) {
                // either up or left
                direction = "up";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (eTopY > nextY && eLeftX < nextX) {
                // either up or right
                direction = "up";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            } else if (eTopY < nextY && eLeftX > nextX) {
                // either down or left
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (eTopY < nextY && eLeftX < nextX) {
                // either down or right
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            }
            // int nextCol = ap.pFinder.pathList.get(0).col;
            // int nextRow = ap.pFinder.pathList.get(0).row;
            // if (nextCol == goalCol && nextRow == goalRow) {
            // onPath = false;
            // }
        }
    }

    public void checkCollision() {
        collisionOn = false;
        ap.gameState.cc.checkTile(this);
        ap.gameState.cc.checkObject(this, false);
        ap.gameState.cc.checkPlayer(this);

    }

    public void die(int index) {
        ap.gameState.zombies[index] = null;
        ap.gameState.aSetter.setZombie();
    }

    public void update() {
        setAction();
        checkCollision();
        if (!collisionOn) {
            if (direction == "up") {
                this.worldY -= this.speed;
            }
            if (direction == "down") {
                this.worldY += this.speed;
            }
            if (direction == "left") {
                this.worldX -= this.speed;
            }
            if (direction == "right") {
                this.worldX += this.speed;
            }
        }
        spriteCounter++;
        if (spriteCounter > 12) { // 12 frames
            if (spriteNum == 1) {
                spriteNum = 2;
            } else {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void takeDamage(int dmg) {
        this.health -= dmg;

    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - ap.gameState.player.worldX + ap.gameState.player.screenX;
        int screenY = worldY - ap.gameState.player.worldY + ap.gameState.player.screenY;
        BufferedImage image = null;

        if (worldX + ap.tileSize > ap.gameState.player.worldX - ap.gameState.player.screenX &&
                worldX - ap.tileSize < ap.gameState.player.worldX + ap.gameState.player.screenX &&
                worldY + ap.tileSize > ap.gameState.player.worldY - ap.gameState.player.screenY &&
                worldY - ap.tileSize < ap.gameState.player.worldY + ap.gameState.player.screenY) {
            switch (this.direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                        break;
                    }
                    image = up2;
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                        break;
                    }
                    image = down2;
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                        break;
                    }
                    image = left2;
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                        break;
                    }
                    image = right2;
                    break;
            }
            g2.drawImage(image, screenX, screenY, ap.tileSize, ap.tileSize, null);

        }
    }

}
