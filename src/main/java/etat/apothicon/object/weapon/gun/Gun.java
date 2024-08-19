package etat.apothicon.object.weapon.gun;

import etat.apothicon.entity.Player;
import etat.apothicon.object.weapon.wallbuy.WallBuy;
import etat.apothicon.utility.MediaManager;
import etat.apothicon.utility.sound.GunSound;
import etat.apothicon.utility.sound.SoundType;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import javax.imageio.ImageIO;

// TODO:
public class Gun {

    String name;
    String upgradedName;
    public BufferedImage image;
    public String upgradedImagePath;
    Player owner;
    public GunSound fireSound;
    public GunSound upgradedFireSound;
    public GunSound reloadSound;
    public int upgradeTier;

    int damage;
    public int upgradedDamage;
    boolean reloading = false;
    int delay;
    public int bullet;
    public int defaultAmmoPerMagazine;
    public int upgradedDefaultAmmoPerMagazine;
    public int magazine;
    public int upgradedDefaultReserve;
    public int reserve;
    public int defaultReserve;
    public int penetration;
    public WallBuy wallBuy;
    public float fireRate;
    public FireType fireType;
    public float fireDelayCounter;
    public float reloadRate;
    public Timer reloadTimer;
    float range;
    public boolean rechamberNeeded = false;
    public float fireDelay;

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
    public Gun(Player owner,
               String name,
               int damage,
               int defaultAmmoPerMagazine,
               int reserve,
               float fireRate,
               int range,
               float reloadRate,

               String imagePath,
               FireType fireType,
               GunSound fireSound,
               GunSound reloadSound,

               // when upgraded
               String upgradedName,
               String upgradedImagePath,
               int upgradedDamage,
               int upgradedDefaultReserve,
               int upgradedDefaultAmmoPerMagazine,
               GunSound upgradedFireSound
    ) {
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
        this.reloadRate = reloadRate;
        this.range = range;
        this.penetration = 2;
        this.fireDelay = 10 * fireRate;

        this.upgradedDamage = upgradedDamage;
        this.upgradedFireSound = upgradedFireSound;
        this.upgradedName = upgradedName;
        this.upgradedDefaultAmmoPerMagazine = upgradedDefaultAmmoPerMagazine;
        this.upgradedDefaultReserve = upgradedDefaultReserve;
        this.upgradedImagePath = upgradedImagePath;

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

        int ammoToBeReloaded = this.defaultAmmoPerMagazine - this.magazine;
        if (ammoToBeReloaded > 0 && !reloading && reserve > 0) {
            this.reloading = true;
            this.reloadTimer = new Timer();
            this.delay = (int) (1000 * this.owner.getLoadout().getReloadRate() * this.reloadRate);

            owner.ap.playSE(reloadSound.ordinal(), SoundType.GUN);
            reloadTimer.schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            reload(ammoToBeReloaded);
                        }
                    },
                    this.delay);
        }
    }

    public void reload(int ammoToBeReloaded) {
        if (this.magazine == this.defaultAmmoPerMagazine) {
            this.reloading = false;
            return;
        }
        if (this.reserve > ammoToBeReloaded) {
            this.reserve -= ammoToBeReloaded;
            this.magazine += ammoToBeReloaded;
        } else {
            this.magazine += this.reserve;
            this.reserve = 0;
        }
        this.reloading = false;
    }

    public void sendBullet() {

        int dir = MediaManager.calculateAngle(owner.ap);
        Bullet bullet1 = new Bullet(owner.ap, this);
        bullet1.set(owner.worldX + 24, owner.worldY + this.owner.ap.tileSize / 2, dir, true, owner);

        owner.ap.gameManager.bullets.add(bullet1);
        owner.getStatistics().addShotFired();

        if (this.owner.getLoadout().hasPerk("double_tap")) {

            Bullet bullet2 = new Bullet(owner.ap, this);
            bullet2.set(owner.worldX + 24, owner.worldY + 24, dir, true, owner);

            owner.ap.gameManager.bullets.add(bullet2);
        }
    }

    public void fire() {

        if (!this.reloading && this.magazine >= 1) {

            owner.ap.playSE(this.fireSound.ordinal(), SoundType.GUN);
            sendBullet();

            if (!owner.getLoadout().isBottomlessClip()) {
                this.magazine -= 1;
            }

        }
        if (magazine == 0) {

            handleReload();

        }
    }

    public void upgrade() {
        this.name = upgradedName;
        this.damage = upgradeTier == 0 ? upgradedDamage : upgradeTier * upgradedDamage;
        this.defaultAmmoPerMagazine = upgradedDefaultAmmoPerMagazine;
        this.magazine = upgradedDefaultAmmoPerMagazine;
        this.defaultReserve = upgradedDefaultReserve;
        this.reserve = upgradedDefaultReserve;
        this.fireSound = upgradedFireSound;
        try {
            this.image = ImageIO.read(new File(upgradedImagePath));
        } catch (IOException e) {
            e.printStackTrace();
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
