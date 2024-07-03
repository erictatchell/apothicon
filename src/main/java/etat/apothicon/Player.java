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
}
