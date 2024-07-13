package etat.apothicon.perk;

import etat.apothicon.entity.Entity;
import etat.apothicon.main.Apothicon;
import etat.apothicon.tile.Tile;

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
    protected Tile machineTile;
    protected String name;
    protected int price;

    public PerkMachine(Apothicon ap, String name, int price) {
        this.ap = ap;
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
    
        return this.name;
    }
}
