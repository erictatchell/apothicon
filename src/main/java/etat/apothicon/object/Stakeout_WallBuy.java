package etat.apothicon.object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Stakeout_WallBuy extends SuperObject {
    public Stakeout_WallBuy() {
        this.name = "Stakeout_WallBuy";
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/stakeout-stonewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
