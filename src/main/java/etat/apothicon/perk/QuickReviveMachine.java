package etat.apothicon.perk;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class QuickReviveMachine extends PerkMachine {
    // this boolean is very coop unfriendly, change!
    boolean purchasable = true;

    public QuickReviveMachine(Apothicon ap) {
        super(ap, "Quick Revive", 500);

        this.x = 200;
        this.y = 200;
        render();
    }

    public void purchase(Player player) {
        if (purchasable) {
            QuickRevive qr = new QuickRevive(player, ap);
            qr.activate(player);
            purchasable = false;
        }

    }

    public void render() {
        try {
            this.machine = ImageIO.read(new File("src/main/resources/perks/quickrevive-machine.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
