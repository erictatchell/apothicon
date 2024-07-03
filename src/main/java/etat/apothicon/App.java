package etat.apothicon;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        final int width = 1080;
        final int height = 540;

        JFrame frame = new JFrame("Apothicon");
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Apothicon ap = new Apothicon();
        frame.add(ap);
        frame.pack();
        frame.setVisible(true);
    }
}
