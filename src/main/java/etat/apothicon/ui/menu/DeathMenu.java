package etat.apothicon.ui.menu;

import etat.apothicon.entity.Player;
import etat.apothicon.main.GameManager;
import etat.apothicon.main.KeyInput;
import etat.apothicon.ui.FontManager;
import etat.apothicon.utility.MediaManager;
import etat.apothicon.main.MouseInput;
import etat.apothicon.ui.menu.menuOptions.MainMenu_MenuOption;
import etat.apothicon.ui.menu.menuOptions.Play_MenuOption;

import java.awt.*;
import java.util.ArrayList;

public class DeathMenu extends Menu {

    Scoreboard scoreboard;

    public DeathMenu(GameManager gm, MouseInput mouseIn, KeyInput keyIn) {
        super(gm, mouseIn, keyIn);
        ArrayList<Player> players = new ArrayList<>();
        players.add(gm.player);
        System.out.println(players);
        this.scoreboard = new Scoreboard(gm, players);
        this.options[0] = new Play_MenuOption(this, "restart", 10, gm.ap.screenHeight / 2 + 100);
        this.options[1] = new MainMenu_MenuOption(this, "main menu", 10, gm.ap.screenHeight / 2 + 140);
    }

    public void draw(Graphics2D _g2) {
        Graphics2D g2 = MediaManager.antialias(_g2);
        g2.setColor(new Color(150, 0, 0, 150));
        g2.fillRect(0, 0, gm.ap.screenWidth , gm.ap.screenHeight);

        scoreboard.drawScoreboard(g2);
        g2.setFont(FontManager.fty64);
        g2.setColor(Color.WHITE);
        g2.drawString("game over", getXForCenteredText(g2, "game over"), gm.ap.screenHeight / 4);
        g2.setFont(FontManager.fty32);
        String survived = "you survived " + gm.roundManager.getCurrentRound() + " rounds";
        g2.drawString(survived, getXForCenteredText(g2, survived), gm.ap.screenHeight / 4 + 40);
        super.draw(g2);

    }

}
