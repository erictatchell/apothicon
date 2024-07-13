package etat.apothicon.entity;

import etat.apothicon.perk.DoubleTap;
import etat.apothicon.perk.Juggernog;
import etat.apothicon.perk.MuleKick;
import etat.apothicon.perk.Perk;
import etat.apothicon.perk.PerkMachine;
import etat.apothicon.perk.QuickRevive;
import etat.apothicon.perk.SpeedCola;
import etat.apothicon.main.Apothicon;
import etat.apothicon.main.KeyInput;
import etat.apothicon.object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity {

    // TODO: oop-ify this!

    Apothicon ap;
    KeyInput keyIn;

    private String purchaseString = null;
    public final int screenX;
    public final int screenY;
    // TODO 1: man this is ugly. find a better solution
    private int perkOffset = 16;
    private int slotX = 16;

    private int revives;
    private int points;
    private float reloadRate;
    private int defaultHealth;
    private int health;
    private int currentWeapon;
    private ArrayList<Perk> perks;
    private int maxGunNum;
    private ArrayList<Gun> guns;

    public Player(Apothicon ap, KeyInput keyIn) {
        this.ap = ap;
        this.keyIn = keyIn;
        this.guns = new ArrayList<>();
        this.perks = new ArrayList<>();
        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        screenX = ap.screenWidth / 2 - (ap.tileSize / 2);
        screenY = ap.screenHeight / 2 - (ap.tileSize / 2);
        setDefaultValues();
        getPlayerImage();
    }

    public int getRevives() {
        return revives;
    }

    public void setRevives(int revives) {
        this.revives = revives;
    }

    public float getReloadRate() {
        return reloadRate;
    }

    public void setReloadRate(float reloadRate) {
        this.reloadRate = reloadRate;
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
        this.worldX = ap.tileSize * 29;
        this.worldY = ap.tileSize * 43;
        this.points = 20000;
        this.revives = 0;
        this.slotX = 16;
        this.perkOffset = 16;
        this.health = 150;
        this.defaultHealth = 150;
        this.speed = 4;
        this.maxGunNum = 2;
        this.direction = "down";
        this.reloadRate = 1.0f;
        Gun m1911 = new Gun(10, 8, 32, 1, 100);
        guns.add(m1911);
        this.currentWeapon = 0;
    }

    public void setGunNum(int x) {
        this.maxGunNum = x;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isPerkPurchasable(SuperObject perk) {
        int n = this.perks.size();
        if (n >= 4) {
            return false;
        } // max perks

        String purchasingName = perk.name;
        for (int i = 0; i < n; i++) {
            if (purchasingName == this.perks.get(i).getName()) {
                return false;
            }
        }
        return true;
    }

    public void update() {
        this.slotX = perkOffset;
        if (keyIn.upPressed || keyIn.downPressed || keyIn.leftPressed || keyIn.rightPressed) {
            if (keyIn.upPressed) {
                direction = "up";
            }
            if (keyIn.downPressed) {
                direction = "down";
            }
            if (keyIn.leftPressed) {
                direction = "left";
            }
            if (keyIn.rightPressed) {
                direction = "right";
            }

            // tile collision
            collisionOn = false;
            ap.cc.checkTile(this);
            int objIndex = ap.cc.checkObject(this, true);
            pickUpObject(objIndex);

            if (!collisionOn) {
                if (direction == "up") {

                    this.worldY -= this.speed;
                }
                if (direction == "down") {

                    this.worldY += this.speed;
                }
                if (direction == "left") {

                    this.worldX -= this.speed;
                }
                if (direction == "right") {

                    this.worldX += this.speed;
                }

            }

            spriteCounter++;
            if (spriteCounter > 12) { // 12 frames
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        // if (keyIn.fPressed) {
        // if (isPlayerInPerkMachineArea(this, ap.getJug()) && this.points >=
        // ap.getJug().getPrice()) {
        // ap.getJug().purchase(this);
        // }
        // if (isPlayerInPerkMachineArea(this, ap.getQuickRevive()) && this.points >=
        // ap.getQuickRevive().getPrice()) {
        // ap.getQuickRevive().purchase(this);
        // }
        // if (isPlayerInPerkMachineArea(this, ap.getSpeedCola()) && this.points >=
        // ap.getSpeedCola().getPrice()) {
        // ap.getSpeedCola().purchase(this);
        // }
        // if (isPlayerInPerkMachineArea(this, ap.getDoubleTap()) && this.points >=
        // ap.getDoubleTap().getPrice()) {
        // ap.getDoubleTap().purchase(this);
        // }
        // }
    }

    public void purchasePerk(SuperObject object) {
        Graphics2D g2;
        switch (object.name) {
            case "Juggernog":
                Juggernog jug = new Juggernog(this, ap);
                jug.activateFor(this);
                break;
            case "Double Tap 2.0":
                DoubleTap dt = new DoubleTap(this, ap);
                dt.activateFor(this);
                drawPurchaseText(object.name, 2000);
                break;
            case "Speed Cola":
                SpeedCola sc = new SpeedCola(this, ap);
                sc.activateFor(this);
                drawPurchaseText(object.name, 2000);
                break;
            case "Quick Revive":
                QuickRevive qr = new QuickRevive(this, ap);
                qr.activateFor(this);
                drawPurchaseText(object.name, 2000);
                break;
            case "Mule Kick":
                MuleKick mk = new MuleKick(this, ap);
                mk.activateFor(this);
                drawPurchaseText(object.name, 2000);
                break;
        }
    }

    public void drawPurchaseText(String name, int price) {
        this.purchaseString = "Press F to buy " + name + " (" + price + ")";
    }

    public void drawPurchaseText(String name) {
        this.purchaseString = name;
    }

    public void pickUpObject(int index) {
        if (index != 999) {

            switch (ap.obj[index].type) {
                case "perk":
                    if (keyIn.fPressed && isPerkPurchasable(ap.obj[index])) {
                        purchasePerk(ap.obj[index]);
                    }

                    drawPurchaseText(ap.obj[index].name, ap.obj[index].price);
            }

            // ap.obj[index] = null;
        } else {

            drawPurchaseText(null);
        }

    }

    public boolean isPlayerInPerkMachineArea(Player player, PerkMachine perkMachine) {
        int playerLeft = player.worldX;
        int playerRight = player.worldX + ap.tileSize;
        int playerTop = player.worldY;
        int playerBottom = player.worldY + ap.tileSize;

        int perkLeft = perkMachine.worldX;
        int perkRight = perkMachine.worldX + ap.tileSize;
        int perkTop = perkMachine.worldY;
        int perkBottom = perkMachine.worldY + ap.tileSize;

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
        g2.drawImage(image, screenX, screenY, ap.tileSize, ap.tileSize, null);
        if (this.purchaseString != null) {
            g2.setColor(Color.white);
            Font font = new Font("Arial", Font.BOLD, 24);
            g2.setFont(font);

            g2.drawString(purchaseString, ap.screenWidth / 2, ap.screenHeight / 2);

        }
    }

    public void resetPerkOffset() {
        this.perkOffset = 0;
    }

    public void incrementPerkOffset() {
        this.perkOffset += 40;
    }

    public int getSlotX() {
        return slotX;
    }

    public void setSlotX(int slotX) {
        this.slotX = slotX;
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
        } else
            this.currentWeapon = 0;
    }
}
