package etat.apothicon.object.weapon.wallbuy;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MP40_WallBuy extends WallBuy{
    public MP40_WallBuy() {
        this.price = 1000;
        this.name = "MP40";
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/mp40-stonewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
