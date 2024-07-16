package etat.apothicon.object.weapon.gun;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import etat.apothicon.entity.Entity;
import etat.apothicon.main.Apothicon;

public class Bullet extends Entity {
    Entity user;

    public Bullet(Apothicon ap) {
        super(ap);
    }
    public void set(int worldX, int worldY, int direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.directionAngle = direction;
        this.alive = alive;
        this.user = user;
    }
    public void update() {
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
