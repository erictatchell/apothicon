package etat.apothicon.entity;

import etat.apothicon.main.Apothicon;
import etat.apothicon.main.KeyInput;
import etat.apothicon.main.MouseInput;
import etat.apothicon.object.SuperObject;
import etat.apothicon.object.perk.bottle.Perk;
import etat.apothicon.object.perk.machine.PerkMachine;
import etat.apothicon.object.weapon.gun.Bullet;
import etat.apothicon.object.weapon.gun.FireType;
import etat.apothicon.object.weapon.wallbuy.WallBuy;
import etat.apothicon.sound.InteractSound;
import etat.apothicon.sound.SoundHandler;
import etat.apothicon.sound.SoundType;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class Player extends Entity {

    KeyInput keyIn;
    MouseInput mouseIn;
    public Apothicon ap;

    public Loadout loadout;
    public ArrayList<Bullet> bullets = new ArrayList<>();

    private String purchaseString = null;
    public final int screenX;
    public final int screenY;
    // for player perks. gross
    private int perkOffset = 16;
    private int slotX = 16;

    public Player(Apothicon ap, KeyInput keyIn, MouseInput mouseIn) {
        super(ap);
        this.ap = ap;
        this.keyIn = keyIn;
        this.mouseIn = mouseIn;
        this.loadout = new Loadout(this, this.ap);
        this.bullets = new ArrayList<>();
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 30;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        screenX = ap.screenWidth / 2 - (ap.tileSize / 2);
        screenY = ap.screenHeight / 2 - (ap.tileSize / 2);
        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage() {
        up1 = setup("player/player-up1.png");
        up2 = setup("player/player-up2.png");
        down1 = setup("player/player-down1.png");
        down2 = setup("player/player-down2.png");
        left1 = setup("player/player-left1.png");
        left2 = setup("player/player-left2.png");
        right1 = setup("player/player-right1.png");
        right2 = setup("player/player-right2.png");
    }

    public void setDefaultValues() {
        this.worldX = ap.tileSize * 29;
        this.worldY = ap.tileSize * 43;
        this.slotX = 16;
        this.perkOffset = 16;
        this.speed = 4;
        this.direction = "down";
    }

    public void update() {
        // for perk placement
        this.slotX = perkOffset;

        // if we want to shoot, don't need to rechamber, and the delay is up
        if (!loadout.getCurrentWeapon().rechamberNeeded && mouseIn.leftMousePressed
                && loadout.getCurrentWeapon().fireDelayCounter == loadout.getCurrentWeapon().fireDelay) {

            loadout.fireWeapon();
            if (loadout.getCurrentWeapon().fireType == FireType.SEMI_AUTO) {
                // rechamber needed, prevent full auto on semi auto guns
                loadout.getCurrentWeapon().rechamberNeeded = true;
            }
            loadout.getCurrentWeapon().fireDelayCounter = 0;
        } // if semi auto, rechamber on trigger release
        else if (loadout.getCurrentWeapon().fireType == FireType.SEMI_AUTO && !mouseIn.leftMousePressed) {
            loadout.getCurrentWeapon().rechamberNeeded = false;
        }

        // inc fire delay
        if (loadout.getCurrentWeapon().fireDelayCounter < loadout.getCurrentWeapon().fireDelay) {

            loadout.getCurrentWeapon().fireDelayCounter += (1 * this.loadout.getFireRateMultiplier());
        }

        // cycle weapons. set to false so we dont constantly switch
        // find better sol?
        if (keyIn.switchWpnPressed) {
            loadout.switchWeapon();
            keyIn.switchWpnPressed = false;
        }

        int objIndex = ap.gameState.cc.checkObject(this, true);
        if (keyIn.fPressed) {
            pickUpObject(objIndex);
        }

        if (keyIn.reloadPressed) {
            this.loadout.getCurrentWeapon().handleReload();
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
            ap.gameState.cc.checkTile(this);
            // for non interact pickups (powerups)
            pickUpObject(objIndex);

            // zombie collision
            int zombieIndex = ap.gameState.cc.checkEntity(this, ap.gameState.zombies);

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

    // onscreen "Press F to buy ..."
    public void drawPurchaseText(String name, int price) {
        this.purchaseString = "Press F to buy " + name + " (" + price + ")";
    }

    // rn, only used to reset. simplify?
    public void drawPurchaseText(String name) {
        this.purchaseString = name;
    }

    /**
     * @param index position i
     */
    public void damageZombie(int index) {
        if (index != 999) {
            Entity zombie = ap.gameState.zombies[index];

            boolean killed = false;
            boolean headshot = false;

            int damage = (int) (loadout.getCurrentWeapon().getDamage() * loadout.getDamageMultiplier());
            zombie.takeDamage(damage);
            if (zombie.health <= 0) {
                zombie.die(index);
                killed = true;
            }
            loadout.addPoints(killed, headshot);
        }
    }

    public void pickUpObject(int index) {
        if (index != 999) {
            SuperObject obj = ap.gameState.obj[index];
            switch (obj.type) {
                case "perk":
                    PerkMachine perkMachine = (PerkMachine) obj;
                    boolean isPerkPurchasable = loadout.isPerkPurchasable(perkMachine);
                    if (isPerkPurchasable) {
                        if (keyIn.fPressed) {

                            ap.playSE(InteractSound.PURCHASE.ordinal(), SoundType.INTERACT);
                            loadout.purchasePerk(perkMachine);
                            break;
                        }

                        drawPurchaseText(perkMachine.name, perkMachine.price);
                        break;
                    }
                    this.purchaseString = null;
                    break;
                case "gun":
                    WallBuy wallBuy = (WallBuy) obj;
                    // if the gun is purchased, we're buying ammo for it
                    boolean buyingAmmo = loadout.isGunPurchased(wallBuy);

                    if (!buyingAmmo) {
                        if (keyIn.fPressed && loadout.isGunPurchasable(wallBuy)) {

                            ap.playSE(InteractSound.PURCHASE.ordinal(), SoundType.INTERACT);
                            loadout.purchaseGun(wallBuy);
                        }

                        drawPurchaseText(wallBuy.name, wallBuy.price);
                        break;
                    } else {
                        int ammoCost = wallBuy.price / 2;
                        if (keyIn.fPressed && loadout.isAmmoPurchasable(ammoCost)) {
                            loadout.purchaseAmmo(wallBuy, ammoCost);
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

    public void drawPerkIcons(Graphics2D g2) {
        for (Perk perk : loadout.getPerks()) {
            if (perk != null) {
                perk.draw(g2);
            }
        }
    }

    public int calculateDistanceFromNearestCollision() {
        return 1000;
    }

    public void draw(Graphics2D g2) {
        drawPerkIcons(g2);
        g2.setColor(Color.YELLOW);
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePosition, ap);
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

        // Display weapon image, flips based on mouse
        int angle = calculateAngle();
        // omg kms
        BufferedImage weaponImage = ImageManager.createFlipped(getImage(mousePosition));
        boolean mouseOnRightSide = mousePosition.x > ap.screenWidth / 2;
        if (mouseOnRightSide) {
            angle -= 180;
        }

        // decide which player side to display the weapon (left if left, right if right)
        int offset = 0;
        if (mouseOnRightSide) {
            offset = ap.tileSize / 2; // 24 default
        } else {
            offset = -(ap.tileSize / 2);
        }
        g2.drawImage(rotateImageByDegrees(weaponImage, angle), screenX + offset, screenY,
                ap.tileSize, ap.tileSize, null);

        if (this.purchaseString != null) {
            g2.setColor(Color.white);
            Font font = new Font("Arial", Font.BOLD, 16);
            g2.setFont(font);

            g2.drawString(purchaseString, ap.screenWidth / 2, ap.screenHeight / 2);

        }

    }

    /**
     * Return the current weapon image based on mouse
     *
     * @param mousePosition
     * @return an image based on the following: mousePosition > middle = image1
     *         (left facing barrel) mousePosition < middle = image2 (right facing
     *         barrel)
     */
    public BufferedImage getImage(Point mousePosition) {
        if (mousePosition.x > ap.screenWidth / 2) {
            return loadout.getCurrentWeapon().image;
        }
        return loadout.getCurrentWeapon().image2;
    }

    /**
     * Calculates the angle between mousePosition.x and centerscreen (player)
     *
     * @return angle in degrees
     */
    public int calculateAngle() {
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePosition, ap);
        int centerX = ap.screenWidth / 2;
        int centerY = ap.screenHeight / 2;

        int deltaX = mousePosition.x - centerX;
        int deltaY = mousePosition.y - centerY;

        // atan2 for / 0 error
        double angleInRadians = Math.atan2(deltaY, deltaX);

        return (int) Math.toDegrees(angleInRadians);
    }

    // Credit:
    // https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return rotated;
    }

    public void resetPerkOffset() {
        this.perkOffset = 0;
    }

    public void incrementPerkOffset() {
        // 35 just works/looks good LOL
        this.perkOffset += 35;
    }

    public int getSlotX() {
        return slotX;
    }

    public void setSlotX(int slotX) {
        this.slotX = slotX;
    }

}
