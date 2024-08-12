package etat.apothicon.object.weapon.gun;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.entity.Player;
import etat.apothicon.utility.sound.GunSound;

public class M14_Gun extends Gun {
    public M14_Gun(Player player) {
        super(player,
                "M14",
                140,
                8,
                92,
                1.0f,
                1,
                1.5f,
                "src/main/resources/guns/m14-1.png",
                FireType.SEMI_AUTO,
                GunSound.M14_FIRE,
                GunSound.M14_RELOAD,
                "Ember",
                "src/main/resources/guns/m14-pap.png",
                300,
                144,
                12,
                GunSound.M14_UPGRADED_FIRE
        );

    }
}
