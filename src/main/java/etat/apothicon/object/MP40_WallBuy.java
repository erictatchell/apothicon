package etat.apothicon.object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MP40_WallBuy extends SuperObject {
    public MP40_WallBuy() {
        this.name = "MP40_WallBuy";
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/mp40-stonewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
