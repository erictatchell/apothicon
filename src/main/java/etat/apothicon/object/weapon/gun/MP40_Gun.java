package etat.apothicon.object.weapon.gun;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;
import etat.apothicon.sound.GunSound;

public class MP40_Gun extends Gun {
    public MP40_Gun(Player player) {
        super(player,
                "MP40",
                20,
                32,
                192,
                0.8f,
                FireType.AUTO,
                GunSound.MP40_FIRE,
                GunSound.MP40_RELOAD,
                1,
                1.5f,
                "src/main/resources/guns/mp40.png");
        setImage2();
    }

    public void setImage2() {
        try {
            this.image2 = ImageIO.read(new File("src/main/resources/guns/mp40-2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
