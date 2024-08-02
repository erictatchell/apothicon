package etat.apothicon.ui.menu;

import etat.apothicon.main.MediaManager;
import etat.apothicon.main.GameManager;
import etat.apothicon.main.KeyInput;
import etat.apothicon.main.MouseInput;
import etat.apothicon.ui.menu.menuOptions.MenuOption;

import javax.swing.*;
import java.awt.*;

public class Menu {
    public GameManager gm;
    private MouseInput mouseIn;
    private KeyInput keyIn;
    protected MenuOption[] options;
    protected int hoveredOption;

    public Menu(GameManager gm, MouseInput mouseIn, KeyInput keyIn) {
        this.mouseIn = mouseIn;
        this.keyIn = keyIn;
        this.gm = gm;
        this.options = new MenuOption[5];
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

    public int getXForCenteredText(Graphics2D g2, String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gm.ap.screenWidth / 2 - length / 2;
    }

    public void draw(Graphics2D _g2) {
        Graphics2D g2 = MediaManager.antialias(_g2);


        // potential todo is move the fonts to a separet file instead of accessing via hud
        if (gm.hud != null) {

            g2.setFont(gm.hud.fty16);
        }
        for (int i = 0; i < options.length; i++) {
            if (options[i] != null) {
                options[i].draw(g2);
                options[i].setHovered(hoveredOption == i);
            }
        }

        g2.dispose();
    }

}
