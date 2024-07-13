package etat.apothicon.entity;

enum SelectFire {
    AUTO,
    SEMI_AUTO,
    BURST
}

// TODO:
public class Gun extends Entity {
    String name;
    int damage;
    int defaultAmmoPerMagazine;
    int magazine;
    int reserve;
    int fireRate;
    SelectFire fireType;
    float range;
    boolean rechamberNeeded = false;

    public Gun(String name, int damage, int defaultAmmoPerMagazine, int reserve, int fireRate, SelectFire fireType,
            int range) {
        this.name = name;
        this.damage = damage;
        this.defaultAmmoPerMagazine = defaultAmmoPerMagazine;
        this.magazine = defaultAmmoPerMagazine;
        this.reserve = reserve;
        this.fireType = fireType;
        this.fireRate = fireRate;
        this.range = range;
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
            }
        }
    }

    public void removeAmmo() {

    }

    public void fire() {

        if (!rechamberNeeded) {
            if (magazine > 0) {
                magazine -= 1;
            } else
                reload();

        }
        switch (this.fireType) {
            case AUTO:
                break;
            case SEMI_AUTO:
                rechamberNeeded = true;
        }
    }
}
