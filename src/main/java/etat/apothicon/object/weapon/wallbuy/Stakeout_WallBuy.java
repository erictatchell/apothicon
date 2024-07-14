package etat.apothicon.object.weapon.wallbuy;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Stakeout_WallBuy extends WallBuy {
    public Stakeout_WallBuy() {
        this.price = 1000;
        this.name = "Stakeout";
        try {
            image = ImageIO.read(new File("src/main/resources/tiles/stakeout-stonewall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
