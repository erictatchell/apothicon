package etat.apothicon.entity;

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
    public int speed;

    public BufferedImage up1, up2, left1, left2, right1, right2, down1, down2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

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

    public void update() {
        setAction();
        collisionOn = false;
        ap.cc.checkTile(this);
        ap.cc.checkObject(this, false);
        ap.cc.checkPlayer(this);
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
