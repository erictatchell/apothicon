package etat.apothicon.object.weapon.gun;

import etat.apothicon.entity.Player;
import etat.apothicon.utility.sound.GunSound;

public class MP40_Upgraded_Gun extends Gun {
    public MP40_Upgraded_Gun(Player player) {
        super(player,
                "MP40",
                75,
                32,
                192,
                0.8f,
                FireType.AUTO,
                GunSound.MP40_FIRE,
                GunSound.MP40_RELOAD,
                1,
                1.5f,
                "src/main/resources/guns/mp40.png");
        this.reserve = 384;
        this.defaultReserve = 384;
        this.defaultAmmoPerMagazine = 64;
        this.damage = 150;
    }

}
