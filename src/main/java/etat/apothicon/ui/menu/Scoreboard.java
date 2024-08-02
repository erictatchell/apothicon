package etat.apothicon.ui.menu;

import etat.apothicon.entity.Player;
import etat.apothicon.main.GameManager;
import etat.apothicon.main.Statistics;

import java.awt.*;
import java.util.ArrayList;

public class Scoreboard {
    private GameManager gm;
    private int x;
    private int y;
    public ArrayList<Statistics> playerStats;
    public Scoreboard(GameManager gm,ArrayList<Player> players) {
        this.gm = gm;
        this.playerStats = new ArrayList<>();
        for (Player player : players) {
            if (player != null) {
                playerStats.add(player.statistics);
            }
        }
    }

    public void drawScoreboard(Graphics2D g2) {
        g2.setFont(gm.hud.fty16);
        for (Statistics stats : playerStats) {

            g2.drawString(Integer.toString(stats.getTotalPoints()), 60, gm.ap.screenHeight / 4);
            g2.drawString(Integer.toString(stats.getKills()), 10, gm.ap.screenHeight / 4);
            g2.drawString(Integer.toString(stats.getHeadshots()), 30, gm.ap.screenHeight / 4);
            g2.drawString(Integer.toString(stats.getShotsFired()), 40, gm.ap.screenHeight / 4);
            g2.drawString(Integer.toString(stats.getDowns()), 50, gm.ap.screenHeight / 4);
            g2.drawString(Integer.toString(stats.getPerksBought()), 60, gm.ap.screenHeight / 4);
        }
    }

}
