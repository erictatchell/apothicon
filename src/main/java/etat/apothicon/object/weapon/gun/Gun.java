package etat.apothicon.object.weapon.gun;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;
import etat.apothicon.object.weapon.wallbuy.WallBuy;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

// TODO:
public class Gun {
    String name;
    public BufferedImage image;
    Player owner;

    // for when mousex goes over the middle, prevents upside down images
    public BufferedImage image2;

    int damage;
    public int bullet;
    public int defaultAmmoPerMagazine;
    public int magazine;
    public int reserve;
    public int defaultReserve;
    public WallBuy wallBuy;
    public float fireRate;
    float reloadRate;
    public FireType fireType;
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
            int range, float reloadRate, String imagePath) {
        this.owner = owner;
        this.name = name;
        this.damage = damage;
        this.defaultAmmoPerMagazine = defaultAmmoPerMagazine;
        this.magazine = defaultAmmoPerMagazine;
        this.reserve = reserve;
        this.defaultReserve = reserve;
        this.fireType = fireType;
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
    }

    public void fire() {

        if (this.magazine >= 1) {
            int dir = owner.calculateAngle();
            Bullet bullet = new Bullet(owner.ap);
            bullet.set(owner.worldX + 24, owner.worldY + 24, dir, true, owner, this);
            owner.ap.bullets.add(bullet);

            this.magazine -= 1;
        }
        if (magazine == 0) {

            reload();
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
