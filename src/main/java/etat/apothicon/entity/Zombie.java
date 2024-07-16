package etat.apothicon.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.main.Apothicon;

public class Zombie extends Entity {
    Apothicon ap;
    public final int screenX;
    public final int screenY;

    public Zombie(Apothicon ap) {
        this.ap = ap;
        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        this.worldX = 32 * ap.tileSize;
        this.worldY = 43 * ap.tileSize;
        setZombieImage();
        screenX = ap.screenWidth / 2 - (ap.tileSize / 2);
        screenY = ap.screenHeight / 2 - (ap.tileSize / 2);

    }

    public void setZombieImage() {
        try {
            this.z1_pr = ImageIO.read(new File("src/main/resources/zombie/z1_right1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {

    }

    public void draw(Graphics2D g2, Apothicon ap) {
        int screenX = worldX - ap.player.worldX + ap.player.screenX;
        int screenY = worldY - ap.player.worldY + ap.player.screenY;

        if (worldX + ap.tileSize > ap.player.worldX - ap.player.screenX &&
                worldX - ap.tileSize < ap.player.worldX + ap.player.screenX &&
                worldY + ap.tileSize > ap.player.worldY - ap.player.screenY &&
                worldY - ap.tileSize < ap.player.worldY + ap.player.screenY) {
            g2.drawImage(this.z1_pr, screenX, screenY, ap.tileSize, ap.tileSize, null);

        }
    }
}
