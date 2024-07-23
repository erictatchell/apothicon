package etat.apothicon.main;

import java.awt.*;
import java.util.ArrayList;

public class Zone {
    // have a ZoneManager that creats all zones for the map
    // but which map?
    // I would like to create a map making tool which lets you plop purchasables and zones
    // array of Point made of worldxy? bad sol find better
    // maybe just have a rectangle?
    // what about overlaps? where is precedence given? both?
    // zone only activates if it has a door opened to it?
    // overlapping zones have "relationships"?
    //
    // wait, what if we have a big rectangle for each zone but you're really only "in" the zone
    // if the tile you're on has no collision
    // still doesnt solve overlapping zones
    // perhaps an ArrayList of rectangles zoneRects for each zone?
    // therefore, when doing zone check we can do a foreach rectangle in zoneRects
    // this solves weird "L" shaped rooms
    // this is the way (for now)
    // 
    //
    // nested loop, ew!? its gonna be max 1-3 zone rects but still
    // in player, call in update(): 
    //  for each zone in ap.gameState.zones:
    //      for each zoneRect in zone.zoneRects:
    //          if player.solidArea.intersects(zoneRect)
    //              currentZone = zone;
    //
    // each zone has ArrayList of ZombieSpawn spawns
    // each spawn can only activate if the parent zone is the currentZone
    
    //  for each spawn in zone.spawns:
    //
    //      if
    //
    //         ap.rh.zombieCount_round < ap.rh.maxZombieCount_round
    //      && ap.rh.zombieCount_spawned < ap.rh.maxZombieCount_spawned:
    //
    //          spawn.spawnZombie(); //zombieCount++ in here
    //          disable(2 seconds);
    //

    public ArrayList<Rectangle> zoneRects;
    public int worldX, worldY;
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public ArrayList<ZombieSpawn> spawns;
    public Zone() {
        this.zoneRects = new ArrayList<>();
        
    }
 
    public void draw(Graphics2D g2, Apothicon ap) {
        for (Rectangle i : zoneRects) {
            int screenX = i.x - ap.gameState.player.worldX + ap.gameState.player.screenX;
            int screenY = i.y - ap.gameState.player.worldY + ap.gameState.player.screenY;
            int zoneWidth = i.width * ap.tileSize;
            int zoneHeight = i.height * ap.tileSize;

            g2.setColor(new Color(255, 0, 0, 70));
            if     (i.x + zoneWidth > ap.gameState.player.worldX - ap.gameState.player.screenX &&
                    i.x - zoneWidth < ap.gameState.player.worldX + ap.gameState.player.screenX &&
                     i.y+ zoneHeight > ap.gameState.player.worldY - ap.gameState.player.screenY &&
                    i.y - zoneHeight < ap.gameState.player.worldY + ap.gameState.player.screenY) {
                g2.fillRect(screenX, screenY, zoneWidth, zoneHeight);
           }

        }
   }
}
