package etat.apothicon.main;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class App {
    public static JFrame frame;
    public static void main(String[] args) {

        frame = new JFrame("Apothicon");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Apothicon ap = new Apothicon();
        frame.add(ap);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        ap.setup();
        ap.start();
    }

    public static void close() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
