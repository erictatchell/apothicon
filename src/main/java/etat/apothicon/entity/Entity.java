package etat.apothicon.entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int x;
    public int y;
    public int speed;

    public BufferedImage up1, up2, left1, left2, right1, right2, down1, down2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    
}
