package etat.apothicon.object.perk.bottle;

import etat.apothicon.entity.Drinkable;
import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

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
        customer.setRevives(1);
        customer.addPerk(this);
        customer.setPoints(customer.getPoints() - 500);
    }
}
