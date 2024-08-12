package etat.apothicon.main;

import etat.apothicon.entity.Zombie;
import etat.apothicon.object.perk.machine.DoubleTapMachine;
import etat.apothicon.object.perk.machine.JuggernogMachine;
import etat.apothicon.object.perk.machine.MuleKickMachine;
import etat.apothicon.object.perk.machine.QuickReviveMachine;
import etat.apothicon.object.perk.machine.SpeedColaMachine;
import etat.apothicon.object.weapon.wallbuy.M14_WallBuy;
import etat.apothicon.object.weapon.wallbuy.MP40_WallBuy;
import etat.apothicon.object.weapon.wallbuy.Olympia_WallBuy;
import etat.apothicon.object.weapon.wallbuy.Stakeout_WallBuy;

public class AssetSetter {
    Apothicon ap;

    public AssetSetter(Apothicon ap) {
        this.ap = ap;
    }

    public void setObject() {
        ap.gameManager.obj[0] = new QuickReviveMachine();
        ap.gameManager.obj[0].worldY = 42 * ap.tileSize;
        ap.gameManager.obj[0].worldX = 35 * ap.tileSize;
        ap.gameManager.obj[1] = new DoubleTapMachine();
        ap.gameManager.obj[1].worldY = 21 * ap.tileSize;
        ap.gameManager.obj[1].worldX = 47 * ap.tileSize;
        ap.gameManager.obj[2] = new SpeedColaMachine();
        ap.gameManager.obj[2].worldY = 30 * ap.tileSize;
        ap.gameManager.obj[2].worldX = 24 * ap.tileSize;
        ap.gameManager.obj[3] = new JuggernogMachine();
        ap.gameManager.obj[3].worldY = 6 * ap.tileSize;
        ap.gameManager.obj[3].worldX = 24 * ap.tileSize;
        ap.gameManager.obj[4] = new MuleKickMachine();
        ap.gameManager.obj[4].worldY = 23 * ap.tileSize;
        ap.gameManager.obj[4].worldX = 2 * ap.tileSize;
        ap.gameManager.obj[5] = new Stakeout_WallBuy();
        ap.gameManager.obj[5].worldY = 29 * ap.tileSize;
        ap.gameManager.obj[5].worldX = 43 * ap.tileSize;
        ap.gameManager.obj[6] = new MP40_WallBuy();
        ap.gameManager.obj[6].worldY = 32 * ap.tileSize;
        ap.gameManager.obj[6].worldX = 16 * ap.tileSize;
        ap.gameManager.obj[7] = new MP40_WallBuy();
        ap.gameManager.obj[7].worldY = 20 * ap.tileSize;
        ap.gameManager.obj[7].worldX = 45 * ap.tileSize;
        ap.gameManager.obj[8] = new M14_WallBuy();
        ap.gameManager.obj[8].worldY = 46 * ap.tileSize;
        ap.gameManager.obj[8].worldX = 29 * ap.tileSize;
        ap.gameManager.obj[9] = new Olympia_WallBuy();
        ap.gameManager.obj[9].worldY = 41 * ap.tileSize;
        ap.gameManager.obj[9].worldX = 40 * ap.tileSize;

    }

    public void setZombie(int worldX, int worldY, int zombieIndex) {
        ap.gameManager.roundManager.getZombies()[zombieIndex] = new Zombie(ap);

        ap.gameManager.roundManager.getZombies()[zombieIndex].worldX = worldX;
        ap.gameManager.roundManager.getZombies()[zombieIndex].worldY = worldY;
    }
}
