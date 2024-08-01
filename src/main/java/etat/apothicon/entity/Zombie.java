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
    Apothicon ap;
    public final int screenX;
    public final int screenY;
    Random random = new Random();
    private Timer hitDelay;
    public boolean hitting;
    public Zombie(Apothicon ap) {
        super(ap);
        direction = "down";
        speed = random.nextInt(2) + 1;
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

    public void dealDamage(Player e) {
        hitting = true;
        hitDelay = new Timer();
        hitDelay.schedule(new TimerTask() {
            @Override
            public void run() {
                ap.gameManager.cc.checkPlayer(e);
                if (collisionOn && !e.inLastStand) {
                    e.loadout.health -= 50;
                    e.loadout.healing = false;
                    if (e.loadout.loadoutTimer != null) {
                        e.loadout.loadoutTimer.cancel();
                    }
                }

                hitting = false;

            }
        }, 300);
    }

    public static void increaseDefaultHealth(int round) {
        if (round < 10) {
            defaultHealth+= 100;
        }
        else defaultHealth *= 1.1f;

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
