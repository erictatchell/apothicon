package etat.apothicon.object.perk.bottle;

import etat.apothicon.entity.Loadout;
import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DoubleTap extends Perk implements Drinkable {
    public DoubleTap(Player player, Apothicon ap) {

        super("double_tap", player, ap);
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
        Loadout customerLoadout = customer.getLoadout();

        customerLoadout.setFireRateMultiplier(1.5f);
        customerLoadout.addPerk(this);
        customerLoadout.spendPoints(2000);
    }
}
