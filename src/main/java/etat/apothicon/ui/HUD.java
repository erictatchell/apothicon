package etat.apothicon.ui;

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

    public Font arial16 = new Font("Arial", Font.BOLD, 16);
    public Font arial12 = new Font("Arial", Font.BOLD, 12);
    public Font arial64 = new Font("Arial", Font.BOLD, 64);
    public Font fty12;
    public Font fty16;
    public Font fty24;

    public Font fty32;
    public Font fty64;


    public HUD(Apothicon ap) {
        this.ap = ap;
        setup();
    }

    private void setup() {
        this.currentWeaponMagazine = Integer.toString(ap.gameManager.player.loadout.getCurrentWeapon().getMagazine());
        this.currentWeaponReserve = Integer.toString(ap.gameManager.player.loadout.getCurrentWeapon().getReserve());
        this.perks = ap.gameManager.player.loadout.getPerks();
        try {
            fty16 = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/fty.ttf")).deriveFont(16.0f);
            fty12 = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/fty.ttf")).deriveFont(12.0f);
            fty32 = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/fty.ttf")).deriveFont(32.0f);
            fty24 = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/fty.ttf")).deriveFont(24.0f);
            fty64 = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/font/fty.ttf")).deriveFont(64.0f);
            gunSplosh = ImageIO.read(new File("src/main/resources/rounds/splosh.png"));
            pointSplosh = ImageIO.read(new File("src/main/resources/blood/pointSplosh.png"));
        } catch (FontFormatException | IOException e) {
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

    public void draw(Graphics2D g2) {
        for (Perk perk : perks) {
            if (perk != null) {
                perk.draw(g2);
            }
        }
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

        // Set color and font for drawing text
        g2.setColor(Color.white);
        g2.setFont(fty16);

        // Calculate x-coordinate for right-aligning currentWeaponName
        String ammo = currentWeaponMagazine + " / " + currentWeaponReserve;
        int textWidth = g2.getFontMetrics().stringWidth(currentWeaponName);
        int xCurrentWeaponName = ap.screenWidth - textWidth - 24; // Adjusted for padding

        // Draw currentWeaponName
        g2.drawString(currentWeaponName,
                xCurrentWeaponName,
                ap.screenHeight - 28);

        g2.setFont(fty16);
        int ammoTextWidth = (g2.getFontMetrics().stringWidth(ammo));
        int xCurrentWeaponAmmo = ap.screenWidth - ammoTextWidth - 24;

        g2.drawString(currentWeaponMagazine,
                xCurrentWeaponAmmo,
                ap.screenHeight - 14);

        g2.drawString(ammo,
                xCurrentWeaponAmmo,
                ap.screenHeight - 14);

        g2.drawString(ap.gameManager.zoneManager.currentZone.getName(), 10, 30);
        g2.drawString(Integer.toString(ap.gameManager.player.loadout.getHealth()), 10, 100);


        int pointTextWidth = (g2.getFontMetrics().stringWidth(points));
        int xPoints = ap.screenWidth - pointTextWidth - 24;

        // Draw points text aligned to the left
        g2.drawString(points,
                xPoints,
                ap.screenHeight - 65);

        g2.setFont(fty64);
        g2.setColor(new Color(150, 0, 0));
        g2.drawString(Integer.toString(ap.gameManager.roundManager.getCurrentRound()), 10, ap.screenHeight - 10);
    }

}
