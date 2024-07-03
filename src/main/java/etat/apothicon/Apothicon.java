package etat.apothicon;

import javax.swing.*;
import java.awt.*;

public class Apothicon extends JPanel {
    final int width = 1080;
    final int height = 540;
    Apothicon() {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
    }
}
