package etat.apothicon.entity;

import etat.apothicon.perk.Perk;
import etat.apothicon.main.Apothicon;
import etat.apothicon.main.KeyInput;
import etat.apothicon.perk.PerkMachine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity {

    Apothicon ap;
    KeyInput keyIn;

    int defaultHealth;
    int health;
    int currentWeapon;
    ArrayList<Perk> perks;
    ArrayList<Gun> guns;

    public Player(Apothicon ap, KeyInput keyIn) {
        this.ap = ap;
        this.keyIn = keyIn;
        this.guns = new ArrayList<>();
        this.perks = new ArrayList<>();

        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(new File("src/main/resources/player/player-down1.png"));
            up2 = ImageIO.read(new File("src/main/resources/player/player-down2.png"));
            down1 = ImageIO.read(new File("src/main/resources/player/player-up1.png"));
            down2 = ImageIO.read(new File("src/main/resources/player/player-up2.png"));
            left1 = ImageIO.read(new File("src/main/resources/player/player-left1.png"));
            left2 = ImageIO.read(new File("src/main/resources/player/player-left2.png"));
            right1 = ImageIO.read(new File("src/main/resources/player/player-right1.png"));
            right2 = ImageIO.read(new File("src/main/resources/player/player-right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setDefaultValues() {
        this.x = 100;
        this.y = 100;
        this.health = 150;
        this.defaultHealth = 150;
        this.speed = 4;
        this.direction = "down";
        Gun m1911 = new Gun(10, 8, 32, 1, 100);
        guns.add(m1911);
        this.currentWeapon = 0;
    }

    public void update() {
        if (keyIn.upPressed || keyIn.downPressed || keyIn.leftPressed || keyIn.rightPressed) {
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

            spriteCounter++;
            if (spriteCounter > 12) { // 10 frames
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        if (keyIn.fPressed && isPlayerInPerkMachineArea(this, ap.getJug())) {
            ap.getJug().purchase(this);
        }
    }

    public boolean isPlayerInPerkMachineArea(Player player, PerkMachine perkMachine) {
        int playerLeft = player.x;
        int playerRight = player.x + ap.tileSize;
        int playerTop = player.y;
        int playerBottom = player.y + ap.tileSize;

        int perkLeft = perkMachine.x;
        int perkRight = perkMachine.x + ap.tileSize;
        int perkTop = perkMachine.y;
        int perkBottom = perkMachine.y + ap.tileSize;

        boolean isInArea = playerRight >= perkLeft &&
                playerLeft <= perkRight &&
                playerBottom >= perkTop &&
                playerTop <= perkBottom;

        return isInArea;
    }

    public void draw(Graphics2D g2) {
        for (Perk perk : this.perks) {
            perk.draw(g2);
        }

        BufferedImage image = null;
        switch (this.direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                    break;
                }
                image = up2;
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                    break;
                }
                image = down2;
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                    break;
                }
                image = left2;
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                    break;
                }
                image = right2;
                break;
        }
        g2.drawImage(image, this.x, this.y, ap.tileSize, ap.tileSize, null);

    }


    public void addPerk(Perk perk) {
        this.perks.add(perk);
    }

    public int getDefaultHealth() {
        return defaultHealth;
    }

    public void setDefaultHealth(int defaultHealth) {
        this.defaultHealth = defaultHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public void switchWeapon() {
        if ((currentWeapon + 1) != guns.size()) {
            this.currentWeapon++;
        } else this.currentWeapon = 0;
    }
}
