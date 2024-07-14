package etat.apothicon.object.weapon.wallbuy;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Olympia_WallBuy extends WallBuy {
    public Olympia_WallBuy() {
        this.price = 500;
        this.name = "Olympia";
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/olympia-stonewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
