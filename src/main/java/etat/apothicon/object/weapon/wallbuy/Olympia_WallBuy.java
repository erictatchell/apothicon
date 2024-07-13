package etat.apothicon.object.weapon.wallbuy;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import etat.apothicon.object.SuperObject;

public class Olympia_WallBuy extends SuperObject {
    public Olympia_WallBuy() {
        this.price = 500;
        this.name = "Olympia_WallBuy";
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/olympia-stonewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
