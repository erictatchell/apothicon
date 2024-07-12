package etat.apothicon.perk;

import etat.apothicon.entity.Drinkable;
import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

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
        
        customer.addPerk(this);
        customer.setPoints(customer.getPoints() - 2000);
    }
}
