package etat.apothicon.object.weapon.gun;

import etat.apothicon.entity.Player;
import etat.apothicon.utility.sound.GunSound;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Stakeout_Gun extends Gun {
    public Stakeout_Gun(Player player) {
        super(player,
                "Stakeout",
                500,
                8,
                32,
                5.0f,
                FireType.SEMI_AUTO,
                GunSound.STAKEOUT_FIRE,
                GunSound.STAKEOUT_RELOAD,
                1,
                2.0f,
                "src/main/resources/guns/stakeout.png");
    }


}
