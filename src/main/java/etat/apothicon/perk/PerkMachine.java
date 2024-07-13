package etat.apothicon.perk;

import etat.apothicon.entity.Entity;
import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * TODO:
 * not a fan of this extending Entity, it should extend
 * a tile class or something. will revisit
 */
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
        g2.drawImage(this.machine, this.worldX, this.worldY, ap.tileSize, ap.tileSize, null);
    }

    public int getPrice() {
        return price;
    }

    public void render(String url) {
        try {
            this.machine = ImageIO.read(new File("src/main/resources/perks/" + url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
    
        return this.name;
    }
}
