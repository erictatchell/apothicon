package etat.apothicon.ui.menu.menuOptions;

import etat.apothicon.main.Drawable;
import etat.apothicon.ui.FontManager;
import etat.apothicon.ui.menu.Clickable;
import etat.apothicon.ui.menu.Menu;

import java.awt.*;

public class MenuOption implements Clickable, Drawable {
    public Menu menu;
    public String text;
    protected boolean hovered;
    public int x, y;
    public MenuOption(Menu menu, String text, int x, int y) {
        this.menu = menu;
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public void setHovered(boolean c) {
        hovered = c;
    }

    @Override
    public void action() {
    }

    @Override
    public void draw(Graphics2D g2) {
        if (menu.gm.hud != null) {
            g2.setFont(FontManager.fty32);
        }
        x = menu.getXForCenteredText(g2, text);
        if (hovered) {
            g2.setColor(new Color(255, 40, 40));
        } else g2.setColor(Color.WHITE);
        g2.drawString(this.text, x, y);
    }
}
