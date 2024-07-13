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
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(Apothicon ap) {
        this.ap = ap;

        this.tile = new Tile[32];
        this.mapTileNum = new int[ap.maxWorldCol][ap.maxWorldRow];

        getTileImage();
        loadMap();
    }

    public void loadMap() {
        try {
            String map01 = "src/main/resources/maps/worldmap.txt";
            FileReader is = new FileReader(map01);
            BufferedReader br = new BufferedReader(is);
            int col = 0;
            int row = 0;
            while (col < ap.maxWorldCol && row < ap.maxWorldRow) {
                String line = br.readLine();
                while (col < ap.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == ap.maxWorldCol) {
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
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File("src/main/resources/tiles/snowstone.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(new File("src/main/resources/tiles/grass-snowy.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(new File("src/main/resources/tiles/grass-barrier.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(new File("src/main/resources/tiles/tree-1.png"));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(new File("src/main/resources/tiles/tree-2.png"));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(new File("src/main/resources/tiles/frozen-tree-1.png"));

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(new File("src/main/resources/tiles/frozen-tree-2.png"));

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(new File("src/main/resources/tiles/grass-zombie.png"));

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(new File("src/main/resources/tiles/water.png"));

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(new File("src/main/resources/tiles/frozen-door.png"));
            //tile[11].collision = true;

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(new File("src/main/resources/tiles/door-barrier.png"));
            tile[12].collision = true;
            
            tile[13] = new Tile();
            tile[13].image = ImageIO.read(new File("src/main/resources/tiles/stone-barrier.png"));
            tile[13].collision = true;

            tile[14] = new Tile();
            tile[14].image = ImageIO.read(new File("src/main/resources/tiles/mossy-stone.png"));
            tile[14].collision = true;

            tile[15] = new Tile();
            tile[15].image = ImageIO.read(new File("src/main/resources/tiles/moss.png"));

            tile[16] = new Tile();
            tile[16].image = ImageIO.read(new File("src/main/resources/tiles/door-barrier.png"));
            tile[16].collision = true;

            tile[17] = new Tile();
            tile[17].image = ImageIO.read(new File("src/main/resources/tiles/snowstone-wall.png"));
            tile[17].collision = true;

            tile[18] = new Tile();
            tile[18].image = ImageIO.read(new File("src/main/resources/tiles/moss.png"));

            tile[19] = new Tile();
            tile[19].image = ImageIO.read(new File("src/main/resources/tiles/mossy-stone.png"));
            tile[19].collision = true;

            tile[20] = new Tile();
            tile[20].image = ImageIO.read(new File("src/main/resources/tiles/dirt.png"));

            tile[21] = new Tile("Juggernog");
            tile[21].image = ImageIO.read(new File("src/main/resources/tiles/jug-snowstonewall.png"));
            tile[21].collision = true;

            tile[22] = new Tile();
            tile[22].image = ImageIO.read(new File("src/main/resources/tiles/m14-stonewall.png"));
            tile[22].collision = true;

            tile[23] = new Tile();
            tile[23].image = ImageIO.read(new File("src/main/resources/tiles/mp40-stonewall.png"));
            tile[23].collision = true;
            

            tile[24] = new Tile("Mule Kick");
            tile[24].image = ImageIO.read(new File("src/main/resources/tiles/mulekick-snowstonewall.png"));
            tile[24].collision = true;

            tile[25] = new Tile();
            tile[25].image = ImageIO.read(new File("src/main/resources/tiles/olympia-stonewall.png"));
            tile[25].collision = true;

            tile[26] = new Tile("Quick Revive");
            tile[26].image = ImageIO.read(new File("src/main/resources/tiles/qr-stonewall.png"));
            tile[26].collision = true;

            tile[27] = new Tile("Speed Cola");
            tile[27].image = ImageIO.read(new File("src/main/resources/tiles/sc-stonewall.png"));
            tile[27].collision = true;

            tile[28] = new Tile();
            tile[28].image = ImageIO.read(new File("src/main/resources/tiles/stakeout-stonewall.png"));
            tile[28].collision = true;

            tile[29] = new Tile("Double Tap 2.0");
            tile[29].image = ImageIO.read(new File("src/main/resources/tiles/dt-stonewall.png"));
            tile[29].collision = true;

            tile[30] = new Tile();
            tile[30].image = ImageIO.read(new File("src/main/resources/tiles/dirt.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        while (worldCol < ap.maxWorldCol && worldRow < ap.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * ap.tileSize;
            int worldY = worldRow * ap.tileSize;
            int screenX = worldX - ap.player.worldX + ap.player.screenX;
            int screenY = worldY - ap.player.worldY + ap.player.screenY;

            if (worldX + ap.tileSize > ap.player.worldX - ap.player.screenX &&
                    worldX - ap.tileSize < ap.player.worldX + ap.player.screenX &&
                    worldY + ap.tileSize > ap.player.worldY - ap.player.screenY &&
                    worldY - ap.tileSize < ap.player.worldY + ap.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, ap.tileSize, ap.tileSize, null);

            }
            worldCol++;
            if (worldCol == ap.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }
    }
}
