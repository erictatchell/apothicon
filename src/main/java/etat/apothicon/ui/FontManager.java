package etat.apothicon.ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontManager {
    public static void loadFonts() {
        try {
            fty16 = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/fty.ttf")).deriveFont(16.0f);
            fty12 = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/fty.ttf")).deriveFont(12.0f);
            fty20 = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/fty.ttf")).deriveFont(20.0f);
            fty24 = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/fty.ttf")).deriveFont(24.0f);
            fty32 = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/fty.ttf")).deriveFont(32.0f);
            fty64 = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/fty.ttf")).deriveFont(64.0f);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
    public static Font arial16 = new Font("Arial", Font.BOLD, 16);
    public static Font arial12 = new Font("Arial", Font.BOLD, 12);
    public static Font arial64 = new Font("Arial", Font.BOLD, 64);
    public static Font fty12;
    public static Font fty16;
    public static Font fty20;
    public static Font fty24;
    public static Font fty32;
    public static Font fty64;

}
