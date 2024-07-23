package etat.apothicon.round;

import etat.apothicon.entity.Entity;
import etat.apothicon.main.Apothicon;

public class RoundManager {
    private Entity zombies[];
    private int currentRound;
    private int maxZombieCountMap;
    private int maxZombieCountRound;
    private int zombieCountRound;
    private int zombieCountMap;
    private Apothicon ap;

    public RoundManager(Apothicon ap) {
        this.ap = ap;

        this.setup();

    }

    private void setup() {
        currentRound = 1;
        zombieCountMap = 0;
        zombieCountRound = 6;
        maxZombieCountMap = 24;
        maxZombieCountRound = 6;
        zombies = new Entity[maxZombieCountMap];
    }

    public void addZombieToMap() {
        zombieCountMap++;
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

    public int getMaxZombieCountMap() {
        return maxZombieCountMap;
    }

    public void setMaxZombieCountMap(int maxZombieCountMap) {
        this.maxZombieCountMap = maxZombieCountMap;
    }

    public int getMaxZombieCountRound() {
        return maxZombieCountRound;
    }

    public void setMaxZombieCountRound(int maxZombieCountRound) {
        this.maxZombieCountRound = maxZombieCountRound;
    }

    public int getZombieCountRound() {
        return zombieCountRound;
    }

    public void setZombieCountRound(int zombieCountRound) {
        this.zombieCountRound = zombieCountRound;
    }

    public int getZombieCountMap() {
        return zombieCountMap;
    }

    public void setZombieCountMap(int zombieCountMap) {
        this.zombieCountMap = zombieCountMap;
    }
}
