package etat.apothicon.entity;

import etat.apothicon.main.Apothicon;
import etat.apothicon.object.InfernalMachine;
import etat.apothicon.object.SuperObject;
import etat.apothicon.object.perk.bottle.DoubleTap;
import etat.apothicon.object.perk.bottle.Juggernog;
import etat.apothicon.object.perk.bottle.MuleKick;
import etat.apothicon.object.perk.bottle.Perk;
import etat.apothicon.object.perk.bottle.QuickRevive;
import etat.apothicon.object.perk.bottle.SpeedCola;
import etat.apothicon.object.perk.machine.PerkMachine;
import etat.apothicon.object.weapon.gun.Gun;
import etat.apothicon.object.weapon.gun.M14_Gun;
import etat.apothicon.object.weapon.gun.M1911_Gun;
import etat.apothicon.object.weapon.gun.MP40_Gun;
import etat.apothicon.object.weapon.gun.Olympia_Gun;
import etat.apothicon.object.weapon.gun.Stakeout_Gun;
import etat.apothicon.object.weapon.wallbuy.WallBuy;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Loadout {

    private final Player player;
    private final Apothicon ap;
    private ArrayList<Gun> guns;
    private ArrayList<Perk> perks;
    private boolean bottomlessClip;
    private int currentWeaponIdx;
    private int maxGunNum;
    private int perkLimit;
    private int revives;
    private int points;

    private float reloadRateMultiplier;
    private float damageMultiplier;
    private float fireRateMultiplier;
    private float pointsMultiplier;
    private float fireDelay;

    protected Timer loadoutTimer;
    protected int defaultHealth;
    protected int health;
    protected boolean healing;

    private boolean doubleTap;
    private boolean speedCola;
    private boolean juggernog;
    private boolean quickRevive;
    private boolean muleKick;




    public Loadout(Player player, Apothicon ap) {
        this.player = player;
        this.ap = ap;

        init();
    }

    public void getDebugLoadout() {
        this.perkLimit = 4;
        this.revives = 0;
        this.reloadRateMultiplier = 1.0f;
        this.damageMultiplier = 1.0f;
        this.fireRateMultiplier = 1.0f;
        this.pointsMultiplier = 1.0f;
        this.defaultHealth = 150000;
        this.health = 150000;
        this.points = 50000;
        this.maxGunNum = 2;
        this.guns = new ArrayList<>();
        this.perks = new ArrayList<>();
        MP40_Gun mp40 = new MP40_Gun(this.player);
        this.guns.add(mp40);
        this.currentWeaponIdx = 0;

        bottomlessClip = true;

    }

    private void init() {
        this.perkLimit = 4;
        this.revives = 0;
        this.reloadRateMultiplier = 1.0f;
        this.damageMultiplier = 1.0f;
        this.fireRateMultiplier = 1.0f;
        this.pointsMultiplier = 1.0f;
        this.defaultHealth = 150;
        this.health = 150;
        this.points = 50000;
        this.maxGunNum = 2;
        this.guns = new ArrayList<>();
        this.perks = new ArrayList<>();
        M1911_Gun m1911 = new M1911_Gun(this.player);
        this.guns.add(m1911);
        this.currentWeaponIdx = 0;

        bottomlessClip = false;
    }

    public void reset() {
        init();
    }

    public boolean isAmmoPurchasable(int price) {
        Gun current = this.guns.get(currentWeaponIdx);
        return (this.points > price) && (current.reserve != current.defaultReserve);
    }

    public boolean isGunPurchasable(WallBuy gun) {
        boolean affordable = this.points >= gun.price;
        int max = this.maxGunNum;
        for (Gun ownedGun : this.guns) {
            if (ownedGun.getName().equals(gun.name)) {
                return false;
            }
        }
        if (this.guns.size() >= max && affordable) {
            this.guns.remove(currentWeaponIdx);
            return true;
        }

        return affordable;
    }

    public boolean alreadyHasPerk(PerkMachine perk) {
        int n = this.perks.size();
        if (n >= 8) {
            return true;
        }

        String purchasingName = perk.name;
        for (Perk ownedPerk : this.perks) {
            if (purchasingName.equals(ownedPerk.getName())) {
                return true;
            }
        }
        return false;
    }
    public boolean canAfford(int price) {
        return points >= price;
    }
    public boolean canAffordPerk(PerkMachine perk) {
        return points >= perk.price;
    }

    public void refillAmmo() {
        for (Gun gun : this.guns) {
            gun.magazine = gun.defaultAmmoPerMagazine;
            gun.reserve = gun.defaultReserve;
        }
    }


    public void addPoints(boolean kill, boolean headshot) {
        int total = 0;
        int hitPoints = (int) (10 * pointsMultiplier);
        this.points += hitPoints;
        total+= hitPoints;
        if (kill) {
            int killPoints = (int) (50 * pointsMultiplier);
            this.points += killPoints;
            total+= killPoints;
            if (headshot) {
                this.points += killPoints;
                total+= killPoints;
            }
        }
        player.getStatistics().addPoints(total);
    }

    public void fireWeapon() {
        this.guns.get(currentWeaponIdx).fire();
    }

    public void purchaseAmmo(SuperObject object, int price) {
        for (Gun gun : this.guns) {
            if (gun.getName().equals(object.name)) {
                gun.reserve = gun.defaultReserve;
                gun.magazine = gun.defaultAmmoPerMagazine;
                spendPoints(price);
            }
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
        if (this.guns.size() >= maxGunNum) {
            this.guns.remove(currentWeaponIdx);
        }
        this.guns.add(gun);

        spendPoints(gun.getPrice());
        this.currentWeaponIdx = this.guns.size() - 1; // swap to the new gun
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
        player.getStatistics().addSpentPoints(p);
        this.points -= p;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxGunNum() {
        return this.maxGunNum;
    }

    public float getDamageMultiplier() {
        return this.damageMultiplier;
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

    public void setFireRateMultiplier(float fr) {
        this.fireRateMultiplier = fr;
    }

    public void setDamageMultiplier(float dmg) {
        this.damageMultiplier = dmg;
    }

    public void setPointsMultiplier(float m) {
        this.pointsMultiplier = m;
    }

    /**
     * perk machine purchases
     *
     * @param object interactable object (perk machine)
     */
    public void purchasePerk(SuperObject object) {
        switch (object.name) {
            case "juggernog":
                Juggernog jug = new Juggernog(player, ap);
                jug.activateFor(player);
                player.drawPurchaseText(object.name, 2500);
                break;
            case "double_tap":
                DoubleTap dt = new DoubleTap(player, ap);
                dt.activateFor(player);
                player.drawPurchaseText(object.name, 2000);
                break;
            case "speed_cola":
                SpeedCola sc = new SpeedCola(player, ap);
                sc.activateFor(player);
                player.drawPurchaseText(object.name, 3000);
                break;
            case "quick_revive":
                QuickRevive qr = new QuickRevive(player, ap);
                qr.activateFor(player);
                player.drawPurchaseText(object.name, 500);
                break;
            case "mule_kick":
                MuleKick mk = new MuleKick(player, ap);
                mk.activateFor(player);
                player.drawPurchaseText(object.name, 4000);
                break;
        }
    }

    // Sets relevant default values for loadout
    public void handleRevive() {
        revives = 0;
        player.setSlotX(16);
        player.setPerkOffset(16);
        perks = new ArrayList<>();
        maxGunNum = 2;

        // remove mule kick gun
        if (guns.size() > 2) {
            guns.remove(2);
        }
        defaultHealth = 150;
        health = 150;
        fireRateMultiplier = 1.0f;
        reloadRateMultiplier = 1.0f;

    }

    /**
     * wall weapon purchase
     *
     * @param object interactable object (wallbuy)
     */
    public void purchaseGun(SuperObject object) {
        switch (object.name) {
            case "MP40":
                MP40_Gun mp40 = new MP40_Gun(this.player);
                mp40.setWallBuy((WallBuy) object);
                handleGunPurchase(mp40);
                break;
            case "M14":
                M14_Gun m14 = new M14_Gun(this.player);
                m14.setWallBuy((WallBuy) object);
                handleGunPurchase(m14);
                break;
            case "Olympia":
                Olympia_Gun o = new Olympia_Gun(this.player);
                o.setWallBuy((WallBuy) object);
                handleGunPurchase(o);
                break;
            case "Stakeout":
                Stakeout_Gun st = new Stakeout_Gun(this.player);
                st.setWallBuy((WallBuy) object);
                handleGunPurchase(st);
                break;
        }
    }

    // kms
    // update 20 min later i made it super clean. i like living now
    public void heal() {
        healing = true;
        loadoutTimer = new Timer();
        loadoutTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (healing) {
                    health++;
                }
                if (health >= defaultHealth) {
                    healing = false;
                    loadoutTimer.cancel();
                }
            }
        }, 4000, 50);
    }

    public void purchaseUpgrade(InfernalMachine machine, int price) {
        spendPoints(price);
        machine.upgrade(getCurrentWeapon());
    }

    public boolean isBottomlessClip() {
        return bottomlessClip;
    }

    public void setBottomlessClip(boolean bottomlessClip) {
        this.bottomlessClip = bottomlessClip;
    }
    public boolean hasPerk(String name) {
        for (Perk perk : perks) {
            if (perk.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasPerk(Perk queriedPerk) {
        for (Perk perk : perks) {
            if (perk.name.equals(queriedPerk.name)) {
                return true;
            }
        }
        return false;
    }
}
