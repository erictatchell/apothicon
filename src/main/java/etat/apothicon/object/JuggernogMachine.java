package etat.apothicon.object;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;
import etat.apothicon.perk.Juggernog;
import etat.apothicon.perk.PerkMachine;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class JuggernogMachine extends SuperObject {
    public JuggernogMachine() {
        this.name = "Juggernog";
        this.type = "perk";
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
