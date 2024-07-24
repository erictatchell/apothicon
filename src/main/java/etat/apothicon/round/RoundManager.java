package etat.apothicon.round;

import etat.apothicon.entity.Entity;
import etat.apothicon.main.Apothicon;

import java.util.Random;
import java.util.Timer;

public class RoundManager {
    private Entity zombies[];
    private Timer spawnTimer;
    private int currentRound;
    private int maxHorde;
    private int maxTotalZombiesForThisRound;
    private int totalZombiesForThisRound;
    private int totalZombiesOnMap;
    private Apothicon ap;
    final Object lock = new Object();

    private int spawnDelay;

    public RoundManager(Apothicon ap) {
        this.ap = ap;

        this.setup();

    }

    private void setup() {
        currentRound = 1;
        totalZombiesOnMap = 0;
        totalZombiesForThisRound = 0;
        maxHorde = 24;
        maxTotalZombiesForThisRound = 5;
        zombies = new Entity[maxTotalZombiesForThisRound];
    }


    public synchronized void spawnZombie(Zone currentZone) {
        Random random = new Random();
        int r = random.nextInt(currentZone.spawns.size());
        if ((totalZombiesForThisRound < maxTotalZombiesForThisRound && totalZombiesOnMap < maxHorde)) {
            ZombieSpawn spawn = currentZone.spawns.get(r);
            if (!spawn.spawning) {
                currentZone.spawns.get(r).handleAddZombieToMap(this, ap);
                totalZombiesForThisRound++;
                totalZombiesOnMap++;
            }

        } else if (totalZombiesOnMap == 0 && totalZombiesForThisRound == maxTotalZombiesForThisRound) {
            System.out.println("Round complete, going to round " + currentRound);
            currentRound++;
            totalZombiesForThisRound = 0;
            maxTotalZombiesForThisRound += 5;
            zombies = new Entity[maxTotalZombiesForThisRound];
        }
        else {
            System.out.println("Total Zombies on Map: " + totalZombiesOnMap);
            System.out.println("Total Zombies for Round " + currentRound + ": " + totalZombiesForThisRound);
            System.out.println("Max Total Zombies For Round" + currentRound + ": " +maxTotalZombiesForThisRound);
            System.out.println("Max Total Zombies For Map" + ": " +maxHorde);
        }
    }


    public int getCurrentRound() {
        return currentRound;
    }

    public Entity[] getZombies() {
        return zombies;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getMaxHorde() {
        return maxHorde;
    }

    public void setMaxHorde(int maxZombieCountMap) {
        this.maxHorde = maxZombieCountMap;
    }

    public int getMaxTotalZombiesForThisRound() {
        return maxTotalZombiesForThisRound;
    }

    public void setMaxTotalZombiesForThisRound(int maxTotalZombiesForThisRound) {
        this.maxTotalZombiesForThisRound = maxTotalZombiesForThisRound;
    }

    public int getTotalZombiesForThisRound() {
        return totalZombiesForThisRound;
    }

    public void setTotalZombiesForThisRound(int totalZombiesForThisRound) {
        this.totalZombiesForThisRound = totalZombiesForThisRound;
    }

    public int getTotalZombiesOnMap() {
        return totalZombiesOnMap;
    }

    public void decreaseTotalZombiesOnMap() {
        this.totalZombiesOnMap--;
    }
}
