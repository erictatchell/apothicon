package etat.apothicon.object.weapon.gun;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;
import etat.apothicon.sound.GunSound;

public class M1911_Gun extends Gun {
    public M1911_Gun(Player player) {
        super(player,
                "M1911",
                15,
                8,
                32,
                1.0f,
                FireType.SEMI_AUTO,
                GunSound.M1911_FIRE,
                GunSound.M1911_RELOAD,
                1,
                1.0f,
                "src/main/resources/guns/m1911.png");
        setImage2();
    }

    public void setImage2() {
        try {
            this.image2 = ImageIO.read(new File("src/main/resources/guns/m1911-2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
