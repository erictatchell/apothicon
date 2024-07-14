package etat.apothicon.object.perk.machine;
import etat.apothicon.entity.Player;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class DoubleTapMachine extends PerkMachine {
    public DoubleTapMachine() {
        this.name = "Double Tap 2.0";

        this.price = 2000;
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/dt-stonewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

    public void purchase(Player player) {
        // // TODO: create a purchasable boolean on each player object so we can individualize purchases
        // if (player.isPerkPurchasable(this)) {
        //     DoubleTap dt = new DoubleTap(player, ap);
        //     dt.activateFor(player);
        // }
    }
}
