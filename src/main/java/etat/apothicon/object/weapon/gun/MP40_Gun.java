package etat.apothicon.object.weapon.gun;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MP40_Gun extends Gun {
    public MP40_Gun() {
        super("MP40",
                1,
                32,
                192,
                1.0f,
                SelectFire.AUTO,
                1,
                1.0f,
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
