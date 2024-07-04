package etat.apothicon.perk;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SpeedColaMachine extends PerkMachine {
    // this boolean is very coop unfriendly, change!
    boolean purchasable = true;

    public SpeedColaMachine(Apothicon ap) {
        super(ap, "Speed Cola", 3000);

        this.x = 300;
        this.y = 100;
        render();
    }

    public void purchase(Player player) {
        if (purchasable) {
            SpeedCola sc = new SpeedCola(player, ap);
            sc.activate(player);
            purchasable = false;
        }

    }

    public void render() {
        try {
            this.machine = ImageIO.read(new File("src/main/resources/perks/speedcola-machine.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
