package etat.apothicon.object;

import etat.apothicon.main.Apothicon;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Drop extends SuperObject {
    public DropType dropType;
    public Apothicon ap;
    public Timer expireTimer;

    /** Spawned also means drawable */
    public boolean spawned = true;

    public boolean active = false;

    public Drop(int worldX, int worldY, Apothicon ap, DropType dropType) {
        this.ap = ap;
        this.type = "drop";
        this.dropType = dropType;
        this.worldX = worldX;
        this.worldY = worldY;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 30;
        solidArea.height = 30;
    }


    public void activate() {
        spawned = false;
        active = true;
        Timer activeTimer = new Timer();
        activeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                deactivate();
            }
        }, 30000);
    }

    public void deactivate() {
        active = false;
        System.out.println("effects gone");
        // play sound for drop expiration
    }

    @Override
    public void draw(Graphics2D g2, Apothicon ap) {
        int screenX = worldX - ap.gameManager.player.worldX + ap.gameManager.player.screenX;
        int screenY = worldY - ap.gameManager.player.worldY + ap.gameManager.player.screenY;

        if (worldX + ap.tileSize > ap.gameManager.player.worldX - ap.gameManager.player.screenX &&
                worldX - ap.tileSize < ap.gameManager.player.worldX + ap.gameManager.player.screenX &&
                worldY + ap.tileSize > ap.gameManager.player.worldY - ap.gameManager.player.screenY &&
                worldY - ap.tileSize < ap.gameManager.player.worldY + ap.gameManager.player.screenY &&
                spawned) {
            g2.setColor(Color.RED);
            g2.fillRect(screenX, screenY, ap.tileSize, ap.tileSize);

        }
    }
}
