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
        int screenX = worldX - ap.gameState.player.worldX + ap.gameState.player.screenX;
        int screenY = worldY - ap.gameState.player.worldY + ap.gameState.player.screenY;

        if (worldX + ap.tileSize > ap.gameState.player.worldX - ap.gameState.player.screenX &&
                worldX - ap.tileSize < ap.gameState.player.worldX + ap.gameState.player.screenX &&
                worldY + ap.tileSize > ap.gameState.player.worldY - ap.gameState.player.screenY &&
                worldY - ap.tileSize < ap.gameState.player.worldY + ap.gameState.player.screenY) {
            g2.drawImage(this.image, screenX, screenY, ap.tileSize, ap.tileSize, null);

        }
    }
}
