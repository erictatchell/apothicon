package etat.apothicon.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import etat.apothicon.main.Apothicon;

public class Zombie extends Entity {
    Apothicon ap;
    public final int screenX;
    public final int screenY;

    public Zombie(Apothicon ap) {
        super(ap);
        direction = "down";
        speed = 2;
        this.ap = ap;
        solidArea = new Rectangle(8, 16, 32, 32);
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

    public void setAction() {
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
