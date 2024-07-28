package etat.apothicon.ui.menu.menuOptions;

import etat.apothicon.ui.menu.Menu;

public class MainMenu_MenuOption extends MenuOption {
    public MainMenu_MenuOption(Menu menu, String text, int x, int y) {
        super(menu, text, x, y);
    }

    @Override
    public void action() {
        menu.gm.quitToMainMenu();
    }
}
