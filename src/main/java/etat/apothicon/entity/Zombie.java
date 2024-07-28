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
                ap.gameState.cc.checkPlayer(e);
                if (collisionOn) {
                    e.loadout.health -= 50;
                    e.loadout.healing = false;
                    e.loadout.loadoutTimer.cancel();
                }

                hitting = false;

            }
        }, 500);
    }

    public void setAction() {
        if (onPath) {
            int goalCol = (ap.gameState.player.worldX + ap.gameState.player.solidArea.x) / ap.tileSize;
            int goalRow = (ap.gameState.player.worldY + ap.gameState.player.solidArea.y) / ap.tileSize;

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
