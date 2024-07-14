package etat.apothicon.object.perk.machine;
import etat.apothicon.entity.Player;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class MuleKickMachine extends PerkMachine {
    public MuleKickMachine() {
        this.name = "Mule Kick";
        this.type = "perk";
        this.price = 4000;
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/mulekick-snowstonewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void purchase(Player player) {
        // // TODO: create a purchasable boolean on each player object so we can individualize purchases
        // if (player.isPerkPurchasable(this)) {
        //     DoubleTap dt = new DoubleTap(player, ap);
        //     dt.activateFor(player);
        // }
    }
}

