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
                FireType.SEMI_AUTO,
                GunSound.OLYMPIA_FIRE,
                GunSound.OLYMPIA_RELOAD,
                1,
                4.0f,
                "src/main/resources/guns/olympia.png");
        setImage2();
    }

    public void setImage2() {
        try {
            this.image2 = ImageIO.read(new File("src/main/resources/guns/olympia-2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
