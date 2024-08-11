package etat.apothicon.object;

import etat.apothicon.main.Apothicon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DropManager {
    Apothicon ap;
    boolean instaKillActive;
    public ArrayList<Drop> drops;
    public DropManager(Apothicon ap) {
        this.ap = ap;
        this.drops = new ArrayList<>();
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

        // drops despawn after 25 sec
        drop.expireTimer = new Timer();
        drop.expireTimer.schedule(new TimerTask() {
            public void run() {
                drops.remove(drop);
                System.gc();
            }
        }, 25000);
    }
}
