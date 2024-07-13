package etat.apothicon.object;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;
import etat.apothicon.perk.DoubleTap;
import etat.apothicon.perk.PerkMachine;


public class QuickReviveMachine extends SuperObject{
    public QuickReviveMachine() {
        this.name = "Quick Revive";
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/qr-stonewall.png"));
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
