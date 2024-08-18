package etat.apothicon.main;

import etat.apothicon.ai.PathFinder;
import etat.apothicon.entity.Entity;
import etat.apothicon.entity.Player;
import etat.apothicon.object.Drop;
import etat.apothicon.object.DropManager;
import etat.apothicon.object.InfernalMachine;
import etat.apothicon.ui.FontManager;
import etat.apothicon.ui.HUD;
import etat.apothicon.object.SuperObject;
import etat.apothicon.object.weapon.gun.Bullet;
import etat.apothicon.round.RoundManager;
import etat.apothicon.round.Zone;
import etat.apothicon.round.ZoneManager;
import etat.apothicon.tile.TileManager;
import etat.apothicon.ui.menu.DeathMenu;
import etat.apothicon.ui.menu.MainMenu;
import etat.apothicon.utility.GameState;

import java.awt.*;
import java.util.ArrayList;

public class GameManager {
    public GameState gameState;
    public boolean dead = false;
    final Object spawnLock = new Object();
    public Apothicon ap;
    public Player player;
    public ArrayList<Bullet> bullets;
    public SuperObject[] obj;
    public HUD hud;
    public MainMenu mainMenu;
    public DeathMenu deathMenu;
    public ArrayList<Drop> drops;
    public InfernalMachine infernalMachine;
    public ZoneManager zoneManager;
    public RoundManager roundManager;
    public TileManager tileManager;
    public DropManager dropManager;
    public CollisionChecker cc;
    public AssetSetter aSetter;
    public PathFinder pFinder;
    private FontManager fontManager;

    public GameManager(Apothicon ap) {
        this.ap = ap;
    }

    public void setup() {
        gameState = GameState.MAIN_MENU;
        mainMenu = new MainMenu(this, ap.mouseIn, ap.keyIn);
        tileManager = new TileManager(ap);
        dropManager = new DropManager(ap);
        player = new Player(ap, ap.keyIn, ap.mouseIn);
        bullets = new ArrayList<>();
        obj = new SuperObject[100];
        infernalMachine = new InfernalMachine();
        zoneManager = new ZoneManager(ap);
        roundManager = new RoundManager(ap);
        drops = new ArrayList<>();
        hud = new HUD(ap);
        cc = new CollisionChecker(ap);
        aSetter = new AssetSetter(ap);
        pFinder = new PathFinder(ap);
        aSetter.setObject();
        deathMenu = new DeathMenu(this, ap.mouseIn, ap.keyIn);
    }


    public void update() {
        switch (gameState) {
            case PLAYING -> {
                if (!player.isDead()) {
                    player.update();
                    hud.updateHUD(player.getLoadout().getCurrentWeapon(), player.getLoadout().getPoints(), player.getLoadout().getPerks());

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
                    dead = true;
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
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src/main/resources/crosshair/default.png");
        Cursor c = toolkit.createCustomCursor(image, new Point(8, 8), "img");
        ap.setCursor(c);
        gameState = GameState.PLAYING;
    }

    public void resetGame() {
        dead = false;
        player.setDead(false);
        player.getLoadout().reset();
        player.reset();
        roundManager.reset();
        dropManager.reset();
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
                    if (ap.keyIn.debugPressed) {
                        zone.drawZoneBounds(g2, ap);
                        infernalMachine.upgrade(player.getLoadout().getCurrentWeapon());
                    }

                    zone.draw(g2, ap);
                }

                dropManager.draw(g2);
                for (Bullet bullet : bullets) {
                    if (bullet != null) {
                        bullet.drawBullet(g2);
                    }
                }
                for (SuperObject superObject : obj) {
                    if (superObject != null) {
                        superObject.draw(g2, ap);
                    }
                }
                for (Entity zombie : roundManager.getZombies()) {
                    if (zombie != null) {
                        zombie.draw(g2);
                        if (dead) zombie.onPath = false;
                    }
                }
                if (dead) {
                    deathMenu.draw(g2);
                } else {
                    player.draw(g2);
                    hud.draw(g2);

                }
            }
        }
    }
}
