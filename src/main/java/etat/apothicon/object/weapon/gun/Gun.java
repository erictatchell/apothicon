package etat.apothicon.object.weapon.gun;

import etat.apothicon.object.weapon.wallbuy.WallBuy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



// TODO:
public class Gun {
    String name;
    public BufferedImage image;

    // for when mousex goes over the middle, prevents upside down images
    public BufferedImage image2;

    private int fireDelay = 0;
    int damage;
    public int bullet;
    public int defaultAmmoPerMagazine;
    public int magazine;
    public int reserve;
    public int defaultReserve;
    public WallBuy wallBuy;
    float fireRate;
    float reloadRate;
    public SelectFire fireType;
    float range;
    public boolean rechamberNeeded = false;
    public int shotCount;

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
    public Gun(String name, int damage, int defaultAmmoPerMagazine, int reserve, float fireRate, SelectFire fireType,
            int range, float reloadRate, String imagePath) {
        this.name = name;
        this.damage = damage;
        this.defaultAmmoPerMagazine = defaultAmmoPerMagazine;
        this.magazine = defaultAmmoPerMagazine;
        this.reserve = reserve;
        this.defaultReserve = reserve;
        this.fireType = fireType;
        this.fireRate = fireRate;
        this.shotCount = (int) ((int) 10 * fireRate);
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
        return magazine;
    }

    public int getReserve() {
        return reserve;
    }

    public String getName() {
        return name;
    }

    public void reload() {
        int ammoToBeReloaded = defaultAmmoPerMagazine - magazine;
        if (reserve > 0) {
            if (this.magazine != defaultAmmoPerMagazine && reserve > ammoToBeReloaded) {
                reserve -= ammoToBeReloaded;
                this.magazine = defaultAmmoPerMagazine;
            } else {
                this.magazine += reserve;
                reserve = 0;
            }
        }
    }

    public void removeAmmo() {

    }

    public void fire() {

        if (!rechamberNeeded) {
            if (magazine >= 1) {
                bullet = 1;
                fireDelay++;
                magazine -= 1;
            }
            if (magazine == 0) {

                reload();
            }

        }
        switch (this.fireType) {
            case AUTO:

                break;
            case SEMI_AUTO:
                rechamberNeeded = true;
                break;
        }

   }

    public void rechamber() {
        this.rechamberNeeded = false;
        this.bullet = 0; 
    }
    public int getPrice() {
        return this.wallBuy.price;
    }
}
