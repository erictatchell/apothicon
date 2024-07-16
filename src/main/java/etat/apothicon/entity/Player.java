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
import etat.apothicon.object.perk.machine.PerkMachine;
import etat.apothicon.object.weapon.gun.M14_Gun;
import etat.apothicon.object.weapon.gun.MP40_Gun;
import etat.apothicon.object.weapon.gun.Olympia_Gun;
import etat.apothicon.object.weapon.gun.SelectFire;
import etat.apothicon.object.weapon.gun.Stakeout_Gun;
import etat.apothicon.object.weapon.wallbuy.WallBuy;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

public class Player extends Entity {

    Apothicon ap;
    KeyInput keyIn;
    MouseInput mouseIn;

    public Loadout loadout;

    private String purchaseString = null;
    public final int screenX;
    public final int screenY;
    // for player perks. gross
    private int perkOffset = 16;
    private int slotX = 16;

    public Player(Apothicon ap, KeyInput keyIn, MouseInput mouseIn) {
        this.ap = ap;
        this.keyIn = keyIn;
        this.mouseIn = mouseIn;
        this.loadout = new Loadout(this);
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
        this.slotX = 16;
        this.perkOffset = 16;
        this.speed = 4;
        this.direction = "down";
    }

    int shotCount = 0;

    public void update() {
        this.slotX = perkOffset;
        int objIndex = ap.cc.checkObject(this, true);

        if (mouseIn.leftMousePressed) {
            shotCount = loadout.fireWeapon(shotCount);
            if (shotCount >= 5) {
                loadout.getCurrentWeapon().bullet = 0;
            }
        } else {
            shotCount = loadout.rechamberWeapon(shotCount);
            // on release, reset shotCount
        }

        // cycle weapons. set 1pressed to false so we dont constantly switch
        // find better sol?
        if (keyIn._1Pressed) {
            loadout.switchWeapon();
            keyIn._1Pressed = false;
        }
        if (keyIn.fPressed) {
            pickUpObject(objIndex);
        }

        boolean moving = false;

        // First, set the direction and check for collision
        if (keyIn.upPressed) {
            direction = "up";
            collisionOn = false;
            ap.cc.checkTile(this);
            if (!collisionOn) {
                this.worldY -= this.speed;
            }
            moving = true;
        }
        if (keyIn.downPressed) {
            direction = "down";
            collisionOn = false;
            ap.cc.checkTile(this);
            if (!collisionOn) {
                this.worldY += this.speed;
            }
            moving = true;
        }
        if (keyIn.leftPressed) {
            direction = "left";
            collisionOn = false;
            ap.cc.checkTile(this);
            if (!collisionOn) {
                this.worldX -= this.speed;
            }
            moving = true;
        }
        if (keyIn.rightPressed) {
            direction = "right";
            collisionOn = false;
            ap.cc.checkTile(this);
            if (!collisionOn) {
                this.worldX += this.speed;
            }
            moving = true;
        }

        // Handle sprite animation
        if (moving) {
            // Check for non-interactive pickups (powerups)
            pickUpObject(objIndex);

            spriteCounter++;
            if (spriteCounter > 12) { // 12 frames
                spriteNum = (spriteNum == 1) ? 2 : 1;
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

    public void pickUpObject(int index) {
        if (index != 999) {
            SuperObject obj = ap.obj[index];
            switch (obj.type) {
                case "perk":
                    PerkMachine perkMachine = (PerkMachine) obj;
                    boolean isPerkPurchasable = loadout.isPerkPurchasable(perkMachine);
                    if (isPerkPurchasable) {
                        if (keyIn.fPressed) {
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
        int centerX = ap.screenWidth / 2;
        int centerY = ap.screenHeight / 2;

        int extendLength = calculateDistanceFromNearestCollision();
        for (int i = 0; i < loadout.getCurrentWeapon().bullet; i++) {
            int deltaX = mousePosition.x - centerX;
            int deltaY = mousePosition.y - centerY;

            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            double directionX = deltaX / distance;
            double directionY = deltaY / distance;

            int extendedX = (int) (centerX + directionX * extendLength);
            int extendedY = (int) (centerY + directionY * extendLength);

            g2.drawLine(centerX, centerY, extendedX, extendedY);
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

        // Display weapon image, flips based on mouse
        int angle = calculateAngle();
        // omg kms
        BufferedImage weaponImage = ImageManager.createFlipped(getImage(mousePosition));
        if (mousePosition.x > ap.screenWidth / 2) {
            angle -= 180;
        }
        g2.drawImage(rotateImageByDegrees(weaponImage, angle), screenX, screenY,
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
     * @return an image based on the following:
     *         mousePosition > middle = image1 (left facing barrel)
     *         mousePosition < middle = image2 (right facing barrel)
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
