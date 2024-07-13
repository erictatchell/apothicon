package etat.apothicon.object;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Olympia_WallBuy extends SuperObject {
    public Olympia_WallBuy() {
        this.name = "Olympia_WallBuy";
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/olympia-stonewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
