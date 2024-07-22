package etat.apothicon.object.weapon.gun;

import etat.apothicon.entity.Player;
import etat.apothicon.object.weapon.wallbuy.WallBuy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import javax.imageio.ImageIO;

// TODO:
public class Gun {

    String name;
    public BufferedImage image;
    Player owner;

    // for when mousex goes over the middle, prevents upside down images
    public BufferedImage image2;

    public GunSound fireSound;
    public GunSound reloadSound;

    int damage;
    boolean reloading = false;
    int delay;
    public int bullet;
    public int defaultAmmoPerMagazine;
    public int magazine;
    public int reserve;
    public int defaultReserve;
    public WallBuy wallBuy;
    public float fireRate;
    public FireType fireType;
    public int fireDelayCounter;
    public float reloadRate;
    public Timer reloadTimer;
    float range;
    public boolean rechamberNeeded = false;
    public int fireDelay;

    /**
     * gun structure
     *
     * @param name                   name
     * @param damage
     * @param defaultAmmoPerMagazine
     * @param reserve
     * @param fireRate
     * @param fireType
     * @param range
     * @param reloadRate
     * @param imagePath
     */
    public Gun(Player owner, String name, int damage, int defaultAmmoPerMagazine, int reserve, float fireRate,
            FireType fireType,
            GunSound fireSound,
            GunSound reloadSound,
            int range, float reloadRate, String imagePath) {
        this.owner = owner;
        this.name = name;
        this.damage = damage;
        this.defaultAmmoPerMagazine = defaultAmmoPerMagazine;
        this.magazine = defaultAmmoPerMagazine;
        this.reserve = reserve;
        this.defaultReserve = reserve;
        this.fireType = fireType;
        this.fireSound = fireSound;
        this.reloadSound = reloadSound;
        this.fireRate = fireRate;
        this.fireDelay = (int) ((int) 10 * fireRate);
        this.reloadRate = reloadRate;
        this.range = range;

        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWallBuy(WallBuy w) {
        this.wallBuy = w;
    }

    public int getMagazine() {
        return this.magazine;
    }

    public int getReserve() {
        return this.reserve;
    }

    public String getName() {
        return name;
    }

    public void handleReload() {
        if (!reloading && reserve > 0) {
            this.reloading = true;
            owner.ap.playSE(this.reloadSound.ordinal());
            this.reloadTimer = new Timer();
            this.delay = (int) (1000 * this.owner.loadout.getReloadRate() * this.reloadRate);
            reloadTimer.schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            reload();
                        }
                    },
                    this.delay);

        }
    }

    public void reload() {
        int ammoToBeReloaded = this.defaultAmmoPerMagazine - this.magazine;
        if (this.reserve > 0 && ammoToBeReloaded > 0) {
            if (this.reserve > ammoToBeReloaded) {
                this.reserve -= ammoToBeReloaded;
                this.magazine += ammoToBeReloaded;
            } else {
                this.magazine += this.reserve;
                this.reserve = 0;
            }
        }
        this.reloading = false;
    }

    public void sendBullet() {

        int dir = owner.calculateAngle();
        int dirDistanceX = 0;
        int dirDistanceY = 0;
        boolean upperRight = (dir <= 0 && dir >= -90);
        boolean upperLeft = (dir <= -90 && dir >= -180);
        boolean lowerRight = (dir >= 0 && dir <= 90);
        boolean lowerLeft = (dir >= 90 && dir <= 180);
        if (upperRight || lowerRight) {
            dirDistanceX = this.owner.ap.tileSize;
        }
        Bullet bullet1 = new Bullet(owner.ap);
        bullet1.set(owner.worldX + dirDistanceX, owner.worldY + this.owner.ap.tileSize / 2, dir, true, owner, this);

        owner.ap.gameState.bullets.add(bullet1);

        if (this.owner.loadout.hasDoubleTap) {

            Bullet bullet2 = new Bullet(owner.ap);
            bullet2.set(owner.worldX + 28, owner.worldY + 28, dir, true, owner, this);

            owner.ap.gameState.bullets.add(bullet2);
        }
    }

    public void fire() {

        if (!this.reloading && this.magazine >= 1) {

            owner.ap.playSE(this.fireSound.ordinal());
            sendBullet();

            this.magazine -= 1;

        }
        if (magazine == 0) {

            handleReload();
        }
    }

    public void rechamber() {
        this.rechamberNeeded = false;
    }

    public int getPrice() {
        return this.wallBuy.price;
    }

    public int getDamage() {
        return this.damage;
    }

}
