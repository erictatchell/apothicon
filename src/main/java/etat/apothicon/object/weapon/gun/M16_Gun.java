package etat.apothicon.object.weapon.gun;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

public class M16_Gun extends Gun {
    public M16_Gun(Player player) {
        super(player,
                "M16",
                20,
                30,
                180,
                1.0f,
                FireType.BURST,
                1,
                1.0f,
                "src/main/resources/guns/m16.png");
        setImage2();
    }

    public void setImage2() {
        try {
            this.image2 = ImageIO.read(new File("src/main/resources/guns/m16-2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
