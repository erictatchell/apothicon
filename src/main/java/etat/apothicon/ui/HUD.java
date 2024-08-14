package etat.apothicon.ui;

import etat.apothicon.utility.MediaManager;
import etat.apothicon.main.Apothicon;
import etat.apothicon.object.perk.bottle.Perk;
import etat.apothicon.object.weapon.gun.Gun;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class HUD {
    private Apothicon ap;
    private BufferedImage gunSplosh;
    private BufferedImage pointSplosh;
    private ArrayList<Perk> perks;
    private String points;
    private String currentWeaponName;

    private String currentWeaponMagazine;
    private String currentWeaponReserve;



    public HUD(Apothicon ap) {
        this.ap = ap;
        setup();
    }

    private void setup() {
        this.currentWeaponMagazine = Integer.toString(ap.gameManager.player.getLoadout().getCurrentWeapon().getMagazine());
        this.currentWeaponReserve = Integer.toString(ap.gameManager.player.getLoadout().getCurrentWeapon().getReserve());
        this.perks = ap.gameManager.player.getLoadout().getPerks();
        try {
            gunSplosh = ImageIO.read(new File("src/main/resources/rounds/splosh.png"));
            pointSplosh = ImageIO.read(new File("src/main/resources/blood/pointSplosh.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateHUD(Gun gun, int points, ArrayList<Perk> perks) {
        this.currentWeaponName = gun.getName();
        this.currentWeaponMagazine = Integer.toString(gun.getMagazine());
        this.currentWeaponReserve = Integer.toString(gun.getReserve());
        this.points = Integer.toString(points);
        this.perks = perks;
    }

    public void draw(Graphics2D _g2) {
        for (Perk perk : perks) {
            if (perk != null) {
                perk.draw(_g2);
            }
        }

        Graphics2D g2 = MediaManager.antialias(_g2);
        g2.drawImage(pointSplosh,
                ap.screenWidth - (pointSplosh.getWidth() * 3),
                ap.screenHeight - (pointSplosh.getHeight() * 6),
                ap.tileSize * 2,
                ap.tileSize,
                null);

        g2.drawImage(gunSplosh,
                ap.screenWidth - (gunSplosh.getWidth() * 3),
                ap.screenHeight - (gunSplosh.getHeight() * 3),
                ap.tileSize * 3,
                ap.tileSize,
                null);


        // chatgpt assisted code lines 92 through 132

        // Set color and font for drawing text
        g2.setColor(Color.white);
        g2.setFont(FontManager.fty16);

        // Calculate x-coordinate for right-aligning currentWeaponName
        String ammo = currentWeaponMagazine + " / " + currentWeaponReserve;
        int textWidth = g2.getFontMetrics().stringWidth(currentWeaponName);
        int xCurrentWeaponName = ap.screenWidth - textWidth - 24; // Adjusted for padding

        // Draw currentWeaponName
        g2.drawString(currentWeaponName,
                xCurrentWeaponName,
                ap.screenHeight - 28);

        g2.setFont(FontManager.fty16);
        int ammoTextWidth = (g2.getFontMetrics().stringWidth(ammo));
        int xCurrentWeaponAmmo = ap.screenWidth - ammoTextWidth - 24;

        g2.drawString(currentWeaponMagazine,
                xCurrentWeaponAmmo,
                ap.screenHeight - 14);

        g2.drawString(ammo,
                xCurrentWeaponAmmo,
                ap.screenHeight - 14);

        g2.drawString(ap.gameManager.zoneManager.currentZone.getName(), 10, 30);
        g2.drawString(Integer.toString(ap.gameManager.player.getLoadout().getHealth()), 10, 100);


        int pointTextWidth = (g2.getFontMetrics().stringWidth(points));
        int xPoints = ap.screenWidth - pointTextWidth - 24;

        // Draw points text aligned to the left
        g2.drawString(points,
                xPoints,
                ap.screenHeight - 65);

        g2.setFont(FontManager.fty64);
        g2.setColor(new Color(150, 0, 0));
        g2.drawString(Integer.toString(ap.gameManager.roundManager.getCurrentRound()), 10, ap.screenHeight - 10);

        g2.dispose();
    }
    public int getXForCenteredText(Graphics2D g2, String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return ap.screenWidth / 2 - length / 2;
    }

    // chatgpt helped with translucent black rectangle
    public void drawPurchaseText(Graphics2D g2, String purchaseString) {
        if (purchaseString != null) {
            // Set the font and get the font metrics
            g2.setFont(FontManager.fty24);
            FontMetrics fm = g2.getFontMetrics();

            // Calculate the position for the text
            int x = getXForCenteredText(g2, purchaseString);
            int y = ap.screenHeight - ap.screenHeight / 4;

            // Calculate the size of the rectangle based on the text and font size
            int padding = 10; // padding around the text
            int textWidth = fm.stringWidth(purchaseString);
            int textHeight = fm.getAscent() - fm.getDescent(); // Approximate height based on font size

            // Save the current composite
            Composite originalComposite = g2.getComposite();

            // Set the composite for semi-transparent drawing
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 0.5f means 50% transparency

            // Set the color to black for the rectangle
            g2.setColor(Color.BLACK);

            // Draw the rectangle behind the text
            g2.fillRect(x - padding, y - 2 * textHeight, textWidth + 2 * padding, textHeight + 2 * padding);

            // Restore the original composite
            g2.setComposite(originalComposite);

            // Draw the text
            g2.setColor(Color.WHITE);
            g2.drawString(purchaseString, x, y);
        }
    }


}
