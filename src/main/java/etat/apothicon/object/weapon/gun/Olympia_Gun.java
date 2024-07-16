package etat.apothicon.object.weapon.gun;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

public class Olympia_Gun extends Gun {
    public Olympia_Gun(Player player) {
        super(player,
                "Olympia",
                150,
                2,
                32,
                0.5f,
                SelectFire.SEMI_AUTO,
                1,
                1.0f,
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
