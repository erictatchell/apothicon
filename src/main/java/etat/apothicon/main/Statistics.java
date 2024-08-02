package etat.apothicon.main;

import etat.apothicon.entity.Player;

public class Statistics {
    private Player player;
    private int kills;
    private int headshots;
    private int totalPoints;
    private int pointsSpent;
    private int perksBought;
    private int shotsFired;
    private int downs;

    public Statistics(Player player) {
        this.player = player;
        this.kills = 0;
        this.headshots = 0;
        this.totalPoints = player.loadout.getPoints();
        this.pointsSpent = 0;
        this.perksBought = 0;
        this.shotsFired = 0;
        this.downs = 0;
    }

    public void addDowns() {
        downs++;
    }
    public void addKill(boolean headshot) {
        kills++;
        if (headshot) {
            headshots++;
        }
    }
    public void addPoints(int points) {
        totalPoints += points;
    }
    public void addSpentPoints(int points) {
        pointsSpent += points;
    }
    public void perkBought() {
        perksBought++;
    }
    public void addShotFired() {
        shotsFired++;
    }
    public void reset() {
       kills = 0;
       headshots = 0;
       pointsSpent = 0;
       perksBought = 0;
       shotsFired = 0;
       totalPoints = 0;
    }
}
