package etat.apothicon.round;

import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ZombieSpawn {
    private int worldX, worldY;
    private Zone parentZone;
    private boolean spawning;
    private Timer spawnDelay;
    private BufferedImage image;
    private Random random = new Random();

    public ZombieSpawn(Zone parent, String imagePath, int worldX, int worldY) {
        this.parentZone = parent;
        this.worldX = worldX;
        this.worldY = worldY;
        try {
            this.image = ImageIO.read(new File("src/main/resources/tiles/" + imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addZombieToMap(RoundManager rm, Apothicon ap) {
        spawning = true;
        ap.gameManager.aSetter.setZombie(worldX, worldY, rm.getTotalZombiesSpawnedForThisRound());
        spawnDelay = new Timer();
        spawnDelay.schedule(new TimerTask() {
            public void run() {
                spawning = false;
            }
        }, (random.nextInt(3000) + getMinimumSpawnDelay(rm)));
    }

    public int getMinimumSpawnDelay(RoundManager rm) {
        return 500 - (rm.getCurrentRound() * 10);
    }

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
    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public boolean isSpawning() {
        return spawning;
    }

    public void setSpawning(boolean spawning) {
        this.spawning = spawning;
    }
}
