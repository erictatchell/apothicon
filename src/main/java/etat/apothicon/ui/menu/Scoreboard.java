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
        init(players);
        System.out.println(players.size());
    }

    public void init(ArrayList<Player> players) {
        for (Player player : players) {
            if (player != null) {
                playerStats.add(player.statistics);
            }
        }
    }


    public void drawScoreboard(Graphics2D g2) {
        // Set the font and color for the scoreboard
        g2.setFont(gm.hud.fty16);
        g2.setColor(Color.WHITE);

        // Define the x coordinates for each column
        int xMargin = gm.ap.screenWidth / 5;
        int pointsX = xMargin;
        int killsX = pointsX + 90;
        int headshotsX = killsX + 90;
        int shotsFiredX = headshotsX + 90;
        int downsX = shotsFiredX + 90;
        int perksBoughtX = downsX + 90;

        // Define the y coordinate for the headers and the start of the stats
        int headerY = gm.ap.screenHeight / 2 - 20;
        int statsStartY = gm.ap.screenHeight / 2;
        int statsOffsetY = 20; // Vertical space between each player's stats

        // Draw the headers
        g2.drawString("Points", pointsX, headerY);
        g2.drawString("Kills", killsX, headerY);
        g2.drawString("Headshots", headshotsX, headerY);
        g2.drawString("Shots Fired", shotsFiredX, headerY);
        g2.drawString("Downs", downsX, headerY);
        g2.drawString("Perks Bought", perksBoughtX, headerY);

        // Draw the player statistics
        int currentY = statsStartY;
        for (Statistics stats : playerStats) {
            g2.drawString(Integer.toString(stats.getTotalPoints()), pointsX, currentY);
            g2.drawString(Integer.toString(stats.getKills()), killsX, currentY);
            g2.drawString(Integer.toString(stats.getHeadshots()), headshotsX, currentY);
            g2.drawString(Integer.toString(stats.getShotsFired()), shotsFiredX, currentY);
            g2.drawString(Integer.toString(stats.getDowns()), downsX, currentY);
            g2.drawString(Integer.toString(stats.getPerksBought()), perksBoughtX, currentY);

            // Move to the next line for the next player's stats
            currentY += statsOffsetY;
        }
    }


}
