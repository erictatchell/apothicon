package etat.apothicon.main;

import etat.apothicon.entity.Entity;
import etat.apothicon.entity.Player;
import etat.apothicon.object.weapon.gun.Bullet;
import java.util.ArrayList;

public class GameState {

    public Apothicon ap;
    public Player player;
    public Entity zombies[];
    public ArrayList<Bullet> bullets;
    public KeyInput keyIn = new KeyInput();
    public MouseInput mouseIn = new MouseInput();

    public GameState(Apothicon ap) {
        this.ap = ap;
        setup();
    }

    public void setup() {
        player = new Player(ap, keyIn, mouseIn);
        zombies = new Entity[10];
        bullets = new ArrayList<>();
    }

    public void update() {
        player.update();
        for (Entity zombie : zombies) {
            if (zombie != null) {
                zombie.update();
            }
        }
        for (Bullet bullet : bullets) {
            if (bullet != null) {
                if (bullet.alive) {
                    bullet.update();
                } else {
                    bullets.remove(bullet);
                }
            }
        }
    }
}
