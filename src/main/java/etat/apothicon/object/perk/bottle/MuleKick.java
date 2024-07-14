package etat.apothicon.object.perk.bottle;

import etat.apothicon.entity.Drinkable;
import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MuleKick extends Perk implements Drinkable {
    public MuleKick(Player player, Apothicon ap) {

        super("Mule Kick", player, ap);
        render();
    }

    public void render() {
        try {
            this.icon = ImageIO.read(new File("src/main/resources/perks/mulekick-icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activateFor(Player customer) {

        customer.loadout.setMaxGunNum(3);
        customer.loadout.addPerk(this);
        customer.loadout.spendPoints(4000);
    }
}

