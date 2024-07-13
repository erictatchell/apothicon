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
    protected int perkID;
    protected String name;

    public Perk(String name, Player player, Apothicon ap) {
        this.slotX = player.getSlotX();
        this.player = player;
        this.ap = ap;
        this.name = name;
        this.perkID = player.getPerks().size();
        this.player.incrementPerkOffset();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(this.icon, this.slotX, ap.getHeight() - 64, ap.tileSize - 16, ap.tileSize - 16, null);
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

    public String getName() {
        return this.name;
    }
}
