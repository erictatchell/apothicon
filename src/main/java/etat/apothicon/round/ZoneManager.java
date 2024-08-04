package etat.apothicon.round;

import etat.apothicon.main.Apothicon;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class ZoneManager {
    private Apothicon ap;
    public ArrayList<Zone> zones;
    public Zone currentZone;
    public ZoneManager(Apothicon ap) {
        this.ap = ap;
        this.zones = new ArrayList<>();
        this.setup();
    }


    private void setup() {
        // leave just these two for now until we make an actual good map (hopefully with a nice tool!)
        Zone spawn = new Zone();
        spawn.name = "spawn";
        spawn.worldX = 28 * ap.tileSize;
        spawn.worldY = 42 * ap.tileSize;
        spawn.zoneRects.add(new Rectangle(spawn.worldX,spawn.worldY, 16, 6));
        spawn.spawns.add(new ZombieSpawn(spawn, "door-barrier.png", 42* ap.tileSize, 41 * ap.tileSize));
        spawn.spawns.add(new ZombieSpawn(spawn, "door-barrier.png", 32* ap.tileSize, 41 * ap.tileSize));
        spawn.spawns.add(new ZombieSpawn(spawn, "stone-barrier.png", 27* ap.tileSize, 44 * ap.tileSize));

        Zone rightCave = new Zone();
        rightCave.name = "cave";
        rightCave.worldX = 40 * ap.tileSize;
        rightCave.worldY = 30 * ap.tileSize;
        rightCave.zoneRects.add(new Rectangle(rightCave.worldX, rightCave.worldY, 9, 7)); // bigger area
        rightCave.zoneRects.add(new Rectangle(45 * ap.tileSize, 37 * ap.tileSize, 3, 9)); // hallway
        rightCave.spawns.add(new ZombieSpawn(rightCave, "door-barrier.png", 48* ap.tileSize, 41 * ap.tileSize));
        rightCave.spawns.add(new ZombieSpawn(rightCave, "door-barrier.png", 48* ap.tileSize, 31 * ap.tileSize));

        this.zones.add(rightCave);
        this.zones.add(spawn);

        this.currentZone = spawn;
    }
}