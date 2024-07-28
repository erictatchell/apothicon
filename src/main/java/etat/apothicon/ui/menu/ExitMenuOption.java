package etat.apothicon.ui.menu;

import etat.apothicon.main.GameState;

import java.awt.*;

public class ExitMenuOption extends MenuOption {
    public ExitMenuOption(Menu menu, String text, int x, int y) {
        super(menu, text, x, y);
    }

    @Override
    public void action() {
        menu.gm.ap.close();
    }
}