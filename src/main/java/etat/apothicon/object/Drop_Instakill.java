package etat.apothicon.object;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Drop_Instakill extends Drop {
    private float defaultDamageMultiplier;
    public Drop_Instakill(int objIndex, int worldX, int worldY, Apothicon ap, DropType type) {
        super(objIndex, worldX, worldY, ap, type);
        try {
            image = ImageIO.read(new File("src/main/resources/drops/instakill.png"));
            dropIcon = ImageIO.read(new File("src/main/resources/drops/instakillicon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activate() {
        if (!ap.gameManager.dropManager.instaKillActive) {
            defaultDamageMultiplier = ap.gameManager.player.loadout.getDamageMultiplier();
            ap.gameManager.player.loadout.setDamageMultiplier(-1.0f);
            ap.gameManager.dropManager.instaKillActive = true;

            super.activate();
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        ap.gameManager.player.loadout.setDamageMultiplier(defaultDamageMultiplier);
        ap.gameManager.dropManager.instaKillActive = false;
    }
}
