package etat.apothicon.perk;

import etat.apothicon.entity.Drinkable;
import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Juggernog extends Perk implements Drinkable {
    public Juggernog(Player player, Apothicon ap) {
        super(player, ap);
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
        customer.setHealth(250);
        customer.setDefaultHealth(250);
        customer.addPerk(this);
        customer.setPoints(customer.getPoints() - 2500);
    }
}
