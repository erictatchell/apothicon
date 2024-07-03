package etat.apothicon;

import javax.swing.*;
import java.awt.*;

public class Apothicon extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int fps = 60;

    KeyInput keyIn = new KeyInput();
    Player player;
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
        player = new Player(100, 100, 5);
        thread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / fps;
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
                System.out.printf("FPS: %d", drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (keyIn.upPressed) {
            player.setY(player.getY() - player.getSpeed());
        } else if (keyIn.downPressed) {
            player.setY(player.getY() + player.getSpeed());
        } else if (keyIn.leftPressed) {
            player.setX(player.getX() - player.getSpeed());
        } else if (keyIn.rightPressed) {
            player.setX(player.getX() + player.getSpeed());
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(player.getX(), player.getY(), tileSize, tileSize);

        g2.dispose();
    }
}
