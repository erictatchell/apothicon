package etat.apothicon.main;

import etat.apothicon.perk.*;
import etat.apothicon.entity.Player;

import javax.swing.*;
import java.awt.*;

public class Apothicon extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    final int maxScreenCol = 18;
    final int maxScreenRow = 12;
    public final int tileSize = originalTileSize * scale;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;
    String drawFPS = "FPS: " + FPS;

    KeyInput keyIn = new KeyInput();
    Player player = new Player(this, keyIn);
    JuggernogMachine jug = new JuggernogMachine(this);
    QuickReviveMachine qr = new QuickReviveMachine(this);
    SpeedColaMachine sc = new SpeedColaMachine(this);
    JLabel info = new JLabel("Text");
    Thread thread;

    public JuggernogMachine getJug() {
        return jug;
    }

    Apothicon() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyIn);
        this.setFocusable(true);
    }

    public void drawText(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.drawString("" + player.getPoints(), 10, 20);
        g2.drawString("+ " + player.getHealth(), 10, 30);
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

    public QuickReviveMachine getQuickRevive() {
        return qr;
    }

    public SpeedColaMachine getSpeedCola() {
        return sc;
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        jug.draw(g2);
        qr.draw(g2);
        sc.draw(g2);
        player.draw(g2);
        drawText(g2);
        g2.dispose();
    }
}
