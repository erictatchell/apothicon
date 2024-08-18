package etat.apothicon.object;

import etat.apothicon.main.Apothicon;

import java.awt.*;
import java.util.ArrayList;
import java.util.TimerTask;

public class DropManager {
    Apothicon ap;
    public boolean instaKillActive = false;
    public boolean doublePointsActive = false;
    public boolean fireSaleActive = false;
    public boolean infiniteAmmoActive = false;
    public ArrayList<Drop> spawnedDrops;
    public int activeDrops;

    private float defaultDamageMultiplier;

    public DropManager(Apothicon ap) {
        this.ap = ap;
        this.spawnedDrops = new ArrayList<>();
    }


    // todo: move to utility class, make static
    public int getSlotX() {
        int temp = 0;
        for (int i = 0; i < activeDrops; i++) {
            temp += ap.tileSize;
        }

        System.out.println("Active Drops: " + activeDrops + "\nReturned X: " + temp);
        return temp;
    }

    private boolean isDropActive(DropType type) {
        switch (type) {
            case INSTA_KILL -> {
                if (instaKillActive) {
                    return true;
                }
                instaKillActive = true;
            }
            case DOUBLE_POINTS -> {
                if (doublePointsActive) {
                    return true;
                }
                doublePointsActive = true;
            }
            case FIRE_SALE -> {
                if (fireSaleActive) {
                    return true;
                }
                fireSaleActive = true;
            }
            case INFINITE_AMMO -> {
                if (infiniteAmmoActive) {
                    return true;
                }
                infiniteAmmoActive = true;
            }
        }
        return false;
    }

    public void handleActivate(Drop drop) {
        // todo: extra processing? maybe a sound?
        if (!isDropActive(drop.dropType)) {
            activeDrops++;
            drop.activate();
        }
    }

    public void handleDeactivate(Drop drop) {
        // todo: extra processing? maybe a sound?
        if (isDropActive(drop.dropType)) {
            activeDrops--;
            drop.deactivate();
        }
    }

    public void removeEffects(DropType type) {
        switch (type) {
            case INSTA_KILL -> {
                ap.gameManager.player.getLoadout().setDamageMultiplier(defaultDamageMultiplier);
                ap.gameManager.dropManager.instaKillActive = false;
            }
            case FIRE_SALE -> {
                // boxPrice goes to defaultPrice
                ap.gameManager.dropManager.fireSaleActive = false;
            }
            case INFINITE_AMMO -> {
                ap.gameManager.dropManager.infiniteAmmoActive = false;
            }
        }
    }

    public void setEffects(DropType type) {
        switch (type) {
            case INSTA_KILL -> {
                defaultDamageMultiplier = ap.gameManager.player.getLoadout().getDamageMultiplier();
                ap.gameManager.player.getLoadout().setDamageMultiplier(-1.0f);
                instaKillActive = true;
            }
            case DOUBLE_POINTS -> {
                doublePointsActive = true;
            }
            case FIRE_SALE -> {
                fireSaleActive = true;
            }
            case INFINITE_AMMO -> {
                infiniteAmmoActive = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (Drop drop : spawnedDrops) {
            if (drop != null && drop.spawned) {
                drop.draw(g2, ap);
            }
        }
    }

    public void spawn(Drop drop) {
        spawnedDrops.add(drop);
        drop.spawned = true;

        drop.dropExpireTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                drop.dropFlashRate = 30;
                drop.dropFlashing = true;
            }
        }, 22000);
        drop.dropExpireTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                drop.dropFlashRate = 15;
            }
        }, 27000);
        drop.dropExpireTimer.schedule(new TimerTask() {
            public void run() {
                deleteDrop(drop);
            }
        }, 30000);
    }

    public void deleteDrop(Drop drop) {
        drop.spawned = false;
        ap.gameManager.aSetter.removeDrop(drop.objIndex);
    }
}
