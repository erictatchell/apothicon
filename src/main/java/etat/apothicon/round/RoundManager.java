package etat.apothicon.round;

import etat.apothicon.entity.Entity;
import etat.apothicon.entity.Zombie;
import etat.apothicon.main.Apothicon;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RoundManager {
    private Entity[] zombies;
    private Timer roundDelay;
    private boolean changingRound;
    private int currentRound;
    private int maxHorde;
    private int maxTotalZombiesForThisRound;
    private int totalZombiesKilled;
    private int totalZombiesSpawnedForThisRound;
    private int totalZombiesOnMap;
    private Apothicon ap;
    final Object lock = new Object();

    Random random = new Random();
    private int spawnDelay;

    public RoundManager(Apothicon ap) {
        this.ap = ap;

        this.setup();

    }

    public int getTotalZombiesKilled() {
        return totalZombiesKilled;
    }

    private void setup() {
        currentRound = 1;
        changingRound = false;
        totalZombiesOnMap = 0;
        totalZombiesSpawnedForThisRound = 0;
        maxHorde = 24;
        maxTotalZombiesForThisRound = 5;
        zombies = new Entity[maxTotalZombiesForThisRound];
    }


    public synchronized void update(Zone currentZone) {
        int r = random.nextInt(currentZone.spawns.size());
        boolean allowedToSpawn = (totalZombiesSpawnedForThisRound < maxTotalZombiesForThisRound && totalZombiesOnMap < maxHorde);
        boolean roundOver = (totalZombiesOnMap == 0 && totalZombiesSpawnedForThisRound == maxTotalZombiesForThisRound)
                || (totalZombiesKilled == maxTotalZombiesForThisRound);
        if (allowedToSpawn) {
            ZombieSpawn spawn = currentZone.spawns.get(r);
            if (!spawn.spawning) {
                spawnZombie(spawn);
            }

        } else if (roundOver && !changingRound) {
            startNewRound();
            changingRound = true;
        }
    }

    private void spawnZombie(ZombieSpawn spawn) {
        spawn.handleAddZombieToMap(this, ap);
        totalZombiesSpawnedForThisRound++;
        totalZombiesOnMap++;
    }


    private void startNewRound() {
        roundDelay = new Timer();
        roundDelay.schedule(new TimerTask() {
            public void run() {
                currentRound++;
                totalZombiesKilled = 0;
                totalZombiesSpawnedForThisRound = 0;
                maxTotalZombiesForThisRound += 5;
                zombies = new Entity[maxTotalZombiesForThisRound];

                changingRound = false;
            }
        }, 10000);


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

    public int getTotalZombiesSpawnedForThisRound() {
        return totalZombiesSpawnedForThisRound;
    }

    public void setTotalZombiesSpawnedForThisRound(int totalZombiesSpawnedForThisRound) {
        this.totalZombiesSpawnedForThisRound = totalZombiesSpawnedForThisRound;
    }

    public int getTotalZombiesOnMap() {
        return totalZombiesOnMap;
    }

    public void decreaseTotalZombiesOnMap() {
        this.totalZombiesOnMap--;
    }

    public void increaseTotalZombiesKilled() {
        this.totalZombiesKilled++;
    }
}
