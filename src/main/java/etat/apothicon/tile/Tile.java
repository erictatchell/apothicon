package etat.apothicon.tile;
import java.awt.image.BufferedImage;

public class Tile {

    public BufferedImage image;
    public boolean collision = false;
    public String id;

    public Tile(String id) {
        this.id = id;   
    }
    public String getTileID() {
        return this.id;
    }
    public Tile() {}
}
