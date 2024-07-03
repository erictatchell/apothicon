package etat.apothicon.main;

import etat.apothicon.entity.Player;

import javax.swing.*;
import java.awt.*;

public class Apothicon extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    public final int tileSize = originalTileSize * scale;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;
    String drawFPS = "FPS: " + FPS;

    KeyInput keyIn = new KeyInput();
    Player player = new Player(this, keyIn);
    Thread thread;

    Apothicon() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyIn);
        this.setFocusable(true);
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

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }
}
