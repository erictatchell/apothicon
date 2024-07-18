package etat.apothicon.object.weapon.gun;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import etat.apothicon.entity.Entity;
import etat.apothicon.main.Apothicon;

public class Bullet extends Entity {
    Entity user;

    public Bullet(Apothicon ap) {
        super(ap);
        solidArea.x = 15;
        solidArea.y = 15;
        solidArea.width = 1;
        solidArea.height = 1;
    }

    public void set(int worldX, int worldY, int direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.directionAngle = direction;
        this.alive = alive;
        this.user = user;
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

    public boolean checkCollision(Entity entity) {
        return solidArea.intersects(entity.solidArea);
    }
}
