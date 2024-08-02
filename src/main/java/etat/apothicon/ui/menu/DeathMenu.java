package etat.apothicon.ui.menu;

import etat.apothicon.main.GameManager;
import etat.apothicon.main.KeyInput;
import etat.apothicon.main.MediaManager;
import etat.apothicon.main.MouseInput;
import etat.apothicon.ui.menu.menuOptions.MainMenu_MenuOption;
import etat.apothicon.ui.menu.menuOptions.Play_MenuOption;

import java.awt.*;

public class DeathMenu extends Menu {

    public DeathMenu(GameManager gm, MouseInput mouseIn, KeyInput keyIn) {
        super(gm, mouseIn, keyIn);
        this.options[0] = new Play_MenuOption(this, "restart", 10, gm.ap.screenHeight / 2 + 100);
        this.options[1] = new MainMenu_MenuOption(this, "main menu", 10, gm.ap.screenHeight / 2 + 140);
    }

    public void draw(Graphics2D _g2) {
        Graphics2D g2 = MediaManager.antialias(_g2);
        g2.setColor(new Color(150, 0, 0, 100));
        g2.fillRect(0, 0, gm.ap.screenWidth , gm.ap.screenHeight);

        g2.setFont(gm.hud.fty64);
        g2.setColor(Color.WHITE);
        g2.drawString("game over", getXForCenteredText(g2, "game over"), gm.ap.screenHeight / 2);
        g2.setFont(gm.hud.fty32);
        String survived = "you survived " + gm.roundManager.getCurrentRound() + " rounds";
        g2.drawString(survived, getXForCenteredText(g2, survived), gm.ap.screenHeight / 2 + 40);
        super.draw(g2);

    }

}
