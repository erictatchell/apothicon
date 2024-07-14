package etat.apothicon.object.perk.machine;

import etat.apothicon.entity.Player;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class JuggernogMachine extends PerkMachine {
    public JuggernogMachine() {
        this.name = "Juggernog";
        this.price = 2500;
        try {
            image = ImageIO.read(new File("src/main/resources/perks/juggernog-machine.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void purchase(Player player) {
        // if (player.isPurchasable(this)) {
        // Juggernog jug = new Juggernog(player, ap);
        // jug.activateFor(player);
        // }
    }

}
