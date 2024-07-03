package etat.apothicon.entity;

import etat.apothicon.entity.Gun;
import etat.apothicon.entity.Perk;
import etat.apothicon.main.Apothicon;
import etat.apothicon.main.KeyInput;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity {

    Apothicon ap;
    KeyInput keyIn;

    int speed;
    int currentWeapon;
    ArrayList<Perk> perks;
    ArrayList<Gun> guns;

    public Player(Apothicon ap, KeyInput keyIn) {
        this.ap = ap;
        this.keyIn = keyIn;

        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player-up1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player-up2.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player-down1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player-down2.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player-left1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player-left2.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player-right1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/player-right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        this.x = 100;
        this.y = 100;
        this.speed = 4;
        this.direction = "down";
    }

    public void update() {
        if (keyIn.upPressed) {
            direction = "up";
            this.y -= this.speed;
        } else if (keyIn.downPressed) {
            direction = "down";
            this.y += this.speed;
        } else if (keyIn.leftPressed) {
            direction = "left";
            this.x -= this.speed;
        } else if (keyIn.rightPressed) {
            direction = "right";
            this.x += this.speed;
        }
    }

    public void draw(Graphics2D g2) {
//        g2.setColor(Color.white);
//        g2.fillRect(this.x, this.y, ap.tileSize, ap.tileSize);
        BufferedImage image = null;
        switch (this.direction) {
            case "up":
                image = up1;
                break;
            case "down":
                image = down1;
                break;
            case "left":
                image = left1;
                break;
            case "right":
                image = right1;
                break;
        }
        g2.drawImage(image, this.x, this.y, ap.tileSize, ap.tileSize, null);
    }

    public void addPerk(Perk perk) {
        this.perks.add(perk);
    }

    public void switchWeapon() {
        if ((currentWeapon + 1) != guns.size()) {
            this.currentWeapon++;
        } else this.currentWeapon = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(int currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public ArrayList<Perk> getPerks() {
        return perks;
    }

    public void setPerks(ArrayList<Perk> perks) {
        this.perks = perks;
    }

    public ArrayList<Gun> getGuns() {
        return guns;
    }

    public void setGuns(ArrayList<Gun> guns) {
        this.guns = guns;
    }
}
