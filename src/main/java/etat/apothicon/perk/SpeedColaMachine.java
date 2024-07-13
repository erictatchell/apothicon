package etat.apothicon.perk;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;


public class SpeedColaMachine extends PerkMachine {
    // this boolean is very coop unfriendly, change!
    boolean purchasable = true;

    public SpeedColaMachine(Apothicon ap) {
        super(ap, "Speed Cola", 3000);

        this.worldX = 300;
        this.worldY = 100;
        render("speedcola-machine.png");
    }

    public void purchase(Player player) {
        if (player.isPurchasable(this)) {
            SpeedCola sc = new SpeedCola(player, ap);
            sc.activateFor(player);
            purchasable = false;
        }

    }
}
