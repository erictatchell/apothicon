package etat.apothicon.main;

import etat.apothicon.entity.Zombie;
import etat.apothicon.object.drop.Drop;
import etat.apothicon.object.drop.DropType;
import etat.apothicon.object.InfernalMachine;
import etat.apothicon.object.SuperObject;
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
    int i;
    static int counter = 0;

    public AssetSetter(Apothicon ap) {
        this.ap = ap;
    }

    public void setObject() {
        ap.gameManager.obj[i] = new QuickReviveMachine();
        ap.gameManager.obj[i].worldY = 42 * ap.tileSize;
        ap.gameManager.obj[i++].worldX = 35 * ap.tileSize;
        ap.gameManager.obj[i] = new DoubleTapMachine();
        ap.gameManager.obj[i].worldY = 21 * ap.tileSize;
        ap.gameManager.obj[i++].worldX = 47 * ap.tileSize;
        ap.gameManager.obj[i] = new SpeedColaMachine();
        ap.gameManager.obj[i].worldY = 30 * ap.tileSize;
        ap.gameManager.obj[i++].worldX = 24 * ap.tileSize;
        ap.gameManager.obj[i] = new JuggernogMachine();
        ap.gameManager.obj[i].worldY = 6 * ap.tileSize;
        ap.gameManager.obj[i++].worldX = 24 * ap.tileSize;
        ap.gameManager.obj[i] = new MuleKickMachine();
        ap.gameManager.obj[i].worldY = 23 * ap.tileSize;
        ap.gameManager.obj[i++].worldX = 2 * ap.tileSize;
        ap.gameManager.obj[i] = new Stakeout_WallBuy();
        ap.gameManager.obj[i].worldY = 29 * ap.tileSize;
        ap.gameManager.obj[i++].worldX = 43 * ap.tileSize;
        ap.gameManager.obj[i] = new MP40_WallBuy();
        ap.gameManager.obj[i].worldY = 32 * ap.tileSize;
        ap.gameManager.obj[i++].worldX = 16 * ap.tileSize;
        ap.gameManager.obj[i] = new MP40_WallBuy();
        ap.gameManager.obj[i].worldY = 20 * ap.tileSize;
        ap.gameManager.obj[i++].worldX = 45 * ap.tileSize;
        ap.gameManager.obj[i] = new M14_WallBuy();
        ap.gameManager.obj[i].worldY = 46 * ap.tileSize;
        ap.gameManager.obj[i++].worldX = 29 * ap.tileSize;
        ap.gameManager.obj[i] = new Olympia_WallBuy();
        ap.gameManager.obj[i].worldY = 41 * ap.tileSize;
        ap.gameManager.obj[i++].worldX = 40 * ap.tileSize;
        ap.gameManager.obj[i] = new InfernalMachine();
        ap.gameManager.obj[i].worldX = 7 * ap.tileSize;
        ap.gameManager.obj[i++].worldY = 2 * ap.tileSize;
    }

    public void removeDrop(int index) {
        ap.gameManager.obj[index] = null;
    }

    public void cleanUpObjects() {
        SuperObject[] temp = new SuperObject[GameManager.MAX_OBJECTS];
        int j = 0;
        for (int i = 0; i < ap.gameManager.obj.length; i++) {
            if (ap.gameManager.obj[i] != null) {
                temp[j] = ap.gameManager.obj[i];
                if (temp[j] instanceof Drop) {
                    ((Drop) temp[j]).objIndex = j; // disgusting but ok
                }
                j++;
            }
        }
        ap.gameManager.obj = temp;
        System.gc();
    }


    public void setZombie(int worldX, int worldY, int zombieIndex) {
        ap.gameManager.roundManager.getZombies()[zombieIndex] = new Zombie(ap);
        ap.gameManager.roundManager.getZombies()[zombieIndex].worldX = worldX;
        ap.gameManager.roundManager.getZombies()[zombieIndex].worldY = worldY;
    }

    public void spawnDrop(int worldX, int worldY) {
        Drop drop = new Drop(i, worldX, worldY, ap, DropType.randomDrop());
        ap.gameManager.dropManager.spawn(drop);
        ap.gameManager.obj[i++] = drop;
        counter++;
        if (counter == GameManager.CLEANUP_TARGET) {
            ap.gameManager.aSetter.cleanUpObjects();
        }
    }
}
