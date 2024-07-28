package etat.apothicon.main;

import etat.apothicon.ai.PathFinder;
import etat.apothicon.entity.Entity;
import etat.apothicon.entity.Player;
import etat.apothicon.ui.HUD;
import etat.apothicon.object.SuperObject;
import etat.apothicon.object.weapon.gun.Bullet;
import etat.apothicon.round.RoundManager;
import etat.apothicon.round.Zone;
import etat.apothicon.round.ZoneManager;
import etat.apothicon.tile.TileManager;
import etat.apothicon.ui.menu.DeathMenu;
import etat.apothicon.ui.menu.MainMenu;
import etat.apothicon.ui.menu.Menu;

import java.awt.*;
import java.util.ArrayList;

public class GameManager {
    public GameState gameState;
    public boolean dead = false;
    final Object spawnLock = new Object();
    public Apothicon ap;
    public Player player;
    public ArrayList<Bullet> bullets;
    public SuperObject obj[];
    public HUD hud;
    public MainMenu mainMenu;
    public DeathMenu deathMenu;
    public ZoneManager zoneManager;
    public RoundManager roundManager;
    public TileManager tileManager;
    public CollisionChecker cc;
    public AssetSetter aSetter;
    public PathFinder pFinder;

    public GameManager(Apothicon ap) {
        this.ap = ap;
    }

    public void setup() {

        gameState = GameState.MAIN_MENU;
        mainMenu = new MainMenu(this, ap.mouseIn, ap.keyIn);
        deathMenu = new DeathMenu(this, ap.mouseIn, ap.keyIn);

        tileManager = new TileManager(ap);
        player = new Player(ap, ap.keyIn, ap.mouseIn);
        bullets = new ArrayList<>();
        obj = new SuperObject[10];
        zoneManager = new ZoneManager(ap);
        roundManager = new RoundManager(ap);
        hud = new HUD(ap);
        cc = new CollisionChecker(ap);
        aSetter = new AssetSetter(ap);
        pFinder = new PathFinder(ap);
        aSetter.setObject();

    }


    public void update() {
        switch (gameState) {
            case PLAYING -> {
                if (!dead) {
                    player.update();
                    hud.updateHUD(player.loadout.getCurrentWeapon(), player.loadout.getPoints(), player.loadout.getPerks());

                    roundManager.update(zoneManager.currentZone);
                    for (int i = 0; i < bullets.size(); i++) {
                        Bullet bullet = bullets.get(i);
                        if (bullet != null) {
                            if (bullet.alive) {
                                bullet.update();
                            } else {
                                bullets.remove(bullet);
                            }
                        }
                    }
                } else {
                    deathMenu.update();
                }
                for (Entity zombie : roundManager.getZombies()) {
                    if (zombie != null) {
                        zombie.update();
                    }
                }
            }
            case MAIN_MENU -> mainMenu.update();
        }

    }

    public void startGame() {
        gameState = GameState.PLAYING;
    }

    public void resetGame() {
        dead = false;
        player.loadout.reset();
        player.reset();
        roundManager.reset();
        // reset doors, map progression etc

    }

    public void quitToMainMenu() {
        resetGame();
        gameState = GameState.MAIN_MENU;
    }


    public void draw(Graphics2D g2) {
        switch (gameState) {
            case DEATH_MENU -> deathMenu.draw(g2);
            case MAIN_MENU -> mainMenu.draw(g2);
            case PLAYING -> {

                tileManager.draw(g2);
                for (Zone zone : zoneManager.zones) {
                    zone.draw(g2, ap);
                }
                for (int i = 0; i < bullets.size(); i++) {
                    if (bullets.get(i) != null) {
                        bullets.get(i).drawBullet(g2);
                    }
                }
                for (int i = 0; i < obj.length; i++) {
                    if (obj[i] != null) {
                        obj[i].draw(g2, ap);
                    }
                }
                for (Entity zombie : roundManager.getZombies()) {
                    if (zombie != null) {
                        zombie.draw(g2);
                        if (dead) zombie.onPath = false;
                    }
                }
                if (player.loadout.getHealth() <= 0) {
                    dead = true;
                    deathMenu.draw(g2);
                } else {

                    player.draw(g2);
                    hud.draw(g2);
                }
            }
        }
    }
}
