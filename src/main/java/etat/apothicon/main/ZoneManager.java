package etat.apothicon.main;

import java.awt.Rectangle;
import java.util.ArrayList;

public class ZoneManager {
    private Apothicon ap;
    public ArrayList<Zone> zones;
    public ZoneManager(Apothicon ap) {
        this.ap = ap;
        this.zones = new ArrayList<>();
        setup();
    }
    private void setup() {
        Zone spawn = new Zone();
        spawn.worldX = 27 * ap.tileSize;
        spawn.worldY = 41 * ap.tileSize;


        spawn.zoneRects.add(new Rectangle(0,0, 16, 7));
        this.zones.add(spawn);
    }
}