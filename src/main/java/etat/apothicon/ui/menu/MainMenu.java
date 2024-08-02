package etat.apothicon.ui.menu;

import etat.apothicon.main.GameManager;
import etat.apothicon.main.KeyInput;
import etat.apothicon.main.MouseInput;
import etat.apothicon.ui.menu.menuOptions.Exit_MenuOption;
import etat.apothicon.ui.menu.menuOptions.Play_MenuOption;

import java.awt.*;

public class MainMenu extends Menu {
    public MainMenu(GameManager gm, MouseInput mouseIn, KeyInput keyIn) {
        super(gm, mouseIn, keyIn);
        this.options[0] = new Play_MenuOption(this, "Play", 0, gm.ap.screenHeight / 2);
        this.options[1] = new Exit_MenuOption(this, "Exit",0, gm.ap.screenHeight / 2 + 50);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(150, 0, 0));
        g2.fillRect(0, 0, gm.ap.screenWidth, gm.ap.screenHeight);
        if (gm.hud != null) {
            g2.setFont(gm.hud.fty64);
        }
        g2.setColor(Color.WHITE);
        g2.drawString("Apothicon", getXForCenteredText(g2, "Apothicon"), gm.ap.screenHeight / 4);
        super.draw(g2);
    }
}
