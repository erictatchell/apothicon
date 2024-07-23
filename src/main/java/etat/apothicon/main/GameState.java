package etat.apothicon.main;

import etat.apothicon.ai.PathFinder;
import etat.apothicon.entity.Entity;
import etat.apothicon.entity.Player;
import etat.apothicon.hud.HUD;
import etat.apothicon.object.SuperObject;
import etat.apothicon.object.weapon.gun.Bullet;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameState {

    public Apothicon ap;
    public Player player;
    public Entity zombies[];
    public ArrayList<Bullet> bullets;
    public SuperObject obj[];
    public HUD hud;
    public ZoneManager zoneManager;
    public CollisionChecker cc;
    public AssetSetter aSetter;
    public PathFinder pFinder;

    public GameState(Apothicon ap) {
        this.ap = ap;
    }

    public void setup() {
        player = new Player(ap, ap.keyIn, ap.mouseIn);
        zombies = new Entity[10];
        bullets = new ArrayList<>();
        obj = new SuperObject[10];
        zoneManager = new ZoneManager(ap);

        hud = new HUD(ap);

        cc = new CollisionChecker(ap);
        aSetter = new AssetSetter(ap);
        pFinder = new PathFinder(ap);
        aSetter.setObject();
        aSetter.setZombie();

    }

    public void update() {
        player.update();

        hud.updateHUD(player.loadout.getCurrentWeapon(), player.loadout.getPoints(), player.loadout.getPerks());
        for (Entity zombie : zombies) {
            if (zombie != null) {
                zombie.update();
            }
        }
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
    }


    public void draw(Graphics2D g2) {
       ap.tileManager.draw(g2);
        for (int i = 0; i < zombies.length; i++) {
            if (zombies[i] != null) {
                zombies[i].draw(g2);
            }
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

        player.draw(g2);
        for (Zone zone : zoneManager.zones) {
            zone.draw(g2, ap);
        }

        hud.draw(g2);
    }
}
