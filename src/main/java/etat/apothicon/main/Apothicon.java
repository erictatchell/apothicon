package etat.apothicon.main;

import etat.apothicon.ai.PathFinder;
import etat.apothicon.entity.Entity;
import etat.apothicon.entity.Player;
import etat.apothicon.hud.HUD;
import etat.apothicon.object.SuperObject;
import etat.apothicon.object.weapon.gun.*;
import etat.apothicon.tile.TileManager;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

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
    public TileManager tileManager = new TileManager(this);

    public CollisionChecker cc = new CollisionChecker(this);

    public SuperObject obj[] = new SuperObject[10];
    public AssetSetter aSetter = new AssetSetter(this);
    public PathFinder pFinder = new PathFinder(this);
    public ArrayList<Bullet> bullets = new ArrayList<>();

    KeyInput keyIn = new KeyInput();
    MouseInput mouseIn = new MouseInput();
    public Player player = new Player(this, keyIn, mouseIn);
    public Entity zombies[] = new Entity[10];
    // JuggernogMachine jug = new JuggernogMachinje(this);
    // QuickReviveMachine qr = new QuickReviveMachine(this);
    // SpeedColaMachine sc = new SpeedColaMachine(this);
    // DoubleTapMachine dt = new DoubleTapMachine(this);
    JLabel info = new JLabel("Text");
    Thread thread;

    public HUD hud = new HUD(this);

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
        aSetter.setZombie();
    }

    // public void drawText(Graphics2D g2) {
    // BufferedImage spl = null;
    // try {

    // spl = ImageIO.read(new File("src/main/resources/rounds/splosh.png"));
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // g2.setColor(Color.white);
    // Font font = new Font("Arial", Font.BOLD, 18);
    // g2.setFont(font);
    // g2.drawString("" + player.loadout.getPoints(), 10, screenHeight - 12);

    // Gun currentWeapon = player.loadout.getCurrentWeapon();
    // String currentWeaponName = currentWeapon.getName();
    // g2.drawImage(spl, screenWidth - spl.getWidth(), screenHeight -
    // spl.getHeight(), null);
    // g2.drawString("" + currentWeaponName, screenWidth -
    // currentWeaponName.length() * 12, screenHeight - 28);
    // g2.drawString("" + currentWeapon.getMagazine() + " / " +
    // currentWeapon.getReserve(), screenWidth - currentWeaponName.length() * 12,
    // screenHeight - 12);

    // }

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
        hud.updateHUD(player.loadout.getCurrentWeapon(), player.loadout.getPoints(), player.loadout.getPerks());
        for (int i = 0; i < zombies.length; i++) {
            if (zombies[i] != null) {
                zombies[i].update();
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i) != null && bullets.get(i).alive) {
                bullets.get(i).update();
            } else if (!bullets.get(i).alive) {
                bullets.remove(i);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        hud.draw(g2);
        for (int i = 0; i < zombies.length; i++) {
            if (zombies[i] != null) {
                zombies[i].draw(g2);
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i) != null) {
                bullets.get(i).drawBullet(g2);
            }
        }
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);

            }
        }

        player.draw(g2);
        // drawText(g2);
        g2.dispose();
    }
}
