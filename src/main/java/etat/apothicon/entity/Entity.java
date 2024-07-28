package etat.apothicon.entity;

import etat.apothicon.main.Apothicon;
import etat.apothicon.sound.ImpactSound;
import etat.apothicon.sound.SoundType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
    private Timer feedbackTimer;
    private boolean drawingFeedback;
    public int headSolidAreaDefaultX;
    public int headSolidAreaDefaultY;

    public Rectangle headSolidArea = new Rectangle();
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean collisionIsHeadshot = false;
    public int actionLockCounter = 0;

    public Entity(Apothicon ap) {

        this.ap = ap;
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        headSolidArea.x = 8;
        headSolidArea.y = 0;
        headSolidArea.width = 30;
        headSolidArea.height = 20;
        headSolidAreaDefaultX = headSolidArea.x;
        headSolidAreaDefaultY = headSolidArea.y;

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

    public BufferedImage drawDirection() {
        BufferedImage image = null;
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
        return image;
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / ap.tileSize;
        int startRow = (worldY + solidArea.y) / ap.tileSize;

        ap.gameManager.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
        if (ap.gameManager.pFinder.search()) {
            int nextX = ap.gameManager.pFinder.pathList.get(0).col * ap.tileSize;
            int nextY = ap.gameManager.pFinder.pathList.get(0).row * ap.tileSize;
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
        ap.gameManager.cc.checkTile(this);
        ap.gameManager.cc.checkObject(this, false);
        ap.gameManager.cc.checkPlayer(this);

    }

    public void die(boolean headshot, int index) {
        ap.gameManager.roundManager.getZombies()[index] = null;
        Random r = new Random();
        int soundIndex = r.nextInt(3) + 1;
        int sound = ImpactSound.KILL1.ordinal();
        if (!headshot) {
            switch (soundIndex) {
                case 2 -> sound = ImpactSound.KILL2.ordinal();
                case 3 -> sound = ImpactSound.KILL3.ordinal();
            }
        } else {
            sound = ImpactSound.HEADSHOT1.ordinal();
        }

        ap.playSE(sound, SoundType.IMPACT);
        ap.gameManager.roundManager.decreaseTotalZombiesOnMap();
        ap.gameManager.roundManager.increaseTotalZombiesKilled();
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

    public void takeDamage(boolean headshot, int dmg) {
        this.health -= dmg;

        feedbackTimer = new Timer();
        if (headshot) {
            feedbackTimer.schedule(new TimerTask() {
                public void run() {
                    drawingFeedback = true;
                }
            }, 0);

        }
        feedbackTimer.schedule(new TimerTask() {
            public void run() {
                drawingFeedback = false;
            }
        }, 100);
    }

    private void drawFeedback(int screenX, int screenY, Graphics2D g2) {

    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - ap.gameManager.player.worldX + ap.gameManager.player.screenX;
        int screenY = worldY - ap.gameManager.player.worldY + ap.gameManager.player.screenY;
        BufferedImage image = null;
        if (worldX + ap.tileSize > ap.gameManager.player.worldX - ap.gameManager.player.screenX &&
                worldX - ap.tileSize < ap.gameManager.player.worldX + ap.gameManager.player.screenX &&
                worldY + ap.tileSize > ap.gameManager.player.worldY - ap.gameManager.player.screenY &&
                worldY - ap.tileSize < ap.gameManager.player.worldY + ap.gameManager.player.screenY) {
            if (drawingFeedback) {
                g2.setColor(Color.YELLOW);
                g2.fillRect(screenX + this.headSolidArea.x, screenY + this.headSolidArea.y, this.headSolidArea.width, this.headSolidArea.height);
            }

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
