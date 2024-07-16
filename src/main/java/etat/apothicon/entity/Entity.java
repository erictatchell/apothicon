package etat.apothicon.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    // TODO: oop-ify this!
    public int worldX;
    public int worldY;
    public int speed;

    public BufferedImage up1, up2, left1, left2, right1, right2, down1, down2;
    public BufferedImage z1_up1, z1_up2, z1_left1, z1_left2, z1_right1, z1_right2, z1_down1, z1_down2;
    public BufferedImage z1_pr;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}
