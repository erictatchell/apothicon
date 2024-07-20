package etat.apothicon.object.weapon.gun;

import etat.apothicon.entity.Entity;
import etat.apothicon.main.Apothicon;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Bullet extends Entity {

    Gun gun;
    Entity user;
    public Rectangle zombieSolidArea = new Rectangle();

    public Bullet(Apothicon ap) {

        super(ap);
        solidArea.x = 15;
        solidArea.y = 15;
        solidArea.width = 1;
        solidArea.height = 1;
        zombieSolidArea.x = 2;
        zombieSolidArea.y = 2;
        zombieSolidArea.width = 15;
        zombieSolidArea.height = 15;
    }

    public void set(int worldX, int worldY, int direction, boolean alive, Entity user, Gun gun) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.directionAngle = direction;
        this.alive = alive;
        this.user = user;
        this.gun = gun;
    }

    public void update() {
        if (user == this.ap.gameState.player) {
            int zombieIndex = ap.cc.bullet_checkEntity(this, ap.gameState.zombies);
            if (zombieIndex != 999) {
                ap.gameState.player.damageZombie(zombieIndex);
                alive = false;
            }
        }
        if (user != this.ap.gameState.player) {

        }
        worldX += speed * Math.cos(Math.toRadians(directionAngle));
        worldY += speed * Math.sin(Math.toRadians(directionAngle));
        ap.cc.bullet_checkTile(this);
        if (this.collisionOn) {
            ap.gameState.bullets.remove(this);
        }

        System.out.println(directionAngle);
        spriteCounter++;
        if (spriteCounter > 120) {
            alive = false;
            spriteCounter = 0;
        }
    }

    public void drawBullet(Graphics2D g2) {
        int screenX = worldX - ap.gameState.player.worldX + ap.gameState.player.screenX;
        int screenY = worldY - ap.gameState.player.worldY + ap.gameState.player.screenY;

        if (worldX + ap.tileSize > ap.gameState.player.worldX - ap.gameState.player.screenX
                && worldX - ap.tileSize < ap.gameState.player.worldX + ap.gameState.player.screenX
                && worldY + ap.tileSize > ap.gameState.player.worldY - ap.gameState.player.screenY
                && worldY - ap.tileSize < ap.gameState.player.worldY + ap.gameState.player.screenY) {

            g2.setColor(Color.yellow);
            g2.fillRect(screenX, screenY, 5, 5);

        }
    }

    public boolean checkCollision(Entity entity) {
        return solidArea.intersects(entity.solidArea);
    }
}
