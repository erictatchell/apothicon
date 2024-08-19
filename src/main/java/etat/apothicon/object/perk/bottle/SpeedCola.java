package etat.apothicon.object.perk.bottle;

import etat.apothicon.entity.Loadout;
import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpeedCola extends Perk implements Drinkable {
    public SpeedCola(Player player, Apothicon ap) {
        super("speed_cola", player, ap);
        render();
    }

    public void render() {
        try {
            this.icon = ImageIO.read(new File("src/main/resources/perks/speedcola-icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activateFor(Player customer) {
        Loadout customerLoadout = customer.getLoadout();
        customerLoadout.setReloadRate(0.7f);
        customerLoadout.addPerk(this);
        customerLoadout.spendPoints(3000);
    }
}
