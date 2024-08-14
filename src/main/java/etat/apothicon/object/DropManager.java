package etat.apothicon.object;

import etat.apothicon.main.Apothicon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DropManager {
    Apothicon ap;
    public boolean instaKillActive;
    public ArrayList<Drop> drops;
    public DropManager(Apothicon ap) {
        this.ap = ap;
        this.drops = new ArrayList<>();
    }

    public int getSlotX() {
        int temp = 0;
        for (int i = 0; i < drops.size(); i++) {
            temp += ap.tileSize;
        }
        return temp;
    }

    public void draw(Graphics2D g2) {
        for (Drop drop : drops) {
            if (drop.spawned) {
                drop.draw(g2, ap);
            }
        }
    }
    public void spawn(Drop drop) {
        drops.add(drop);
        drop.spawned = true;



        // drops despawn after 30 sec
        drop.expireTimer = new Timer();

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
        drops.remove(drop);
        drop.spawned = false;
        ap.gameManager.aSetter.removeDrop(drop.objIndex);
    }
}
