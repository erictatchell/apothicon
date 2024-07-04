package etat.apothicon.perk;

import etat.apothicon.entity.Entity;
import etat.apothicon.entity.Player;
import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PerkMachine extends Entity {

    protected BufferedImage machine;
    protected Apothicon ap;
    protected String name;
    protected int price;

    public PerkMachine(Apothicon ap, String name, int price) {
        this.ap = ap;
        this.name = name;
        this.price = price;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(this.machine, this.x, this.y, ap.tileSize, ap.tileSize, null);
    }
}
