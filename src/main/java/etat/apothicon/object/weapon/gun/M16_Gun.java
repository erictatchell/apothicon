package etat.apothicon.object.weapon.gun;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.entity.Player;
import etat.apothicon.utility.sound.GunSound;

public class M16_Gun extends Gun {
    public M16_Gun(Player player) {
        super(player,
                "M16",
                50,
                30,
                180,
                1.0f,
                1,
                1.0f,
                "src/main/resources/guns/m16.png",
                FireType.BURST,
                GunSound.M16_FIRE,
                GunSound.M16_RELOAD,
                "Emancipator 16000",
                "src/main/resources/guns/m16-pap.png",
                100,
                360,
                40,
                GunSound.M16_UPGRADED_FIRE
                );
    }
}
