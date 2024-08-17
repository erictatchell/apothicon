package etat.apothicon.object;

import etat.apothicon.main.Apothicon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;

public class DropManager {
    Apothicon ap;
    public boolean instaKillActive = false;
    public boolean doublePointsActive = false;
    public boolean fireSaleActive = false;
    public boolean infiniteAmmoActive = false;
    public ArrayList<Drop> spawnedDrops;
    public ArrayList<Drop> activeDrops;

    public DropManager(Apothicon ap) {
        this.ap = ap;
        this.spawnedDrops = new ArrayList<>();
        this.activeDrops = new ArrayList<>();
    }

    // todo: move to utility class, make static
    public int getSlotX() {
        int temp = 0;
        for (int i = 0; i < activeDrops.size(); i++) {
            temp += ap.tileSize;
        }

        System.out.println("Active Drops: " + activeDrops.size() + "\nReturned X: " + temp);
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

            activeDrops.add(drop);
            drop.activate();
        }
    }

    public void handleDeactivate(Drop drop) {
        // todo: extra processing? maybe a sound?
        activeDrops.remove(drop);
        drop.deactivate();
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

        drop.expireTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                drop.flashRate = 30;
                drop.flashing = true;
            }
        }, 22000);
        drop.expireTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                drop.flashRate = 15;
            }
        }, 27000);
        drop.expireTimer.schedule(new TimerTask() {
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
