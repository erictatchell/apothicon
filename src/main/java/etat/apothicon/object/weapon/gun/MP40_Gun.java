package etat.apothicon.object.weapon.gun;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.entity.Player;
import etat.apothicon.utility.sound.GunSound;

public class MP40_Gun extends Gun {
    public MP40_Gun(Player player) {
        super(player,
                "MP40",
                75,
                32,
                192,
                0.8f,
                1,
                1.5f,
                "src/main/resources/guns/mp40.png",
                FireType.AUTO,
                GunSound.MP40_FIRE,
                GunSound.MP40_RELOAD,
                "Mortality Provider 4000",
                "src/main/resources/guns/mp40-pap.png",
                150,
                384,
                64,
                GunSound.MP40_UPGRADED_FIRE);
    }
}
