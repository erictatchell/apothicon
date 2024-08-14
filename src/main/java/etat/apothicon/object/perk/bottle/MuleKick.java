package etat.apothicon.object.perk.bottle;

import etat.apothicon.entity.Loadout;
import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MuleKick extends Perk implements Drinkable {
    public MuleKick(Player player, Apothicon ap) {

        super("mule_kick", player, ap);
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
        Loadout customerLoadout = customer.getLoadout();
        customerLoadout.setMaxGunNum(3);
        customerLoadout.addPerk(this);
        customerLoadout.spendPoints(4000);
        customerLoadout.hasMuleKick = true;
    }
}

