package etat.apothicon.object.weapon.gun;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.entity.Player;
import etat.apothicon.utility.sound.GunSound;

public class M1911_Gun extends Gun {
    public M1911_Gun(Player player) {
        super(player,
                "M1911",
                35,
                8,
                32,
                1.0f,
                1,
                1.0f,
                "src/main/resources/guns/m1911.png",
                FireType.SEMI_AUTO,
                GunSound.M1911_FIRE,
                GunSound.M1911_RELOAD,
                "Abel & Aubrey",
                "src/main/resources/guns/m1911-pap.png",
                400,
                50,
                6,
                GunSound.M1911_UPGRADED_FIRE
                );
    }

}
