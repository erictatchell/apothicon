package etat.apothicon.perk;

import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Perk {
    protected Player player;
    protected Apothicon ap;
    protected BufferedImage icon;
    protected int uses;
    protected int slotX;

    public Perk(Player player, Apothicon ap) {
        this.slotX = player.slotX;
        this.player = player;
        this.ap = ap;

        this.player.perkOffset += 50;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(this.icon, this.slotX, ap.getHeight() - 64, ap.tileSize, ap.tileSize, null);
    }


    public BufferedImage getIcon() {
        return icon;
    }

    public void setIcon(BufferedImage icon) {
        this.icon = icon;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }
}
