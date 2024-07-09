package etat.apothicon.perk;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;


public class DoubleTapMachine extends PerkMachine {
    // this boolean is very coop unfriendly, change!
    boolean purchasable = true;

    public DoubleTapMachine(Apothicon ap) {
        super(ap, "Double Tap 2.0", 200);

        // TODO: are x and y not common amongst all perkmachines? should be set in super()
        this.x = 400;
        this.y = 100;
        render("doubletap-machine.png");
    }

    public void purchase(Player player) {
        // TODO: create a purchasable boolean on each player object so we can individualize purchases
        if (purchasable) {
            DoubleTap dt = new DoubleTap(player, ap);
            dt.activateFor(player);
            purchasable = false;
        }
    }
}
