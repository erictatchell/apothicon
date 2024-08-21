package etat.apothicon.object.weapon.gun;

import etat.apothicon.entity.Player;
import etat.apothicon.object.weapon.wallbuy.WallBuy;
import etat.apothicon.utility.sound.GunSound;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import java.awt.image.BufferedImage;

public class GunBuilder {

    private String name;
    private String upgradedName;
    private BufferedImage image;
    private String upgradedImagePath;
    private Player owner;
    private GunSound fireSound;
    private GunSound upgradedFireSound;
    private GunSound reloadSound;
    private int damage;
    private int upgradedDamage;
    private int defaultAmmoPerMagazine;
    private int upgradedDefaultAmmoPerMagazine;
    private int reserve;
    private int upgradedDefaultReserve;
    private float fireRate;
    private FireType fireType;
    private float reloadRate;
    private String imagePath;
    private int range;

    public GunBuilder name(String name) {
        this.name = name;
        return this;
    }

    public GunBuilder upgradedName(String upgradedName) {
        this.upgradedName = upgradedName;
        return this;
    }

    public GunBuilder image(BufferedImage image) {
        this.image = image;
        return this;
    }

    public GunBuilder upgradedImagePath(String upgradedImagePath) {
        this.upgradedImagePath = upgradedImagePath;
        return this;
    }

    public GunBuilder owner(Player owner) {
        this.owner = owner;
        return this;
    }

    public GunBuilder fireSound(GunSound fireSound) {
        this.fireSound = fireSound;
        return this;
    }

    public GunBuilder upgradedFireSound(GunSound upgradedFireSound) {
        this.upgradedFireSound = upgradedFireSound;
        return this;
    }

    public GunBuilder reloadSound(GunSound reloadSound) {
        this.reloadSound = reloadSound;
        return this;
    }

    public GunBuilder damage(int damage) {
        this.damage = damage;
        return this;
    }

    public GunBuilder upgradedDamage(int upgradedDamage) {
        this.upgradedDamage = upgradedDamage;
        return this;
    }

    public GunBuilder defaultAmmoPerMagazine(int defaultAmmoPerMagazine) {
        this.defaultAmmoPerMagazine = defaultAmmoPerMagazine;
        return this;
    }

    public GunBuilder upgradedDefaultAmmoPerMagazine(int upgradedDefaultAmmoPerMagazine) {
        this.upgradedDefaultAmmoPerMagazine = upgradedDefaultAmmoPerMagazine;
        return this;
    }

    public GunBuilder reserve(int reserve) {
        this.reserve = reserve;
        return this;
    }

    public GunBuilder upgradedDefaultReserve(int upgradedDefaultReserve) {
        this.upgradedDefaultReserve = upgradedDefaultReserve;
        return this;
    }

    public GunBuilder fireRate(float fireRate) {
        this.fireRate = fireRate;
        return this;
    }

    public GunBuilder fireType(FireType fireType) {
        this.fireType = fireType;
        return this;
    }

    public GunBuilder reloadRate(float reloadRate) {
        this.reloadRate = reloadRate;
        return this;
    }

    public GunBuilder range(int range) {
        this.range = range;
        return this;
    }

    public GunBuilder image(String imagePath) {
        try {
            this.image = ImageIO.read(new File(imagePath));
            return this;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Gun build() {
        return new Gun(
                owner,
                name,
                damage,
                defaultAmmoPerMagazine,
                reserve,
                fireRate,
                range,
                reloadRate,
                image,
                fireType,
                fireSound,
                reloadSound,
                upgradedName,
                upgradedImagePath,
                upgradedDamage,
                upgradedDefaultReserve,
                upgradedDefaultAmmoPerMagazine,
                upgradedFireSound
        );
    }
}
