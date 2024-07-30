
package etat.apothicon.main;

import etat.apothicon.sound.SoundHandler;
import etat.apothicon.sound.SoundType;

import java.awt.*;
import java.awt.event.WindowEvent;
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
    Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public GameManager gameManager;

    // TODO: oop-ify these variables
    int FPS = 60;
    String drawFPS = "FPS: " + FPS;
    Thread thread;
    Thread soundThread;
    SoundHandler sound = new SoundHandler();

    public KeyInput keyIn = new KeyInput();
    public MouseInput mouseIn = new MouseInput();

    Apothicon() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyIn);
        this.addMouseListener(mouseIn);
        this.setFocusable(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src/main/resources/crosshair/default.png");
        Cursor c = toolkit.createCustomCursor(image , new Point(8, 8), "img");
        this.setCursor (c);
    }

    public void setup() {

        gameManager = new GameManager(this);
        gameManager.setup();
    }


    public void drawText(Graphics2D g2, String text, String price) {
        g2.setColor(Color.white);
        Font font = new Font("Arial", Font.BOLD, 24);
        g2.setFont(font);

        g2.drawString("Press F to buy " + text + " (" + price + ")", screenWidth / 2, screenHeight / 2);
    }

    public void start() {
        thread = new Thread(this);
        soundThread = new Thread(sound);
        soundThread.start();
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
        gameManager.update();
    }

    public void playSE(int i, SoundType t) {
        SoundHandler.consolidate();
        SoundHandler.play(i, t);
//        sound.play();

    }

    public void close() {
        App.close();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        gameManager.draw(g2);
        g2.dispose();
    }
}
