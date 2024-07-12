package etat.apothicon.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import etat.apothicon.main.Apothicon;

public class TileManager {

    Apothicon ap;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(Apothicon ap) {
        this.ap = ap;

        this.tile = new Tile[10];
        this.mapTileNum = new int[ap.maxScreenCol][ap.maxScreenRow];

        getTileImage();
        loadMap();
    }

    public void loadMap() {
        try {
            String map01 = "src/main/resources/maps/map1.txt";
            FileReader is = new FileReader(map01);
            BufferedReader br = new BufferedReader(is);
            int col = 0;
            int row = 0;
            while (col < ap.maxScreenCol && row < ap.maxScreenRow) {
                String line = br.readLine();
                while (col < ap.maxScreenCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == ap.maxScreenCol) {
                    col = 0;
                    row++;
                }

            }
            br.close();
        } catch (Exception e) {

        }
    }

    public void getTileImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new File("src/main/resources/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File("src/main/resources/tiles/stone.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File("src/main/resources/tiles/snowstone.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while (col < ap.maxScreenCol && row < ap.maxScreenRow) {
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, ap.tileSize, ap.tileSize, null);
            col++;
            x += ap.tileSize;
            if (col == ap.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += ap.tileSize;
            }
        }
    }
}
