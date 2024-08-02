package etat.apothicon.object.weapon.gun;

import etat.apothicon.entity.Entity;
import etat.apothicon.main.Apothicon;
import etat.apothicon.utility.sound.ImpactSound;
import etat.apothicon.utility.sound.SoundType;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Bullet extends Entity {

    Gun gun;
    Entity user;
    public Rectangle zombieSolidArea = new Rectangle();

    int hitZombie = 999;
    private HashMap<Integer, Integer> hitZombiesTable;
    private ArrayList<Integer> hitZombiesArray;
    private int penetrations;
    Random r = new Random();

    public Bullet(Apothicon ap, Gun gun) {

        super(ap);
        this.gun = gun;
        hitZombiesTable = new HashMap<>();
        penetrations = gun.penetration;
        solidArea.x = 15;
        solidArea.y = 15;
        solidArea.width = 1;
        solidArea.height = 1;
        zombieSolidArea.x = 2;
        zombieSolidArea.y = 2;
        zombieSolidArea.width = 15;
        zombieSolidArea.height = 15;
    }

    public void set(int worldX, int worldY, int direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.directionAngle = direction;
        this.alive = alive;
        this.user = user;
    }

    public int generateDeathSound() {
        int sound = ImpactSound.HIT1.ordinal();
        int rn = r.nextInt(6) + 1;
        switch (rn) {
            case 1 -> {
            }
            case 2 -> sound = ImpactSound.HIT2.ordinal();
            case 3 -> sound = ImpactSound.HIT3.ordinal();
            case 4 -> sound = ImpactSound.HIT4.ordinal();
            case 5 -> sound = ImpactSound.HIT5.ordinal();
            case 6 -> sound = ImpactSound.HIT6.ordinal();
        }
        return sound;
    }

    /**
     * @return true if bullet can pass thorugh, false if it is destroyed
     */
    public boolean checkPenetration() {
        if (penetrations > 0) {
            penetrations--;

            return true;
        } else {
            ap.gameManager.bullets.remove(this);
            alive = false;
            hitZombiesTable.clear();
            System.gc();
            return false;
        }
    }


    public void update() {
        if (user == this.ap.gameManager.player) {
            int zombieIndex = ap.gameManager.cc.bullet_checkEntity(this, ap.gameManager.roundManager.getZombies());

            // if we hit a zombie
            if (zombieIndex != 999) {

                // if the zombie is not already hit by this bullet
                boolean zombieIsHit = hitZombiesTable.get(zombieIndex) != null;
                if (!zombieIsHit && checkPenetration()) {
                    ap.gameManager.player.damageZombie(collisionIsHeadshot, zombieIndex);
                    hitZombiesTable.put(zombieIndex, 1);
                }

                ap.playSE(generateDeathSound(), SoundType.IMPACT);

            }
        }

        if (alive) {
            worldX += (int) (speed * Math.cos(Math.toRadians(directionAngle)));
            worldY += (int) (speed * Math.sin(Math.toRadians(directionAngle)));
            ap.gameManager.cc.bullet_checkTile(this);
            if (this.collisionIsWall) {
                System.out.println("hello");
                ap.playSE(ImpactSound.WALL_COLLISION.ordinal(), SoundType.IMPACT);
                ap.gameManager.bullets.remove(this);
                System.gc();
            }

            spriteCounter++;
            if (spriteCounter > 120) {
                alive = false;
                spriteCounter = 0;
            }
        }

    }

    public void drawBullet(Graphics2D g2) {
        int screenX = worldX - ap.gameManager.player.worldX + ap.gameManager.player.screenX;
        int screenY = worldY - ap.gameManager.player.worldY + ap.gameManager.player.screenY;

        if (worldX + ap.tileSize > ap.gameManager.player.worldX - ap.gameManager.player.screenX
                && worldX - ap.tileSize < ap.gameManager.player.worldX + ap.gameManager.player.screenX
                && worldY + ap.tileSize > ap.gameManager.player.worldY - ap.gameManager.player.screenY
                && worldY - ap.tileSize < ap.gameManager.player.worldY + ap.gameManager.player.screenY) {

            g2.setColor(Color.yellow);
            g2.fillRect(screenX, screenY, 5, 5);

        }
    }

    public boolean checkCollision(Entity entity) {
        return solidArea.intersects(entity.solidArea);
    }
}
