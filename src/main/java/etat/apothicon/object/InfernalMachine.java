package etat.apothicon.object;

import etat.apothicon.entity.Player;
import etat.apothicon.object.weapon.gun.Gun;

public class InfernalMachine extends SuperObject {
    private final int tier1_price = 5000;
    private final int tier2_price = 15000;
    private final int tier3_price = 30000;

    public InfernalMachine() {
        this.name = "infernal_machine";
    }

    public void upgrade(Gun gun) {
        if (gun.upgradeTier < 3) {
            gun.upgrade();
            gun.upgradeTier++;
        }
    }

}
