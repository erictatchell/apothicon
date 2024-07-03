package etat.apothicon;

public class Gun {
    int damage;
    int defaultAmmoPerMagazine;
    int magazine;
    int reserve;
    int fireRate;
    float range;

    Gun(int damage, int defaultAmmoPerMagazine, int reserve, int fireRate, int range) {
        this.damage = damage;
        this.defaultAmmoPerMagazine = defaultAmmoPerMagazine;
        this.magazine = defaultAmmoPerMagazine;
        this.reserve = reserve;
        this.fireRate = fireRate;
        this.range = range;
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

    public void fire() {
        if (magazine > 0) {
            magazine -= 1;
        } else reload();
    }
}
