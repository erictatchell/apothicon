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
    private String name;
    private String upgradedName;
    private BufferedImage image;
    private String upgradedImagePath;
    private Player owner;
    private GunSound fireSound;
    private GunSound upgradedFireSound;
    private GunSound reloadSound;
    private int upgradeTier;
    private int damage;
    private int upgradedDamage;
    private int delay;
    private int bullet;
    private int defaultAmmoPerMagazine;
    private int upgradedDefaultAmmoPerMagazine;
    private int magazine;
    private int upgradedDefaultReserve;
    private int reserve;
    private int defaultReserve;
    private int penetration;
    private WallBuy wallBuy;
    private float fireRate;
    private FireType fireType;
    public float fireDelayCounter;
    private float reloadRate;
    private Timer reloadTimer;
    private float range;
    private boolean rechamberNeeded = false;
    private boolean reloading = false;
    private float fireDelay;

    private int fireSoundIdx;

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
    protected Gun(Player owner,
        String name,
        int damage,
        int defaultAmmoPerMagazine,
        int reserve,
        float fireRate,
        int range,
        float reloadRate,
        BufferedImage image,
        FireType fireType,
        GunSound fireSound,
        GunSound reloadSound,
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
        this.fireSoundIdx = fireSound.ordinal();
        this.reloadSound = reloadSound;
        this.fireRate = fireRate;
        this.reloadRate = reloadRate;
        this.range = range;
        this.image = image;
        this.penetration = 2;
        this.fireDelay = 10 * fireRate;

        this.upgradedDamage = upgradedDamage;
        this.upgradedFireSound = upgradedFireSound;
        this.upgradedName = upgradedName;
        this.upgradedDefaultAmmoPerMagazine = upgradedDefaultAmmoPerMagazine;
        this.upgradedDefaultReserve = upgradedDefaultReserve;
        this.upgradedImagePath = upgradedImagePath;

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

    // kms
    private int getNextFireSound() {
        // if we've reached the final fire sound for the gun
        int temp = fireSoundIdx;
        if (fireSound.ordinal() + 5 == fireSoundIdx) {
            fireSoundIdx = fireSound.ordinal();
            return temp;
        }
        fireSoundIdx++;
        return temp;
    }

    public void fire() {

        if (!this.reloading && this.magazine >= 1) {

            int sound = getNextFireSound();
            owner.ap.playSE(sound, SoundType.GUN);
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

    public void setName(String name) {
        this.name = name;
    }

    public String getUpgradedName() {
        return upgradedName;
    }

    public void setUpgradedName(String upgradedName) {
        this.upgradedName = upgradedName;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getUpgradedImagePath() {
        return upgradedImagePath;
    }

    public void setUpgradedImagePath(String upgradedImagePath) {
        this.upgradedImagePath = upgradedImagePath;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public GunSound getFireSound() {
        return fireSound;
    }

    public void setFireSound(GunSound fireSound) {
        this.fireSound = fireSound;
    }

    public GunSound getUpgradedFireSound() {
        return upgradedFireSound;
    }

    public void setUpgradedFireSound(GunSound upgradedFireSound) {
        this.upgradedFireSound = upgradedFireSound;
    }

    public GunSound getReloadSound() {
        return reloadSound;
    }

    public void setReloadSound(GunSound reloadSound) {
        this.reloadSound = reloadSound;
    }

    public int getUpgradeTier() {
        return upgradeTier;
    }

    public void setUpgradeTier(int upgradeTier) {
        this.upgradeTier = upgradeTier;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getUpgradedDamage() {
        return upgradedDamage;
    }

    public void setUpgradedDamage(int upgradedDamage) {
        this.upgradedDamage = upgradedDamage;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getBullet() {
        return bullet;
    }

    public void setBullet(int bullet) {
        this.bullet = bullet;
    }

    public int getDefaultAmmoPerMagazine() {
        return defaultAmmoPerMagazine;
    }

    public void setDefaultAmmoPerMagazine(int defaultAmmoPerMagazine) {
        this.defaultAmmoPerMagazine = defaultAmmoPerMagazine;
    }

    public int getUpgradedDefaultAmmoPerMagazine() {
        return upgradedDefaultAmmoPerMagazine;
    }

    public void setUpgradedDefaultAmmoPerMagazine(int upgradedDefaultAmmoPerMagazine) {
        this.upgradedDefaultAmmoPerMagazine = upgradedDefaultAmmoPerMagazine;
    }

    public void setMagazine(int magazine) {
        this.magazine = magazine;
    }

    public int getUpgradedDefaultReserve() {
        return upgradedDefaultReserve;
    }

    public void setUpgradedDefaultReserve(int upgradedDefaultReserve) {
        this.upgradedDefaultReserve = upgradedDefaultReserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    public int getDefaultReserve() {
        return defaultReserve;
    }

    public void setDefaultReserve(int defaultReserve) {
        this.defaultReserve = defaultReserve;
    }

    public int getPenetration() {
        return penetration;
    }

    public void setPenetration(int penetration) {
        this.penetration = penetration;
    }

    public WallBuy getWallBuy() {
        return wallBuy;
    }

    public float getFireRate() {
        return fireRate;
    }

    public void setFireRate(float fireRate) {
        this.fireRate = fireRate;
    }

    public FireType getFireType() {
        return fireType;
    }

    public void setFireType(FireType fireType) {
        this.fireType = fireType;
    }

    public float getFireDelayCounter() {
        return fireDelayCounter;
    }

    public void setFireDelayCounter(float fireDelayCounter) {
        this.fireDelayCounter = fireDelayCounter;
    }

    public float getReloadRate() {
        return reloadRate;
    }

    public void setReloadRate(float reloadRate) {
        this.reloadRate = reloadRate;
    }

    public Timer getReloadTimer() {
        return reloadTimer;
    }

    public void setReloadTimer(Timer reloadTimer) {
        this.reloadTimer = reloadTimer;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public boolean isRechamberNeeded() {
        return rechamberNeeded;
    }

    public void setRechamberNeeded(boolean rechamberNeeded) {
        this.rechamberNeeded = rechamberNeeded;
    }

    public boolean isReloading() {
        return reloading;
    }

    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    public float getFireDelay() {
        return fireDelay;
    }

    public void setFireDelay(float fireDelay) {
        this.fireDelay = fireDelay;
    }

    public int getPrice() {
        return wallBuy.price;
    }
}
