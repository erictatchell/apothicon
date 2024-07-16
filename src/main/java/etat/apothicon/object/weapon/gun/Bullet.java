package etat.apothicon.object.weapon.gun;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import etat.apothicon.entity.Entity;
import etat.apothicon.main.Apothicon;

public class Bullet {
    public double x, y;
    public double direction; // Angle in degrees
    public double speed;
    public Rectangle solidArea;
    
    public Bullet(double x, double y, double direction, double speed) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
        this.solidArea = new Rectangle((int)x, (int)y, 5, 5); // Assume a 5x5 bullet
    }
    
    public void update() {
        x += speed * Math.cos(Math.toRadians(direction));
        y += speed * Math.sin(Math.toRadians(direction));
        solidArea.setLocation((int)x, (int)y);
    }

    
    public boolean checkCollision(Entity entity) {
        return solidArea.intersects(entity.solidArea);
    }
}
