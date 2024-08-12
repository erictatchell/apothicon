package etat.apothicon.object.weapon.gun;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.entity.Player;
import etat.apothicon.utility.sound.GunSound;

public class Olympia_Gun extends Gun {
    public Olympia_Gun(Player player) {
        super(player,
                "Olympia",
                450,
                2,
                32,
                3.0f,
                1,
                4.0f,
                "src/main/resources/guns/olympia.png",
                FireType.SEMI_AUTO,
                GunSound.OLYMPIA_FIRE,
                GunSound.OLYMPIA_RELOAD,
                "Crete 725",
                "src/main/resources/guns/olympia-pap.png",
                1000,
                50,
                2,
                GunSound.OLYMPIA_UPGRADED_FIRE
        );
    }
}
