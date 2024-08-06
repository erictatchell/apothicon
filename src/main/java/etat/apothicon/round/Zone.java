package etat.apothicon.round;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import java.awt.*;
import java.util.ArrayList;
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
//  for each zone in ap.gameManager.zones:
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
public class Zone {

    public ArrayList<Rectangle> zoneRects;
    public int worldX, worldY;
    public String name;
    public ArrayList<ZombieSpawn> spawns;

    public Zone() {
        this.zoneRects = new ArrayList<>();
        this.spawns = new ArrayList<>();
    }

    public static boolean isPlayerInZone(Rectangle zoneRect, Player player) {
        boolean playerInRangeX = zoneRect.x / player.ap.tileSize <= player.worldX / player.ap.tileSize && zoneRect.x / player.ap.tileSize + zoneRect.width >= player.worldX / player.ap.tileSize;
        boolean playerInRangeY = zoneRect.y / player.ap.tileSize <= player.worldY / player.ap.tileSize && zoneRect.y / player.ap.tileSize + zoneRect.height >= player.worldY / player.ap.tileSize;

        return playerInRangeX && playerInRangeY;
    }

    public void draw(Graphics2D g2, Apothicon ap) {
        for (ZombieSpawn spawn : spawns) {
            spawn.draw(g2, ap);
        }
    }
    public void drawZoneBounds(Graphics2D g2, Apothicon ap) {
        for (Rectangle i : zoneRects) {
            int screenX = i.x - ap.gameManager.player.worldX + ap.gameManager.player.screenX;
            int screenY = i.y - ap.gameManager.player.worldY + ap.gameManager.player.screenY;
            int zoneWidth = i.width * ap.tileSize;
            int zoneHeight = i.height * ap.tileSize;

            g2.setColor(new Color(255, 0, 0, 70));
            if (i.x + zoneWidth > ap.gameManager.player.worldX - ap.gameManager.player.screenX &&
                    i.x - zoneWidth < ap.gameManager.player.worldX + ap.gameManager.player.screenX &&
                    i.y + zoneHeight > ap.gameManager.player.worldY - ap.gameManager.player.screenY &&
                    i.y - zoneHeight < ap.gameManager.player.worldY + ap.gameManager.player.screenY) {
                g2.fillRect(screenX, screenY, zoneWidth, zoneHeight);
            }

        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
