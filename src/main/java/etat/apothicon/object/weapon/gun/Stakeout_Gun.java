package etat.apothicon.object.weapon.gun;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Stakeout_Gun extends Gun {
    public Stakeout_Gun() {
        super("Stakeout",
                1,
                8,
                32,
                0.0f,
                SelectFire.SEMI_AUTO,
                1,
                1.0f,
                "src/main/resources/guns/stakeout.png");
        setImage2();
    }
    public void setImage2() {
        try {
            this.image2 = ImageIO.read(new File("src/main/resources/guns/stakeout-2.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
