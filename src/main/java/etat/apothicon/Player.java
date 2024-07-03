package etat.apothicon;

import java.util.ArrayList;

public class Player {
    int x;
    int y;
    int speed;
    int currentWeapon;
    ArrayList<Perk> perks;
    ArrayList<Gun> guns;

    Player(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.currentWeapon = 0;
    }

    public void addPerk(Perk perk) {
        this.perks.add(perk);
    }

    public void switchWeapon() {
        if ((currentWeapon + 1) != guns.size()) {
            this.currentWeapon++;
        } else this.currentWeapon = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(int currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public ArrayList<Perk> getPerks() {
        return perks;
    }

    public void setPerks(ArrayList<Perk> perks) {
        this.perks = perks;
    }

    public ArrayList<Gun> getGuns() {
        return guns;
    }

    public void setGuns(ArrayList<Gun> guns) {
        this.guns = guns;
    }
}
