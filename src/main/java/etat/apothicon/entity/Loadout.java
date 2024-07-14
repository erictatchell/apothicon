package etat.apothicon.entity;

import etat.apothicon.object.SuperObject;
import etat.apothicon.object.perk.bottle.Perk;
import etat.apothicon.object.perk.machine.PerkMachine;
import etat.apothicon.object.weapon.gun.Gun;
import etat.apothicon.object.weapon.gun.M1911_Gun;
import etat.apothicon.object.weapon.wallbuy.WallBuy;

public class Loadout {

    private Gun[] guns;
    private Perk[] perks;
    private int currentWeaponIdx;
    private int maxGunNum;
    private int perkLimit;
    private int revives;
    private int points;
    private float reloadRateMultiplier;
    private float fireRateMultiplier;
    private int defaultHealth;
    private int health;

    public Loadout() {
        init();
    }

    private void init() {
        this.perkLimit = 4;
        this.revives = 0;
        this.reloadRateMultiplier = 1.0f;
        this.fireRateMultiplier = 1.0f;
        this.defaultHealth = 150;
        this.health = 150;
        this.maxGunNum = 2;
        this.guns = new Gun[maxGunNum];
        this.perks = new Perk[perkLimit];
        M1911_Gun m1911 = new M1911_Gun();
        this.guns[0] = m1911;
        this.currentWeaponIdx = 0;

    }

    public boolean isAmmoPurchasable(int price) {
        Gun current = this.guns[currentWeaponIdx];
        return (this.points > price) && (current.reserve != current.defaultReserve);
    }

    public boolean isGunPurchasable(WallBuy gun) {
        int n = this.maxGunNum;
        int m = this.guns.length;
        for (int i = 0; i < m; i++) {
            if (this.guns[i].getName().equals(gun.name)) {
                return false;

            }
        }
        if (m >= n) {
            this.guns[currentWeaponIdx] = null;
            return true;
        }

        return this.points >= gun.price;
    }

    public boolean isPerkPurchasable(PerkMachine perk) {
        int n = this.perks.length;
        if (n >= 4) {
            return false;
        } // max perks

        String purchasingName = perk.name;
        for (int i = 0; i < n; i++) {
            if (purchasingName.equals(this.perks[i].getName())) {
                return false;
            }
        }
        return true;
    }

    public int fireWeapon(int shotCount) {
        // simulate time/fire rate
        // only guns with fire rate 1.0f and above will be impacted by this
        // all other guns are semi auto and can fire as fast as user can trigger pull
        shotCount++;
        if (shotCount > this.guns[currentWeaponIdx].shotCount) {
            this.guns[currentWeaponIdx].fire();
            shotCount = 0;

        }
        return shotCount;
    }

    public void rechamberWeapon(int shotCount) {
        shotCount = this.guns[currentWeaponIdx].shotCount;
        this.guns[currentWeaponIdx].rechamberNeeded = false;

    }

    public void purchaseAmmo(SuperObject object, int price) {
        Gun current = this.guns[currentWeaponIdx];
        // currently, user must be holding the weapon to buy ammo
        // change?
        if (current.getName() == object.name) {

            current.reserve = current.defaultReserve;
            current.magazine = current.defaultAmmoPerMagazine;
            this.points -= price;
        }
    }

    public boolean isGunPurchased(WallBuy obj) {
        for (Gun gun : this.guns) {
            if (obj.name.equals(gun.getName())) {

                return true;
            }
        }
        return false;
    }

    public void handleGunPurchase(Gun gun) {
        // mr 4 mint greens

        if (maxGunNum == 2) {
            this.guns[currentWeaponIdx] = gun;
        } else { // 3
            int index = this.guns[1] == null ? 2 : 1;
            this.guns[index] = gun;
        }
        this.points -= gun.getPrice();
        this.currentWeaponIdx = this.guns.length - 1;
    }

    public Perk[] getPerks() {
        return this.perks;
    }

    public void switchWeapon() {
        if ((currentWeaponIdx + 1) != guns.length) {
            this.currentWeaponIdx++;
        } else {
            this.currentWeaponIdx = 0;
        }
    }

    public Gun getCurrentWeapon() {
        return this.guns[currentWeaponIdx];
    }
    public int getPoints() {
        return this.points;
    }
    public void spendPoints(int p) {
        this.points -= p;
    }
    public int getHealth() {
        return this.health;
    }
    public int getMaxGunNum() {
        return this.maxGunNum; 
    }
    public float getReloadRate() {
        return this.reloadRateMultiplier;
    }
    public float getFireRateMultiplier() {
        return this.fireRateMultiplier;
    }
    public int getRevives() {
        return this.revives;
    }
    public void setHealth(int h) {
        this.health = h;
    }
    public void setDefaultHealth(int h) {
        this.defaultHealth = h;
    }
    public void setMaxGunNum(int m) {
        this.maxGunNum = m;
    }
    public void setRevives(int r) {
        this.revives = r;
    }
    public void setReloadRate(float rr) {
        this.reloadRateMultiplier = rr;
    }
    public void addPerk(Perk perk) {
        int index = 0;
        for (Perk p: this.perks) {
            index ++;       
        }
        this.perks[index + 1] = perk;
    }
}
