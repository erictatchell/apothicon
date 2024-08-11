package etat.apothicon.object;

import etat.apothicon.main.Apothicon;
import etat.apothicon.utility.MediaManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Drop extends SuperObject {
    public DropType dropType;
    public int objIndex;
    public Apothicon ap;
    public Timer expireTimer;
    private int auraCounter;
    private int angle;
    public int flashRate;
    private int flashCounter;

    public BufferedImage auraImage;

    /**
     * Spawned also means drawable
     */
    public boolean spawned = true;
    public boolean visible = false;
    public boolean flashing = false;
    public boolean active = false;

    public Drop(int objIndex, int worldX, int worldY, Apothicon ap, DropType dropType) {
        this.objIndex = objIndex;
        this.auraCounter = 0;
        this.flashCounter = 0;
        visible = true;
        this.angle = 0;
        this.ap = ap;
        this.type = "drop";
        this.dropType = dropType;
        this.worldX = worldX;
        this.worldY = worldY;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 30;
        solidArea.height = 30;

        try {
            auraImage = ImageIO.read(new File("src/main/resources/drops/aura.png"));
        } catch (IOException e) {
            e.printStackTrace();

        }
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

    public void flash() {
        flashCounter++;
        if (flashCounter > flashRate) {
            visible = !visible;
            flashCounter = 0;
        }
    }

    public int calculateAuraAngle() {
        auraCounter++;
        if (auraCounter > 3) {
            angle += 2;
            if (angle >= 360) {
                angle = 0;
            }
            auraCounter = 0;

        }
        return angle;
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
            if (flashing) {
                flash();
            }
            if (visible) {
                g2.drawImage(MediaManager.rotateImageByDegrees(this.auraImage, calculateAuraAngle()), screenX, screenY, ap.tileSize, ap.tileSize, null);
                g2.drawImage(this.image, screenX, screenY, ap.tileSize, ap.tileSize, null);
            }

        }
    }
}
