package etat.apothicon.object.drop;

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
    public ArrayList<Drop> activeDrops;

    private float defaultDamageMultiplier;

    public DropManager(Apothicon ap) {
        this.ap = ap;
        this.spawnedDrops = new ArrayList<>();
    }

    public void reset() {
        spawnedDrops = new ArrayList<>();
        activeDrops = new ArrayList<>();
        instaKillActive = false;
        doublePointsActive = false;
        fireSaleActive = false;
        infiniteAmmoActive = false;
    }

    // todo: move to utility class, make static
    public int getSlotX() {
        int temp = 0;
        for (int i = 0; i < activeDrops.size(); i++) {
            temp += ap.tileSize;
        }

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
        if (drop.dropType == DropType.MAX_AMMO) {
            drop.activate();
            return;
        }

        if (!isDropActive(drop.dropType)) {
            System.out.println("drop is not active");
            activeDrops.add(drop);
            drop.activate();
        }
        drop.spawned = false;
    }

    public void handleDeactivate(Drop drop) {
        // todo: extra processing? maybe a sound?
        if (isDropActive(drop.dropType)) {
            activeDrops.remove(drop);
            drop.deactivate();
        }
    }

    public void clearDrop(Drop drop) {
        spawnedDrops.remove(drop);
    }

    public void removeEffects(DropType type) {
        switch (type) {
            case INSTA_KILL -> {
                ap.gameManager.player.getLoadout().setDamageMultiplier(defaultDamageMultiplier);
                instaKillActive = false;
            }
            case FIRE_SALE -> {
                // boxPrice goes to defaultPrice
                fireSaleActive = false;
            }
            case DOUBLE_POINTS -> {
                ap.gameManager.player.getLoadout().setPointsMultiplier(1.0f);
                doublePointsActive = false;
            }
            case INFINITE_AMMO -> {
                ap.gameManager.player.getLoadout().setBottomlessClip(false);
                infiniteAmmoActive = false;
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
                ap.gameManager.player.getLoadout().setPointsMultiplier(2.0f);
                doublePointsActive = true;
            }
            case MAX_AMMO -> {
                ap.gameManager.player.getLoadout().refillAmmo();
            }
            case FIRE_SALE -> {
                fireSaleActive = true;
            }
            case INFINITE_AMMO -> {
                ap.gameManager.player.getLoadout().setBottomlessClip(true);
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
        if (!activeDrops.isEmpty()) revalidateSlotX();
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
                if (!drop.active) {
                    deleteDrop(drop);
                }
            }
        }, 30000);
    }

    public void revalidateSlotX() {
        int i = 1;
        for (Drop drop : activeDrops) {
            drop.slotX = i * ap.tileSize;
            i++;
        }
    }

    public void deleteDrop(Drop drop) {
        drop.spawned = false;
        ap.gameManager.aSetter.removeDrop(drop.objIndex);
    }
}
