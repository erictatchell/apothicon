package etat.apothicon.object.weapon.gun;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import etat.apothicon.entity.Entity;
import etat.apothicon.main.Apothicon;

public class Bullet extends Entity {
    Entity user;

    public Bullet(Apothicon ap) {
        super(ap);
        solidArea.x = 0;
        solidArea.y = 0; 
        solidArea.width = 5;
        solidArea.height = 5;
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
            ap.cc.bullet_checkTile(this);
            if (this.collisionOn) {
                ap.bullets.remove(this);
            }
            if (zombieIndex != 999) {
                ap.player.damageZombie(zombieIndex);
                alive = false;
            }
        }
        if (user != this.ap.player) {

        }
        worldX += speed * Math.cos(Math.toRadians(directionAngle));
        worldY += speed * Math.sin(Math.toRadians(directionAngle));

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
