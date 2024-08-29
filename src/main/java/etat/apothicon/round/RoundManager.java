package etat.apothicon.round;

import etat.apothicon.entity.Entity;
import etat.apothicon.entity.Zombie;
import etat.apothicon.main.Apothicon;
import etat.apothicon.utility.sound.RoundChangeMusic;
import etat.apothicon.utility.sound.SoundType;

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
        totalZombiesKilled = 0;
        totalZombiesSpawnedForThisRound = 0;
        maxHorde = 24;
        maxTotalZombiesForThisRound = 5 * currentRound;
        Zombie.defaultHealth = 150.0f;
        zombies = new Entity[maxTotalZombiesForThisRound];
    }

    public void reset() {
        setup();
    }


    public synchronized void update(Zone currentZone) {
        int r = random.nextInt(currentZone.spawns.size());
        boolean allowedToSpawn = (totalZombiesSpawnedForThisRound < maxTotalZombiesForThisRound && totalZombiesOnMap < maxHorde);
        boolean roundOver = (totalZombiesOnMap == 0 && totalZombiesSpawnedForThisRound == maxTotalZombiesForThisRound)
                || (totalZombiesKilled == maxTotalZombiesForThisRound);
        if (allowedToSpawn) {
            ZombieSpawn spawn = currentZone.spawns.get(r);
            if (!spawn.isSpawning()) {
                spawn.spawnZombie(this, ap);
                totalZombiesSpawnedForThisRound++;
                totalZombiesOnMap++;
            }

        } else if (roundOver && !changingRound) {
            startNewRound();
            changingRound = true;
        }
    }

    private void startNewRound() {
        ap.playSE(RoundChangeMusic.JULY_30_CUSTOM.ordinal(), SoundType.ROUND_CHANGE);
//        ap.gameManager.aSetter.cleanUpObjects();
        roundDelay = new Timer();
        roundDelay.schedule(new TimerTask() {
            public void run() {
                currentRound++;
                totalZombiesKilled = 0;
                totalZombiesSpawnedForThisRound = 0;
                maxTotalZombiesForThisRound += 5;
                Zombie.increaseDefaultHealth(currentRound);
                zombies = new Entity[maxTotalZombiesForThisRound];
                ap.gameManager.hud.updateCurrentRound();
                changingRound = false;
            }
        }, 5000);

    }

    public void togglePathfinding(boolean option) {
        for (Entity zombie : zombies ) {
            if (zombie != null) {

                zombie.onPath = option;
            }
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
