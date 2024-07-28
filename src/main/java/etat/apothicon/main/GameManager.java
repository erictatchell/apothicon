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
import etat.apothicon.ui.menu.Menu;

import java.awt.*;
import java.util.ArrayList;

public class GameManager {
    public GameState gameState;
    final Object spawnLock = new Object();
    public Apothicon ap;
    public Player player;
    public ArrayList<Bullet> bullets;
    public SuperObject obj[];
    public HUD hud;
    public Menu menu;

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

        gameState = GameState.MENU;
        menu = new Menu(this, ap.mouseIn, ap.keyIn);

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
            case PLAYING:
                player.update();
                hud.updateHUD(player.loadout.getCurrentWeapon(), player.loadout.getPoints(), player.loadout.getPerks());
                for (Entity zombie : roundManager.getZombies()) {
                    if (zombie != null) {
                        zombie.update();
                    }
                }
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
                break;
            case MENU:
                menu.update();
        }

    }

    public void startGame() {
        gameState = GameState.PLAYING;
    }


    public void draw(Graphics2D g2) {
        switch (gameState) {
            case MENU:
                menu.draw(g2);
                break;
            case PLAYING:
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
                    }
                }
                player.draw(g2);
                hud.draw(g2);
                break;

        }
    }
}
