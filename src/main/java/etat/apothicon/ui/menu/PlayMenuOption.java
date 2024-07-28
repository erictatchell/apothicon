package etat.apothicon.ui.menu;

import etat.apothicon.main.GameState;

import java.awt.*;

public class PlayMenuOption extends MenuOption {
    public PlayMenuOption(Menu menu, String text, int x, int y) {
        super(menu, text, x, y);
    }

    @Override
    public void action() {
        menu.gm.startGame();
    }
}
