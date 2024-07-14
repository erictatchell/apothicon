package etat.apothicon.entity;

import etat.apothicon.main.Apothicon;
import etat.apothicon.main.KeyInput;
import etat.apothicon.main.MouseInput;
import etat.apothicon.object.SuperObject;
import etat.apothicon.object.perk.bottle.DoubleTap;
import etat.apothicon.object.perk.bottle.Juggernog;
import etat.apothicon.object.perk.bottle.MuleKick;
import etat.apothicon.object.perk.bottle.Perk;
import etat.apothicon.object.perk.bottle.QuickRevive;
import etat.apothicon.object.perk.bottle.SpeedCola;
import etat.apothicon.object.weapon.gun.Gun;
import etat.apothicon.object.weapon.gun.M14_Gun;
import etat.apothicon.object.weapon.gun.M1911_Gun;
import etat.apothicon.object.weapon.gun.MP40_Gun;
import etat.apothicon.object.weapon.gun.Olympia_Gun;
import etat.apothicon.object.weapon.gun.Stakeout_Gun;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.BiFunction;

public class Player extends Entity {

    Apothicon ap;
    KeyInput keyIn;
    MouseInput mouseIn;

    private String purchaseString = null;
    public final int screenX;
    public final int screenY;
    // for player perks. gross
    private int perkOffset = 16;
    private int slotX = 16;

    private int revives;
    private int points;
    private float reloadRateMultiplier;
    private float fireRateMultiplier;
    private int defaultHealth;
    private int health;
    private int currentWeapon;
    private ArrayList<Perk> perks;
    private int maxGunNum;
    private ArrayList<Gun> guns;

    public Player(Apothicon ap, KeyInput keyIn, MouseInput mouseIn) {
        this.ap = ap;
        this.keyIn = keyIn;
        this.mouseIn = mouseIn;
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
        this.reloadRateMultiplier = 1.0f;
        this.fireRateMultiplier = 1.0f;
        M1911_Gun m1911 = new M1911_Gun();
        this.guns.add(m1911);
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

    public int getGunNum() {
        return this.maxGunNum;
    }

    public boolean isAmmoPurchasable(int price) {
        Gun current = this.guns.get(currentWeapon);
        return (this.points > price) && (current.reserve != current.defaultReserve);
    }

    public boolean isGunPurchasable(SuperObject gun) {
        int n = this.maxGunNum;
        int m = this.guns.size();
        for (int i = 0; i < m; i++) {
            if (this.guns.get(i).getName() == gun.name) {
                return false;

            }
        }
        if (m >= n) {
            this.guns.remove(currentWeapon);
            return true;
        }

        if (this.points < gun.price) {
            return false;
        }

        return true;
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

    int shotCount = 0;

    public void update() {
        this.slotX = perkOffset;
        int objIndex = ap.cc.checkObject(this, true);

        if (mouseIn.leftMousePressed) {

            // simulate time/fire rate
            // only guns with fire rate 1.0f and above will be impacted by this
            // all other guns are semi auto and can fire as fast as user can trigger pull
            shotCount++;
            if (shotCount > this.guns.get(currentWeapon).shotCount) {
                this.guns.get(currentWeapon).fire();
                shotCount = 0;

            }
        } else {

            // on release, reset shotCount
            shotCount = this.guns.get(currentWeapon).shotCount;
            this.guns.get(currentWeapon).rechamberNeeded = false;
        }

        // cycle weapons. set 1pressed to false so we dont constantly switch
        // find better sol?
        if (keyIn._1Pressed) {
            switchWeapon();
            keyIn._1Pressed = false;
        }
        if (keyIn.fPressed) {
            pickUpObject(objIndex);

        }
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

            collisionOn = false;
            ap.cc.checkTile(this);
            // for non interact pickups (powerups)
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
    }

    public void purchaseAmmo(SuperObject object, int price) {
        Gun current = this.guns.get(currentWeapon);
        // currently, user must be holding the weapon to buy ammo
        // change?
        if (current.getName() == object.name) {

            current.reserve = current.defaultReserve;
            current.magazine = current.defaultAmmoPerMagazine;
            this.points -= price;
        }
    }

    /**
     * wall weapon purchase
     * 
     * @param object interactable object (wallbuy)
     */
    public void purchaseGun(SuperObject object) {
        switch (object.name) {
            case "MP40":
                MP40_Gun mp40 = new MP40_Gun();
                this.guns.add(mp40);
                this.points -= object.price;
                this.currentWeapon = this.guns.size() - 1;
                break;
            case "M14":
                M14_Gun m14 = new M14_Gun();
                this.guns.add(m14);
                this.points -= object.price;
                this.currentWeapon = this.guns.size() - 1;
                break;
            case "Olympia":
                Olympia_Gun o = new Olympia_Gun();
                this.guns.add(o);
                this.points -= object.price;
                this.currentWeapon = this.guns.size() - 1;
                break;
            case "Stakeout":
                Stakeout_Gun st = new Stakeout_Gun();
                this.guns.add(st);
                this.points -= object.price;
                this.currentWeapon = this.guns.size() - 1;
                break;
        }
    }

    /**
     * perk machine purchases
     * 
     * @param object interactable object (perk machine)
     */
    public void purchasePerk(SuperObject object) {
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

    // onscreen "Press F to buy ..."
    public void drawPurchaseText(String name, int price) {
        this.purchaseString = "Press F to buy " + name + " (" + price + ")";
    }

    // rn, only used to reset. simplify?
    public void drawPurchaseText(String name) {
        this.purchaseString = name;
    }

    public void pickUpObject(int index) {
        if (index != 999) {
            SuperObject obj = ap.obj[index];
            switch (obj.type) {
                case "perk":
                    boolean isPerkPurchasable = isPerkPurchasable(obj);
                    if (isPerkPurchasable) {
                        if (keyIn.fPressed) {
                            purchasePerk(obj);
                            break;
                        }

                        drawPurchaseText(obj.name, obj.price);
                        break;
                    }
                    this.purchaseString = null;
                    break;
                case "gun":

                    // if the gun is purchased, we're buying ammo for it
                    boolean buyingAmmo = isGunPurchased(obj);

                    if (!buyingAmmo) {
                        if (keyIn.fPressed && isGunPurchasable(obj)) {
                            purchaseGun(obj);
                        }

                        drawPurchaseText(obj.name, obj.price);
                        break;
                    } else {
                        int ammoCost = obj.price / 2;
                        if (keyIn.fPressed && isAmmoPurchasable(ammoCost)) {
                            purchaseAmmo(obj, ammoCost);
                        }
                        drawPurchaseText("ammo", ammoCost);
                        break;
                    }
            }
        } else {
            // reset purchase txt
            this.purchaseString = null;
        }
    }

    private boolean isGunPurchased(SuperObject obj) {
        for (Gun gun : this.guns) {
            if (obj.name == gun.getName()) {

                return true;
            }
        }
        return false;
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

        // gun on screen. horrible
        g2.drawImage(getCurrentWeapon().image, screenX, screenY, ap.tileSize, ap.tileSize, null);

        if (this.purchaseString != null) {
            g2.setColor(Color.white);
            Font font = new Font("Arial", Font.BOLD, 16);
            g2.setFont(font);

            g2.drawString(purchaseString, ap.screenWidth / 2, ap.screenHeight / 2);

        }

    }




    public void resetPerkOffset() {
        this.perkOffset = 0;
    }

    public void incrementPerkOffset() {
        // 40 just works/looks good LOL
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

    public Gun getCurrentWeapon() {
        return this.guns.get(currentWeapon);
    }

    public void setCurrentWeapon(int currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public ArrayList<Perk> getPerks() {
        return perks;
    }

    public int getRevives() {
        return revives;
    }

    public void setRevives(int revives) {
        this.revives = revives;
    }

    public float getReloadRate() {
        return reloadRateMultiplier;
    }

    public void setReloadRate(float reloadRate) {
        this.reloadRateMultiplier = reloadRate;
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
