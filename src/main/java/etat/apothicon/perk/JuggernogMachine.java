package etat.apothicon.perk;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class JuggernogMachine extends PerkMachine {
    boolean purchasable = true;

    public JuggernogMachine(Apothicon ap) {
        super(ap, "Juggernog", 2500);

        // TODO: are x and y not common amongst all perkmachines? should be set in
        // super()
        this.x = 100;
        this.y = 100;
        render("juggernog-machine.png");
    }

    public void purchase(Player player) {
        if (player.isPurchasable(this)) {
            Juggernog jug = new Juggernog(player, ap);
            jug.activateFor(player);
            purchasable = false;
        }
    }

}
