package etat.apothicon.entity;

import etat.apothicon.main.*;
import etat.apothicon.object.drop.Drop;
import etat.apothicon.object.InfernalMachine;
import etat.apothicon.object.weapon.gun.Gun;
import etat.apothicon.round.Zone;
import etat.apothicon.object.SuperObject;
import etat.apothicon.object.perk.bottle.Perk;
import etat.apothicon.object.perk.machine.PerkMachine;
import etat.apothicon.object.weapon.gun.FireType;
import etat.apothicon.object.weapon.wallbuy.WallBuy;
import etat.apothicon.utility.sound.InteractSound;
import etat.apothicon.utility.sound.SoundType;
import etat.apothicon.utility.MediaManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;

public class Player extends Entity {

    private KeyInput keyIn;
    private MouseInput mouseIn;
    private Loadout loadout;
    private String purchaseString = null;
    private Statistics statistics;
    private int perkOffset = 16;
    private int slotX = 16;
    private boolean dead = false;
    private boolean inLastStand = false;

    public final int screenX;
    public final int screenY;
    public final Apothicon ap;

    public Player(Apothicon ap, KeyInput keyIn, MouseInput mouseIn) {
        super(ap);
        this.ap = ap;
        this.keyIn = keyIn;
        this.mouseIn = mouseIn;
        this.loadout = new Loadout(this, this.ap);
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
        if (!inLastStand && loadout.health <= 0 && loadout.hasPerk("quick_revive")) {
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
        if (!currentWeapon.isRechamberNeeded() && mouseIn.leftMousePressed
                && currentWeapon.getFireDelayCounter() >= currentWeapon.getFireDelay()) {

            loadout.fireWeapon();
            if (currentWeapon.getFireType() == FireType.SEMI_AUTO) {
                // rechamber needed, prevent full auto on semi auto guns
                currentWeapon.setRechamberNeeded(true);
            }
            currentWeapon.setFireDelayCounter(0);
        } // if semi auto, rechamber on trigger release
        else if (currentWeapon.getFireType() == FireType.SEMI_AUTO && !mouseIn.leftMousePressed) {
            currentWeapon.setRechamberNeeded(false);
        }

        // inc fire delay
        if (currentWeapon.getFireDelayCounter() < currentWeapon.getFireDelay()) {

            // todo
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
        // for non interact pickups (powerups)
        pickUpObject(objIndex);

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
//            System.out.println("X: " + this.worldX / ap.tileSize + ", Y: " + this.worldY / ap.tileSize);
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
            // instakill
            if (loadout.getDamageMultiplier() == -1.0f) {
                zombie.die(collisionIsHeadshot, index);
                statistics.addKill(collisionIsHeadshot);
                killed = true;
            }

            int damage = (int) (loadout.getCurrentWeapon().getDamage() * loadout.getDamageMultiplier());
            if (collisionIsHeadshot) {
                damage *= 1.5f; // TODO make this value gun dependent?
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
                case "drop" -> {
                    Drop drop = (Drop) obj;
                    drop.objIndex = index;
                    if (drop.spawned) {
                        ap.gameManager.dropManager.handleActivate(drop);
                    }
                }
                case "pap" -> {
                    InfernalMachine machine = (InfernalMachine) obj;
                    int price = machine.getPrice(loadout.getCurrentWeapon());
                    if (loadout.getCurrentWeapon().getUpgradeTier() != 3) {
                        drawPurchaseText("Upgrade Tier " + machine.getNextTier(loadout.getCurrentWeapon()),
                                machine.getPrice(loadout.getCurrentWeapon()));
                    }
                    if (keyIn.fPressed && loadout.canAfford(price)) {
                        ap.playSE(InteractSound.PURCHASE.ordinal(), SoundType.INTERACT);
                        loadout.purchaseUpgrade(machine, price);
                        keyIn.fPressed = false;
                    }
                }
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
        int angle = MediaManager.calculateAngle(ap);
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
        g2.drawImage(MediaManager.rotateImageByDegrees(weaponImage, angle), screenX + offset, screenY,

                ap.tileSize, ap.tileSize, null);

        MediaManager.antialias(g2);
        ap.gameManager.hud.drawPurchaseText(g2, this.purchaseString);

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
            return loadout.getCurrentWeapon().getImage();
        }
        return MediaManager.horizontalFlip(loadout.getCurrentWeapon().getImage());
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

    public Loadout getLoadout() {
        return loadout;
    }

    public int getPerkOffset() {
        return perkOffset;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isInLastStand() {
        return inLastStand;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setInLastStand(boolean inLastStand) {
        this.inLastStand = inLastStand;
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
