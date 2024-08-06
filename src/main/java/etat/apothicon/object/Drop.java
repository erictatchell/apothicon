package etat.apothicon.object;

import etat.apothicon.main.Apothicon;

import java.util.Timer;
import java.util.TimerTask;

public class Drop extends SuperObject {
    public DropType type;
    public Apothicon ap;
    public Timer expireTimer;
    public boolean spawned = true;
    public Drop(Apothicon ap, DropType type) {
        this.ap = ap;
        this.type = type;
    }

    public void spawn() {
        spawned = true;

        // drops despawn after 25 sec
        expireTimer = new Timer().schedule(new TimerTask() {
            public void run() {
                ap.gameManager.drops.remove(this);
                System.gc();
            }
        }, 25000);
    }

}
