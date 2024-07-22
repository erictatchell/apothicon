package etat.apothicon.object.perk.bottle;

import etat.apothicon.entity.Drinkable;
import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DoubleTap extends Perk implements Drinkable {
    public DoubleTap(Player player, Apothicon ap) {

        super("Double Tap 2.0", player, ap);
        render();
    }

    public void render() {
        try {
            this.icon = ImageIO.read(new File("src/main/resources/perks/doubletap-icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activateFor(Player customer) {

        customer.loadout.setFireRateMultiplier(2.0f);
        customer.loadout.addPerk(this);
        customer.loadout.spendPoints(2000);
        customer.loadout.hasDoubleTap = true;
    }
}
