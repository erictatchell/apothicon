package etat.apothicon.hud;

import etat.apothicon.main.Apothicon;
import etat.apothicon.object.perk.bottle.Perk;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HUD {
    private Apothicon ap;
    private BufferedImage gunSplosh;
    private Perk[] perks;
    private String points;
    private String currentWeaponName;
    private String currentWeaponAmmo;

    public HUD() {
        setup();
    }
    
    private void setup() {
        try {
            gunSplosh = ImageIO.read(new File("src/main/resources/rounds/splosh.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2) {
        g2.drawImage(gunSplosh, ap.screenWidth - gunSplosh.getWidth(), ap.screenHeight - gunSplosh.getHeight(), null);
    }

}
