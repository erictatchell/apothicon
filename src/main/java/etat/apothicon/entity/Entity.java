package etat.apothicon.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.main.Apothicon;

public class Entity {
    // TODO: oop-ify this!
    Apothicon ap;
    public int worldX;
    public int worldY;
    public int speed = 10;

    public BufferedImage up1, up2, left1, left2, right1, right2, down1, down2;
    public String direction;
    public int directionAngle;

    public boolean alive;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean onPath = false;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;

    public Entity(Apothicon ap) {

        this.ap = ap;
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

        ap.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
        if (ap.pFinder.search() == true) {
            int nextX = ap.pFinder.pathList.get(0).col * ap.tileSize;
            int nextY = ap.pFinder.pathList.get(0).row * ap.tileSize;
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
        ap.cc.checkTile(this);
        ap.cc.checkObject(this, false);
        ap.cc.checkPlayer(this);

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

    public void drawBullet(Graphics2D g2) {
        int screenX = worldX - ap.player.worldX + ap.player.screenX;
        int screenY = worldY - ap.player.worldY + ap.player.screenY;

        if (worldX + ap.tileSize > ap.player.worldX - ap.player.screenX &&
                worldX - ap.tileSize < ap.player.worldX + ap.player.screenX &&
                worldY + ap.tileSize > ap.player.worldY - ap.player.screenY &&
                worldY - ap.tileSize < ap.player.worldY + ap.player.screenY) {

            g2.setColor(Color.yellow);
            g2.drawRect(screenX, screenY, ap.tileSize / 3, ap.tileSize / 3);

        }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - ap.player.worldX + ap.player.screenX;
        int screenY = worldY - ap.player.worldY + ap.player.screenY;
        BufferedImage image = null;

        if (worldX + ap.tileSize > ap.player.worldX - ap.player.screenX &&
                worldX - ap.tileSize < ap.player.worldX + ap.player.screenX &&
                worldY + ap.tileSize > ap.player.worldY - ap.player.screenY &&
                worldY - ap.tileSize < ap.player.worldY + ap.player.screenY) {
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
