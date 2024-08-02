package etat.apothicon.object.perk.bottle;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpeedCola extends Perk implements Drinkable {
    public SpeedCola(Player player, Apothicon ap) {
        super("Speed Cola", player, ap);
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
        customer.loadout.setReloadRate(0.7f);
        customer.loadout.addPerk(this);
        customer.loadout.spendPoints(3000);
        customer.loadout.hasSpeedCola = true;
    }
}
