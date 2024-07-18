package etat.apothicon.object.weapon.gun;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import etat.apothicon.entity.Entity;
import etat.apothicon.main.Apothicon;

public class Bullet extends Entity {
    Gun gun;
    Entity user;

    public Bullet(Apothicon ap) {
        super(ap);
        solidArea.x = 15;
        solidArea.y = 15;
        solidArea.width = 1;
        solidArea.height = 1;
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
        if (user == this.ap.player) {
            int zombieIndex = ap.cc.checkOmnidirectionalEntity(this, ap.zombies);
            if (zombieIndex != 999) {
                ap.player.damageZombie(zombieIndex);
                alive = false;
            }
        }
        if (user != this.ap.player) {

        }
        worldX += speed * Math.cos(Math.toRadians(directionAngle));
        worldY += speed * Math.sin(Math.toRadians(directionAngle));
        ap.cc.bullet_checkTile(this);
        if (this.collisionOn) {
            ap.bullets.remove(this);
        }

        System.out.println(directionAngle);
        spriteCounter++;
        if (spriteCounter > 120) {
            alive = false;
            spriteCounter = 0;
        }
    }

    public void drawBullet(Graphics2D g2) {
        int screenX = worldX - ap.player.worldX + ap.player.screenX;
        int screenY = worldY - ap.player.worldY + ap.player.screenY;

        if (worldX + ap.tileSize > ap.player.worldX - ap.player.screenX &&
                worldX - ap.tileSize < ap.player.worldX + ap.player.screenX &&
                worldY + ap.tileSize > ap.player.worldY - ap.player.screenY &&
                worldY - ap.tileSize < ap.player.worldY + ap.player.screenY) {

            g2.setColor(Color.yellow);
            g2.fillRect(screenX, screenY, (1 * this.gun.damage) / 6, (1 * this.gun.damage) / 6);

        }
    }

    public boolean checkCollision(Entity entity) {
        return solidArea.intersects(entity.solidArea);
    }
}
