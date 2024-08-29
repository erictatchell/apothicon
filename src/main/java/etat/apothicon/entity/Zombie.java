package etat.apothicon.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import etat.apothicon.main.Apothicon;
import etat.apothicon.round.RoundManager;

public class Zombie extends Entity {
    private Apothicon ap;
    private final int screenX;
    private final int screenY;
    private Random random = new Random();
    private Timer hitDelay;

    private boolean hitting;

    public Zombie(Apothicon ap) {
        super(ap);
        direction = "down";
//        speed = calculateZombieSpeed(ap.gameManager.roundManager.getCurrentRound(),
//                ap.gameManager.roundManager.getTotalZombiesOnMap(),
//                ap.gameManager.roundManager.getTotalZombiesSpawnedForThisRound());
        speed = 3;
        this.ap = ap;
        this.health = defaultHealth;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setZombieImage();
        screenX = ap.screenWidth / 2 - (ap.tileSize / 2);
        screenY = ap.screenHeight / 2 - (ap.tileSize / 2);

    }

    public void setZombieImage() {
        up1 = setup("zombie/z1_up1.png");
        up2 = setup("zombie/z1_up2.png");
        down1 = setup("zombie/z1_down1.png");
        down2 = setup("zombie/z1_down2.png");
        left1 = setup("zombie/z1_left1.png");
        left2 = setup("zombie/z1_left2.png");
        right1 = setup("zombie/z1_right1.png");
        right2 = setup("zombie/z1_right2.png");
    }

    public int calculateZombieSpeed(int round, int zombiesOnMap, int totalZombiesSpawned) {
        if (round <= 4) {
            return 1;

        } else if (round <= 8) {
            return random.nextInt(2) + 1;
        } else {
            return random.nextInt(3) + 1;
        }
    }

    public void dealDamage(Player e) {
        hitting = true;
        hitDelay = new Timer();
        hitDelay.schedule(new TimerTask() {
            @Override
            public void run() {
                Loadout loadout = e.getLoadout();
                ap.gameManager.cc.checkPlayer(e);
                if (collisionOn && !e.isInLastStand()) {
                    loadout.health -= 50;
                    loadout.healing = false;
                    if (loadout.loadoutTimer != null) {
                        loadout.loadoutTimer.cancel();
                    }
                }

                hitting = false;

            }
        }, 100);
    }

    public static void increaseDefaultHealth(int round) {
        if (round < 10) {
            defaultHealth += 100;
        } else defaultHealth *= 1.1f;

    }

    public boolean isHitting() {
        return hitting;
    }

    public void setHitting(boolean hitting) {
        this.hitting = hitting;
    }

    public void setAction() {
        if (onPath) {
            int goalCol = (ap.gameManager.player.worldX + ap.gameManager.player.solidArea.x) / ap.tileSize;
            int goalRow = (ap.gameManager.player.worldY + ap.gameManager.player.solidArea.y) / ap.tileSize;

            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;
            if (actionLockCounter > 60) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;
                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75 && i <= 100) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }
}
