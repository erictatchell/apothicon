package etat.apothicon;

import javax.swing.*;

public class App {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Apothicon");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Apothicon ap = new Apothicon();
        frame.add(ap);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        ap.start();
    }
}
