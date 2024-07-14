package etat.apothicon.entity;

import java.util.ArrayList;

import etat.apothicon.object.SuperObject;
import etat.apothicon.object.perk.bottle.Perk;
import etat.apothicon.object.perk.machine.PerkMachine;
import etat.apothicon.object.weapon.gun.Gun;
import etat.apothicon.object.weapon.gun.M1911_Gun;
import etat.apothicon.object.weapon.wallbuy.WallBuy;

public class Loadout {

    private ArrayList<Gun> guns;
    private ArrayList<Perk> perks;
    private int currentWeaponIdx;
    private int maxGunNum;
    private int numGuns;
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
        this.points = 20000;
        this.maxGunNum = 2;
        this.numGuns = 1; // starting pistol
        this.guns = new ArrayList<>();
        this.perks = new ArrayList<>();
        M1911_Gun m1911 = new M1911_Gun();
        this.guns.add(m1911);
        this.currentWeaponIdx = 0;

    }

    public boolean isAmmoPurchasable(int price) {
        Gun current = this.guns.get(currentWeaponIdx);
        return (this.points > price) && (current.reserve != current.defaultReserve);
    }

    public boolean isGunPurchasable(WallBuy gun) {
        int n = this.maxGunNum;
        for (int i = 0; i < this.numGuns; i++) {
            if (this.guns.get(i).getName().equals(gun.name)) {
                return false;
            }
        }
        if (this.numGuns >= n) {
            this.guns.remove(currentWeaponIdx);
            return true;
        }

        return this.points >= gun.price;
    }

    public boolean isPerkPurchasable(PerkMachine perk) {
        int n = this.perks.size();
        if (n >= 8) {
            return false;
        } // max perks

        String purchasingName = perk.name;
        for (int i = 0; i < n; i++) {
            if (purchasingName.equals(this.perks.get(i).getName())) {
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
        if (shotCount > this.guns.get(currentWeaponIdx).shotCount) {
            this.guns.get(currentWeaponIdx).fire();
            shotCount = 0;

        }
        return shotCount;
    }

    public int rechamberWeapon(int shotCount) {
        shotCount = this.guns.get(currentWeaponIdx).shotCount;
        this.guns.get(currentWeaponIdx).rechamberNeeded = false;
        return shotCount;
    }

    public void purchaseAmmo(SuperObject object, int price) {
        Gun current = this.guns.get(currentWeaponIdx);
        // currently, user must be holding the weapon to buy ammo
        // change?
        if (current.getName() == object.name) {

            current.reserve = current.defaultReserve;
            current.magazine = current.defaultAmmoPerMagazine;
            this.points -= price;
        }
    }

    public boolean isGunPurchased(WallBuy obj) {
        for (int i = 0; i < this.numGuns; i++) {
            if (obj.name.equals(this.guns.get(i).getName())) {

                return true;
            }
        }
        return false;
    }

    public void handleGunPurchase(Gun gun) {
        // mr 4 mint greens

        if (this.guns.size() >= maxGunNum) {
            this.guns.remove(currentWeaponIdx);
        }
        this.guns.add(gun);

        this.points -= gun.getPrice();
        this.numGuns++;
        this.currentWeaponIdx = this.guns.size() - 1;
    }

    public ArrayList<Perk> getPerks() {
        return this.perks;
    }

    public void switchWeapon() {
        if ((currentWeaponIdx + 1) != guns.size()) {
            this.currentWeaponIdx++;
        } else {
            this.currentWeaponIdx = 0;
        }
    }

    public Gun getCurrentWeapon() {
        return this.guns.get(currentWeaponIdx);
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
        this.perks.add(perk);
    }
}
