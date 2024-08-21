package etat.apothicon.object.drop;

import etat.apothicon.main.Apothicon;
import etat.apothicon.object.SuperObject;
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

/**
 * Represents a powerup and its corresponding HUD icon
 */
public class Drop extends SuperObject {
    public DropType dropType;
    public int objIndex;
    public int spawnedIndex;
    public int activeIndex;
    public Apothicon ap;
    public Timer dropExpireTimer;
    public Timer activeTimer;
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
    public boolean dropVisible = true;
    public boolean dropFlashing = false;

    public boolean active = false;
    public boolean iconVisible = true;
    public boolean iconFlashing = false;

    public Drop(int objIndex, int worldX, int worldY, Apothicon ap, DropType dropType) {
        this.objIndex = objIndex;
        this.auraCounter = 0;
        this.dropFlashCounter = 0;
        this.auraAngle = 0;
        this.dropExpireTimer = new Timer();
        this.activeTimer = new Timer();
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
                    image = ImageIO.read(new File("src/main/resources/drops/maxammo.png"));
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
        this.spawned = false;

        // no expiration for max ammo, instant effects then cancel
        if (dropType == DropType.MAX_AMMO) {
            dm.spawnedDrops.remove(this);
            dm.deleteDrop(this);
            return;
        }
        this.active = true;
        this.slotX = dm.getSlotX();

        startActiveTimer(dm);
    }


    public void deactivate() {
        DropManager dm = ap.gameManager.dropManager;
        dm.removeEffects(this.dropType);
        active = false;
        // play sound for drop expiration
    }


    public void flashIcon() {
        activeFlashCounter++;
        if (activeFlashCounter > activeFlashRate) {
            iconVisible = !iconVisible;
            activeFlashCounter = 0;
        }
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

    public void startActiveTimer(DropManager dm) {
        activeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                iconFlashing = true;
                activeFlashRate = 21;
            }
        }, 22000);
        activeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                activeFlashRate = 7;
            }
        }, 25000);
        activeTimer.schedule(new DeactivateDrop(dm, this), 30000);
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
            if (dropFlashing) {
                flashDrop();
            }
            if (dropVisible) {
                g2.drawImage(MediaManager.rotateImageByDegrees(this.auraImage, calculateAuraAngle()), screenX, screenY, ap.tileSize, ap.tileSize, null);
                g2.drawImage(this.image, screenX, screenY, ap.tileSize, ap.tileSize, null);
            }

        } else if (active) {
            if (iconFlashing) {
                flashIcon();
            }
            if (iconVisible) {
                g2.drawImage(dropIcon, ap.screenWidth / 2, ap.screenHeight - 50, ap.tileSize - 16, ap.tileSize - 16, null);
            }
        }
    }
}
