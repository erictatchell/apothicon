package etat.apothicon.perk;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

public class QuickReviveMachine extends PerkMachine {
    // this boolean is very coop unfriendly, change!
    boolean purchasable = true;

    public QuickReviveMachine(Apothicon ap) {
        super(ap, "Quick Revive", 500);

        this.x = 200;
        this.y = 100;
        render("quickrevive-machine.png");
    }

    public void purchase(Player player) {
        if (player.isPurchasable(this)) {
            QuickRevive qr = new QuickRevive(player, ap);
            qr.activateFor(player);
            purchasable = false;
        }

    }


}
