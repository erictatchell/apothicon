package etat.apothicon.object.weapon.wallbuy;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class M14_WallBuy extends WallBuy{
    public M14_WallBuy() {
        this.price = 500;
        this.name = "M14";
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/m14-stonewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
