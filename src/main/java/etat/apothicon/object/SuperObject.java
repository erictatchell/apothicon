package etat.apothicon.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import etat.apothicon.main.Apothicon;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public String type = "gun";
    public int price = 0;
    public boolean collision = false;
    public int worldX;
    public int worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, Apothicon ap) {
        int screenX = worldX - ap.player.worldX + ap.player.screenX;
            int screenY = worldY - ap.player.worldY + ap.player.screenY;

            if (worldX + ap.tileSize > ap.player.worldX - ap.player.screenX &&
                    worldX - ap.tileSize < ap.player.worldX + ap.player.screenX &&
                    worldY + ap.tileSize > ap.player.worldY - ap.player.screenY &&
                    worldY - ap.tileSize < ap.player.worldY + ap.player.screenY) {
                g2.drawImage(this.image, screenX, screenY, ap.tileSize, ap.tileSize, null);

            }
    }
}
