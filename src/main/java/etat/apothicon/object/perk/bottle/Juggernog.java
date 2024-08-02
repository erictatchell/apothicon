package etat.apothicon.object.perk.bottle;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Juggernog extends Perk implements Drinkable {
    public Juggernog(Player player, Apothicon ap) {
        super("Juggernog", player, ap);
        render();
    }

    public void render() {
        try {
            this.icon = ImageIO.read(new File("src/main/resources/perks/juggernog-icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activateFor(Player customer) {
        customer.loadout.setHealth(250);
        customer.loadout.setDefaultHealth(250);
        customer.loadout.addPerk(this);
        customer.loadout.spendPoints(2500);
        customer.loadout.hasJuggernog = true;
    }
}
