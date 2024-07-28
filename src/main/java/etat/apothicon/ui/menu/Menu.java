package etat.apothicon.ui.menu;

import etat.apothicon.main.MediaManager;
import etat.apothicon.main.GameManager;
import etat.apothicon.main.KeyInput;
import etat.apothicon.main.MouseInput;

import javax.swing.*;
import java.awt.*;

public class Menu {
    protected GameManager gm;
    private MouseInput mouseIn;
    private KeyInput keyIn;
    private MenuOption[] options;
    private int hoveredOption;

    public Menu(GameManager gm, MouseInput mouseIn, KeyInput keyIn) {
        this.mouseIn = mouseIn;
        this.keyIn = keyIn;
        this.gm = gm;
        this.options = new MenuOption[5];
        this.options[0] = new PlayMenuOption(this, "Play", gm.ap.screenWidth / 2 - 200, gm.ap.screenHeight / 2);
        this.options[1] = new ExitMenuOption(this, "Exit", gm.ap.screenWidth / 2 - 200, gm.ap.screenHeight / 2 - 50);
        this.hoveredOption = 0;
    }

    public void update() {
        if (keyIn.downPressed) {
            hoveredOption = hoveredOption == 0 ? 1 : hoveredOption - 1;
            keyIn.downPressed = false;
        }
        if (keyIn.upPressed) {
            hoveredOption = hoveredOption == 0 ? hoveredOption + 1 : 0;
            keyIn.upPressed = false;
        }
        if (keyIn.enterPressed) {
            options[hoveredOption].action();
            keyIn.enterPressed = false;
        }
        if (mouseIn.leftMousePressed) {
            Point mousePosition = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(mousePosition, gm.ap);
            System.out.println(mousePosition);
        }
    }

    public void draw(Graphics2D _g2) {
        Graphics2D g2 = MediaManager.antialias(_g2);

        g2.setColor(new Color(150, 0, 0));
        g2.fillRect(0, 0, gm.ap.screenWidth, gm.ap.screenHeight);

        // potential todo is move the fonts to a separet file instead of accessing via hud
        if (gm.hud != null) {
            g2.setFont(gm.hud.fty64);
        }
        g2.setColor(Color.WHITE);
        g2.drawString("Apothicon", gm.ap.screenWidth / 2, gm.ap.screenHeight / 2);
        if (gm.hud != null) {
            g2.setFont(gm.hud.fty16);
        }
        String controlsRaw = "menu controls: W, S, ENTER";

        int padding = (gm.ap.screenWidth / 2 - (controlsRaw.length() * 5));
        g2.drawString(controlsRaw, padding, 24);
        g2.setFont(gm.hud.fty16);
        for (int i = 0; i < options.length; i++) {
            if (options[i] != null) {
                options[i].draw(g2);
                options[i].setHovered(hoveredOption == i);
            }
        }

        g2.dispose();
    }

}
