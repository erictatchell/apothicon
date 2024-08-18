package etat.apothicon.object;

import etat.apothicon.main.Apothicon;
import etat.apothicon.utility.MediaManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

// credit: https://stackoverflow.com/questions/8498691/pass-parameters-to-timer-task-java
class DeactivateDrop extends TimerTask {
    public Drop drop;
    private DropManager dm;

    public DeactivateDrop(DropManager dm, Drop drop) {
        this.drop = drop;
        this.dm = dm;
    }

    @Override
    public void run() {
        dm.handleDeactivate(drop);
    }
}

public class Drop extends SuperObject {
    public DropType dropType;
    public int objIndex;
    public int spawnedIndex;
    public int activeIndex;
    public Apothicon ap;
    public Timer dropExpireTimer;
    public Timer activeExpireTimer;
    private int auraCounter;
    private int auraAngle;
    public int dropFlashRate;
    private int dropFlashCounter;
    public int activeFlashRate;
    private int activeFlashCounter;
    public int slotX;

    public BufferedImage auraImage;
    public BufferedImage dropIcon;

    public boolean spawned = true;
    public boolean dropVisible = false;
    public boolean dropFlashing = false;

    public boolean active = false;
    public boolean iconVisible = false;
    public boolean iconFlashing = false;

    public Drop(int objIndex, int worldX, int worldY, Apothicon ap, DropType dropType) {
        this.objIndex = objIndex;
        this.auraCounter = 0;
        this.dropFlashCounter = 0;
        this.dropVisible = true;
        this.auraAngle = 0;
        this.dropExpireTimer = new Timer();
        this.ap = ap;
        this.type = "drop";
        this.dropType = dropType;
        this.worldX = worldX;
        this.worldY = worldY;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 30;
        solidArea.height = 30;

        setImage();

    }

    public void setImage() {
        try {
            auraImage = ImageIO.read(new File("src/main/resources/drops/aura.png"));
            switch (this.dropType) {
                case INSTA_KILL -> {
                    dropIcon = ImageIO.read(new File("src/main/resources/drops/instakillicon.png"));
                    image = ImageIO.read(new File("src/main/resources/drops/instakill.png"));
                }
                case DOUBLE_POINTS -> {
                    dropIcon = ImageIO.read(new File("src/main/resources/drops/doublepointsicon.png"));
                    image = ImageIO.read(new File("src/main/resources/drops/doublepoints.png"));
                }
                case MAX_AMMO -> {
                    dropIcon = ImageIO.read(new File("src/main/resources/drops/maxammoicon.png"));
                    image = ImageIO.read(new File("src/main/resources/drops/nmaxammo.png"));
                }
                case FIRE_SALE -> {
                    dropIcon = ImageIO.read(new File("src/main/resources/drops/firesaleicon.png"));
                    image = ImageIO.read(new File("src/main/resources/drops/firesale.png"));
                }
                case INFINITE_AMMO -> {
                    dropIcon = ImageIO.read(new File("src/main/resources/drops/infiniteammoicon.png"));
                    image = ImageIO.read(new File("src/main/resources/drops/infiniteammo.png"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void activate() {
        DropManager dm = ap.gameManager.dropManager;
        dm.setEffects(this.dropType);

        Timer activeTimer = new Timer();
        activeTimer.schedule(new DeactivateDrop(dm, this), 30000);
        this.activeExpireTimer = new Timer();

        this.spawned = false;
        this.active = true;
        this.slotX = dm.getSlotX();

        activeExpireTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                iconFlashing = true;
                activeFlashRate = 30;
            }
        }, 22000);
        activeExpireTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                activeFlashRate = 15;
            }
        }, 27000);
    }

    public void deactivate() {
        DropManager dm = ap.gameManager.dropManager;
        dm.removeEffects(this.dropType);
        active = false;
        // play sound for drop expiration
    }


    public void flashDrop() {
        dropFlashCounter++;
        if (dropFlashCounter > dropFlashRate) {
            dropVisible = !dropVisible;
            dropFlashCounter = 0;
        }
    }

    public int calculateAuraAngle() {
        auraCounter++;
        if (auraCounter > 3) {
            auraAngle += 2;
            if (auraAngle >= 360) {
                auraAngle = 0;
            }
            auraCounter = 0;
        }
        return auraAngle;
    }

    public void drawIcon(Graphics2D g2) {
        g2.drawImage(dropIcon, slotX + 10, ap.screenHeight - 50, ap.tileSize - 16, ap.tileSize - 16, null);
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
            if (iconFlashing) {
//                flashIcon();
            }
            if (iconVisible) {

            }
            if (dropFlashing) {
                flashDrop();
            }
            if (dropVisible) {
                g2.drawImage(MediaManager.rotateImageByDegrees(this.auraImage, calculateAuraAngle()), screenX, screenY, ap.tileSize, ap.tileSize, null);
                g2.drawImage(this.image, screenX, screenY, ap.tileSize, ap.tileSize, null);
            }

        }
    }
}
