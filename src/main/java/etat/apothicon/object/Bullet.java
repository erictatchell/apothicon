package etat.apothicon.object;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;

import etat.apothicon.entity.Entity;
import etat.apothicon.main.Apothicon;
import etat.apothicon.object.weapon.gun.Gun;

/**
 * bullet class, we use its location data with Graphics2D drawLine
 */
public class Bullet extends Entity {
    // x2 y2 should be the collidable tile's world position
    // x1 y1 are just center screen
    boolean collisionOn;
    Gun gun;
    int x1, x2, y1, y2;
    public Bullet() { 
    }
}
