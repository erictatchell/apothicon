package etat.apothicon.ui.menu.menuOptions;

import etat.apothicon.ui.menu.Menu;

public class Exit_MenuOption extends MenuOption {
    public Exit_MenuOption(Menu menu, String text, int x, int y) {
        super(menu, text, x, y);
    }

    @Override
    public void action() {
        menu.gm.ap.close();
    }
}