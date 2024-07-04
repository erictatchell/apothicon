package etat.apothicon.perk;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class JuggernogMachine extends PerkMachine {
    // this boolean is very coop unfriendly, change!
    boolean purchasable = true;

    public JuggernogMachine(Apothicon ap) {
        super(ap, "Juggernog", 2500);

        this.x = 100;
        this.y = 100;
        render();
    }

    public void purchase(Player player) {
        if (purchasable) {
            Juggernog jug = new Juggernog(player, ap);
            jug.activate(player);
            purchasable = false;
        }
    }

    public void render() {
        try {
            this.machine = ImageIO.read(new File("src/main/resources/perks/juggernog-machine.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
