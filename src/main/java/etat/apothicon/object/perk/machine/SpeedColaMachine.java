package etat.apothicon.object.perk.machine;

import etat.apothicon.entity.Player;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpeedColaMachine extends PerkMachine {
    public SpeedColaMachine() {
        this.name = "Speed Cola";
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
