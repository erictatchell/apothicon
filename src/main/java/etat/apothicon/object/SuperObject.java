package etat.apothicon.object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import etat.apothicon.main.Apothicon;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX;
    public int worldY;

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
