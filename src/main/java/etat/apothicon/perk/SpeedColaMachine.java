package etat.apothicon.perk;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;


public class SpeedColaMachine extends PerkMachine {
    // this boolean is very coop unfriendly, change!
    boolean purchasable = true;

    public SpeedColaMachine(Apothicon ap) {
        super(ap, "Speed Cola", 3000);

        this.x = 300;
        this.y = 100;
        render("speedcola-machine.png");
    }

    public void purchase(Player player) {
        if (purchasable) {
            SpeedCola sc = new SpeedCola(player, ap);
            sc.activateFor(player);
            purchasable = false;
        }

    }
}
