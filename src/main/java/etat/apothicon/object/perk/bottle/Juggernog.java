package etat.apothicon.object.perk.bottle;

import etat.apothicon.entity.Loadout;
import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Juggernog extends Perk implements Drinkable {
    public Juggernog(Player player, Apothicon ap) {
        super("juggernog", player, ap);
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
        Loadout customerLoadout = customer.getLoadout();
        customerLoadout.setHealth(250);
        customerLoadout.setDefaultHealth(250);
        customerLoadout.addPerk(this);
        customerLoadout.spendPoints(2500);
        customerLoadout.hasJuggernog = true;
    }
}
