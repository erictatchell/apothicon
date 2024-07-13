package etat.apothicon.main;

import etat.apothicon.perk.*;
import etat.apothicon.tile.TileManager;
import etat.apothicon.entity.Gun;
import etat.apothicon.entity.Player;
import etat.apothicon.object.DoubleTapMachine;
import etat.apothicon.object.JuggernogMachine;
import etat.apothicon.object.QuickReviveMachine;
import etat.apothicon.object.SpeedColaMachine;
import etat.apothicon.object.SuperObject;

import javax.swing.*;
import java.awt.*;

public class Apothicon extends JPanel implements Runnable {
    public final int originalTileSize = 16;
    public final int scale = 3;
    public final int maxScreenCol = 18;
    public final int maxScreenRow = 12;
    public final int tileSize = originalTileSize * scale;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // world settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // TODO: oop-ify these variables
    int FPS = 60;
    String drawFPS = "FPS: " + FPS;
    TileManager tileManager = new TileManager(this);

    public CollisionChecker cc = new CollisionChecker(this);

    public SuperObject obj[] = new SuperObject[10];
    public AssetSetter aSetter = new AssetSetter(this);

    KeyInput keyIn = new KeyInput();
    MouseInput mouseIn = new MouseInput();
    public Player player = new Player(this, keyIn, mouseIn);
    // JuggernogMachine jug = new JuggernogMachinje(this);
    // QuickReviveMachine qr = new QuickReviveMachine(this);
    // SpeedColaMachine sc = new SpeedColaMachine(this);
    // DoubleTapMachine dt = new DoubleTapMachine(this);
    JLabel info = new JLabel("Text");
    Thread thread;

    Apothicon() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyIn);
        this.addMouseListener(mouseIn);
        this.setFocusable(true);
    }

    public void setup() {
        aSetter.setObject();

    }

    public void drawText(Graphics2D g2) {
        g2.setColor(Color.white);
        Font font = new Font("Arial", Font.BOLD, 18);
        g2.setFont(font);
        g2.drawString("" + player.getPoints(), 10, screenHeight - 12);
        g2.drawString("health: " + player.getHealth(), 10, 40);
        g2.drawString("reloadRate: " + player.getReloadRate(), 10, 60);
        g2.drawString("maxGunNum: " + player.getGunNum(), 10, 80);
        g2.drawString("revives: " + player.getRevives(), 10, 100);

        Gun currentWeapon = player.getCurrentWeapon();
        String currentWeaponName = currentWeapon.getName();
        g2.drawString("" + currentWeaponName, screenWidth / 2, screenHeight - 28);
        g2.drawString("" + currentWeapon.getMagazine() + " / " + currentWeapon.getReserve(), screenWidth / 2,
                screenHeight - 12);

    }

    public void drawText(Graphics2D g2, String text, String price) {
        g2.setColor(Color.white);
        Font font = new Font("Arial", Font.BOLD, 24);
        g2.setFont(font);

        g2.drawString("Press F to buy " + text + " (" + price + ")", screenWidth / 2, screenHeight / 2);
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (thread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    // public QuickReviveMachine getQuickRevive() {
    // return qr;
    // }

    // public SpeedColaMachine getSpeedCola() {
    // return sc;
    // }

    // public DoubleTapMachine getDoubleTap() {
    // return dt;
    // }

    // public JuggernogMachine getJug() {
    // return jug;
    // }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);

            }
        }
        player.draw(g2);
        drawText(g2);
        g2.dispose();
    }
}
