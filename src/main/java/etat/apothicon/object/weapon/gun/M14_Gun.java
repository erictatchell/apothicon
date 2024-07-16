package etat.apothicon.object.weapon.gun;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

public class M14_Gun extends Gun {
    public M14_Gun(Player player) {
        super(player,
                "M14",
                60,
                8,
                92,
                0.0f,
                SelectFire.SEMI_AUTO,
                1,
                1.0f,
                "src/main/resources/guns/m14-1.png");

        setImage2();
    }

    public void setImage2() {
        try {
            this.image2 = ImageIO.read(new File("src/main/resources/guns/m14-2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
