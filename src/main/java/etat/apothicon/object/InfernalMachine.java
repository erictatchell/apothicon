package etat.apothicon.object;

import etat.apothicon.entity.Player;
import etat.apothicon.object.weapon.gun.Gun;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class InfernalMachine extends SuperObject {
    private final int tier1_price = 5000;
    private final int tier2_price = 15000;
    private final int tier3_price = 30000;

    public InfernalMachine() {
        this.name = "infernal_machine";
        this.type = "pap";
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/pap.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upgrade(Gun gun) {
        gun.upgrade();
        gun.upgradeTier++;
    }

    public int getPrice(Gun gun) {
        if (gun.upgradeTier == 0) {
            return tier1_price;
        } else if (gun.upgradeTier == 1) {
            return tier2_price;
        } else return tier3_price;
    }

    public int getNextTier(Gun gun) {
        if (gun.upgradeTier == 0) {
            return 1;
        } else if (gun.upgradeTier == 1) {
            return 2;
        } else return 3;
    }
}
