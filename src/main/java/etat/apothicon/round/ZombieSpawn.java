package etat.apothicon.round;

import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;

public class ZombieSpawn {
    public int worldX, worldY;
    private Zone parentZone;
    public boolean spawning;

    private Timer spawnTimer;
    private RoundManager roundManager;
    private BufferedImage image;


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

    public void handleAddZombieToMap(RoundManager rm, Apothicon ap) {
        spawning = true;
        addZombieToMap(rm, ap);

    }

    private void addZombieToMap(RoundManager rm, Apothicon ap) {
        ap.gameState.aSetter.setZombie(worldX, worldY, rm.getTotalZombiesSpawnedForThisRound());
        spawning = false;
    }

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
