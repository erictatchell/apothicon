package etat.apothicon.entity;

import etat.apothicon.main.*;
import etat.apothicon.object.weapon.gun.Gun;
import etat.apothicon.round.Zone;
import etat.apothicon.object.SuperObject;
import etat.apothicon.object.perk.bottle.Perk;
import etat.apothicon.object.perk.machine.PerkMachine;
import etat.apothicon.object.weapon.gun.Bullet;
import etat.apothicon.object.weapon.gun.FireType;
import etat.apothicon.object.weapon.wallbuy.WallBuy;
import etat.apothicon.sound.InteractSound;
import etat.apothicon.sound.SoundType;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;

public class Player extends Entity {

    KeyInput keyIn;
    MouseInput mouseIn;
    public Apothicon ap;

    public Loadout loadout;
    public ArrayList<Bullet> bullets = new ArrayList<>();

    private String purchaseString = null;

    public Statistics statistics;

    public final int screenX;
    public final int screenY;
    // for player perks. gross
    private int perkOffset = 16;
    private int slotX = 16;
    public boolean dead = false;
    protected boolean inLastStand = false;


    public Player(Apothicon ap, KeyInput keyIn, MouseInput mouseIn) {
        super(ap);
        this.ap = ap;
        this.keyIn = keyIn;
        this.mouseIn = mouseIn;
        this.loadout = new Loadout(this, this.ap);
        this.bullets = new ArrayList<>();
        this.statistics = new Statistics(this);
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
        statistics.reset();
        this.worldX = ap.tileSize * 29;
        this.worldY = ap.tileSize * 43;
        this.slotX = 16;
        this.perkOffset = 16;
        this.speed = 4;
        this.direction = "down";
    }

    public void enterLastStand() {
        ap.gameManager.roundManager.togglePathfinding(false);
        speed = 1;

        inLastStand = true;
        Timer reviveTimer = new Timer();
        reviveTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ap.gameManager.roundManager.togglePathfinding(true);
                loadout.handleRevive();
                statistics.addDowns();
                speed = 4;
                inLastStand = false;
            }
        }, 8000);
    }

    public void update() {
        // last stand if player has quick revive
        if (!inLastStand && loadout.health <= 0 && loadout.hasQuickRevive) {
            enterLastStand();
        }
        else if (!inLastStand && loadout.health <= 0) {
            statistics.addDowns();
            dead = true;

        }

        // for perk placement
        this.slotX = perkOffset;
        Gun currentWeapon = loadout.getCurrentWeapon();

        // if we want to shoot, don't need to rechamber, and the delay is up
        if (!currentWeapon.rechamberNeeded && mouseIn.leftMousePressed
                && currentWeapon.fireDelayCounter >= currentWeapon.fireDelay) {

            loadout.fireWeapon();
            if (currentWeapon.fireType == FireType.SEMI_AUTO) {
                // rechamber needed, prevent full auto on semi auto guns
                currentWeapon.rechamberNeeded = true;
            }
            currentWeapon.fireDelayCounter = 0;
        } // if semi auto, rechamber on trigger release
        else if (currentWeapon.fireType == FireType.SEMI_AUTO && !mouseIn.leftMousePressed) {
            currentWeapon.rechamberNeeded = false;
        }

        // inc fire delay
        if (currentWeapon.fireDelayCounter < currentWeapon.fireDelay) {

            currentWeapon.fireDelayCounter += this.loadout.getFireRateMultiplier();

        }

        // cycle weapons. set to false so we dont constantly switch
        // find better sol?
        if (keyIn.switchWpnPressed) {
            loadout.switchWeapon();
            keyIn.switchWpnPressed = false;
        }

        // health regen
        if (loadout.health < loadout.defaultHealth) {
            if (!loadout.healing) {

                loadout.heal();
            }
        }


        int objIndex = ap.gameManager.cc.checkObject(this, true);
        if (keyIn.fPressed) {
            pickUpObject(objIndex);
        }

        if (keyIn.reloadPressed) {
            currentWeapon.handleReload();
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
            ap.gameManager.cc.checkTile(this);
            // for non interact pickups (powerups)
            pickUpObject(objIndex);

            // zombie collision
            int zombieIndex = ap.gameManager.cc.checkEntity(this, ap.gameManager.roundManager.getZombies());

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
            for (Zone zone : ap.gameManager.zoneManager.zones) {
                for (Rectangle zoneRect : zone.zoneRects) {
                    if (Zone.isPlayerInZone(zoneRect, this)) {
                        ap.gameManager.zoneManager.currentZone = zone;
                    }
                }
            }
            System.out.println("X: " + this.worldX / ap.tileSize + ", Y: " + this.worldY / ap.tileSize);
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

    public void reset() {
        setDefaultValues();
    }

    /**
     * @param index position i
     */
    public void damageZombie(boolean collisionIsHeadshot, int index) {
        if (index != 999) {
            Entity zombie = ap.gameManager.roundManager.getZombies()[index];

            boolean killed = false;

            int damage = (int) (loadout.getCurrentWeapon().getDamage() * loadout.getDamageMultiplier());
            if (collisionIsHeadshot) {
                damage *= 2.0f; // TODO make this value gun dependent?
            }
            zombie.takeDamage(collisionIsHeadshot, damage);
            if (zombie.health <= 0) {
                zombie.die(collisionIsHeadshot, index);
                statistics.addKill(collisionIsHeadshot);
                killed = true;
            }
            loadout.addPoints(killed, collisionIsHeadshot);
        }
    }

    public void pickUpObject(int index) {
        if (index != 999) {
            SuperObject obj = ap.gameManager.obj[index];
            switch (obj.type) {
                case "perk" -> {
                    PerkMachine perkMachine = (PerkMachine) obj;
                    boolean alreadyHasPerk = loadout.alreadyHasPerk(perkMachine);
                    boolean canAffordPerk = loadout.canAffordPerk(perkMachine);
                    if (alreadyHasPerk) {
                        this.purchaseString = null;
                        break;
                    }
                    drawPurchaseText(perkMachine.name, perkMachine.price);

                    if (canAffordPerk && keyIn.fPressed) {
                        ap.playSE(InteractSound.PURCHASE.ordinal(), SoundType.INTERACT);
                        loadout.purchasePerk(perkMachine);
                    }
                }
                case "gun" -> {
                    WallBuy wallBuy = (WallBuy) obj;
                    // if the gun is purchased, we're buying ammo for it
                    boolean buyingAmmo = loadout.isGunPurchased(wallBuy);
                    if (!buyingAmmo) {
                        if (keyIn.fPressed && loadout.isGunPurchasable(wallBuy)) {

                            ap.playSE(InteractSound.PURCHASE.ordinal(), SoundType.INTERACT);
                            loadout.purchaseGun(wallBuy);
                        }

                        drawPurchaseText(wallBuy.name, wallBuy.price);
                    } else {
                        int ammoCost = wallBuy.price / 2;
                        if (keyIn.fPressed && loadout.isAmmoPurchasable(ammoCost)) {
                            ap.playSE(InteractSound.PURCHASE.ordinal(), SoundType.INTERACT);
                            loadout.purchaseAmmo(wallBuy, ammoCost);
                        }
                        drawPurchaseText("ammo", ammoCost);
                    }
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
        BufferedImage image = drawDirection();
        g2.drawImage(image, screenX, screenY, ap.tileSize, ap.tileSize, null);

        // Display weapon image, flips based on mouse
        int angle = calculateAngle();
        // omg kms
        BufferedImage weaponImage = MediaManager.createFlipped(getImage(mousePosition));
        boolean mouseOnRightSide = mousePosition.x > ap.screenWidth / 2;
        if (mouseOnRightSide) {
            angle -= 180;
        }

        // decide which player side to display the weapon (left if left, right if right)
        int offset;
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
     * (left facing barrel) mousePosition < middle = image2 (right facing
     * barrel)
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

    public void setPerkOffset(int perkOffset) {
        this.perkOffset = perkOffset;
    }
}
