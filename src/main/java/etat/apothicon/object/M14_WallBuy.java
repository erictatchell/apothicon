package etat.apothicon.object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class M14_WallBuy extends SuperObject {
    public M14_WallBuy() {
        this.name = "M14_WallBuy";
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/m14-stonewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
