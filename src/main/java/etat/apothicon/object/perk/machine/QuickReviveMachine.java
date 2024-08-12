package etat.apothicon.object.perk.machine;

import etat.apothicon.entity.Player;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class QuickReviveMachine extends PerkMachine {
    public QuickReviveMachine() {

        this.name = "quick_revive";
        this.price = 500;
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/qr-stonewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void purchase(Player player) {
        // // TODO: create a purchasable boolean on each player object so we can
        // individualize purchases
        // if (player.isPerkPurchasable(this)) {
        // DoubleTap dt = new DoubleTap(player, ap);
        // dt.activateFor(player);
        // }
    }
}
