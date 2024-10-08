package etat.apothicon.object;

import etat.apothicon.main.Apothicon;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public String type = "gun";
    public boolean collision = false;
    public int worldX;
    public int worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
    public int solidAreaDefaultX, solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, Apothicon ap) {
        int screenX = worldX - ap.gameManager.player.worldX + ap.gameManager.player.screenX;
        int screenY = worldY - ap.gameManager.player.worldY + ap.gameManager.player.screenY;

        if (worldX + ap.tileSize > ap.gameManager.player.worldX - ap.gameManager.player.screenX &&
                worldX - ap.tileSize < ap.gameManager.player.worldX + ap.gameManager.player.screenX &&
                worldY + ap.tileSize > ap.gameManager.player.worldY - ap.gameManager.player.screenY &&
                worldY - ap.tileSize < ap.gameManager.player.worldY + ap.gameManager.player.screenY) {
            g2.drawImage(this.image, screenX, screenY, ap.tileSize, ap.tileSize, null);

        }
    }
}
