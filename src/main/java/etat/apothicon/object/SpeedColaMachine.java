package etat.apothicon.object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;
import etat.apothicon.perk.PerkMachine;
import etat.apothicon.perk.SpeedCola;

public class SpeedColaMachine extends SuperObject {
    public SpeedColaMachine() {
        this.name = "Speed Cola";
        this.type = "perk";
        this.price = 3000;
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/sc-stonewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void purchase(Player player) {
        // if (player.isPurchasable(this)) {
        // SpeedCola sc = new SpeedCola(player, ap);
        // sc.activateFor(player);
        // }

    }
}
