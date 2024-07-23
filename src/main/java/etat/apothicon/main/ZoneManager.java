package etat.apothicon.main;

import java.awt.Rectangle;
import java.util.ArrayList;

public class ZoneManager {
    private Apothicon ap;
    public ArrayList<Zone> zones;
    public ZoneManager(Apothicon ap) {
        this.ap = ap;
        this.zones = new ArrayList<>();
        this.setup();
    }
    private void setup() {
        Zone spawn = new Zone();
        spawn.name = "spawn";
        spawn.worldX = 28 * ap.tileSize;
        spawn.worldY = 42 * ap.tileSize;
        spawn.zoneRects.add(new Rectangle(spawn.worldX,spawn.worldY, 16, 6));

        Zone cave = new Zone();
        cave.name = "cave";
        cave.worldX = 40 * ap.tileSize;
        cave.worldY = 30 * ap.tileSize;
        cave.zoneRects.add(new Rectangle(cave.worldX, cave.worldY, 9, 7));
        cave.zoneRects.add(new Rectangle(44 * ap.tileSize, 37 * ap.tileSize, 4, 9));


        this.zones.add(cave);
        this.zones.add(spawn);
    }
}