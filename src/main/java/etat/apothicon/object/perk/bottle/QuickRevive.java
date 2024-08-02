package etat.apothicon.object.perk.bottle;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class QuickRevive extends Perk implements Drinkable {
    public QuickRevive(Player player, Apothicon ap) {
        super("Quick Revive", player, ap);
        render();
    }

    public void render() {
        try {
            this.icon = ImageIO.read(new File("src/main/resources/perks/quickrevive-icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activateFor(Player customer) {
        customer.loadout.setRevives(1);
        customer.loadout.addPerk(this);
        customer.loadout.spendPoints(500);
        customer.loadout.hasQuickRevive = true;
    }
}
