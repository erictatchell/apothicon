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
    int assetIndex;
    static int counter = 0;

    public AssetSetter(Apothicon ap) {
        this.ap = ap;
    }

    public void setObject() {
        ap.gameManager.obj[assetIndex] = new QuickReviveMachine();
        ap.gameManager.obj[assetIndex].worldY = 42 * ap.tileSize;
        ap.gameManager.obj[assetIndex++].worldX = 35 * ap.tileSize;
        ap.gameManager.obj[assetIndex] = new DoubleTapMachine();
        ap.gameManager.obj[assetIndex].worldY = 21 * ap.tileSize;
        ap.gameManager.obj[assetIndex++].worldX = 47 * ap.tileSize;
        ap.gameManager.obj[assetIndex] = new SpeedColaMachine();
        ap.gameManager.obj[assetIndex].worldY = 30 * ap.tileSize;
        ap.gameManager.obj[assetIndex++].worldX = 24 * ap.tileSize;
        ap.gameManager.obj[assetIndex] = new JuggernogMachine();
        ap.gameManager.obj[assetIndex].worldY = 6 * ap.tileSize;
        ap.gameManager.obj[assetIndex++].worldX = 24 * ap.tileSize;
        ap.gameManager.obj[assetIndex] = new MuleKickMachine();
        ap.gameManager.obj[assetIndex].worldY = 23 * ap.tileSize;
        ap.gameManager.obj[assetIndex++].worldX = 2 * ap.tileSize;
        ap.gameManager.obj[assetIndex] = new Stakeout_WallBuy();
        ap.gameManager.obj[assetIndex].worldY = 29 * ap.tileSize;
        ap.gameManager.obj[assetIndex++].worldX = 43 * ap.tileSize;
        ap.gameManager.obj[assetIndex] = new MP40_WallBuy();
        ap.gameManager.obj[assetIndex].worldY = 32 * ap.tileSize;
        ap.gameManager.obj[assetIndex++].worldX = 16 * ap.tileSize;
        ap.gameManager.obj[assetIndex] = new MP40_WallBuy();
        ap.gameManager.obj[assetIndex].worldY = 20 * ap.tileSize;
        ap.gameManager.obj[assetIndex++].worldX = 45 * ap.tileSize;
        ap.gameManager.obj[assetIndex] = new M14_WallBuy();
        ap.gameManager.obj[assetIndex].worldY = 46 * ap.tileSize;
        ap.gameManager.obj[assetIndex++].worldX = 29 * ap.tileSize;
        ap.gameManager.obj[assetIndex] = new Olympia_WallBuy();
        ap.gameManager.obj[assetIndex].worldY = 41 * ap.tileSize;
        ap.gameManager.obj[assetIndex++].worldX = 40 * ap.tileSize;
        ap.gameManager.obj[assetIndex] = new InfernalMachine();
        ap.gameManager.obj[assetIndex].worldX = 7 * ap.tileSize;
        ap.gameManager.obj[assetIndex++].worldY = 2 * ap.tileSize;
    }

    public void removeDrop(int index) {
        ap.gameManager.obj[index] = null;
    }

    public void cleanUpObjects() {
        SuperObject[] temp = new SuperObject[GameManager.MAX_OBJECTS];
        int j = 0;
        for (int i = 0; i < ap.gameManager.obj.length; i++) {
            temp[j] = ap.gameManager.obj[i];
            if (ap.gameManager.obj[i] != null) {
                if (temp[j] instanceof Drop drop) {
                    drop.objIndex = j;
                }
                assetIndex = j;
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
        Drop drop = new Drop(assetIndex, worldX, worldY, ap, DropType.randomDrop());
        try {
            ap.gameManager.dropManager.spawn(drop);
            ap.gameManager.obj[assetIndex++] = drop;
        } catch (ArrayIndexOutOfBoundsException outOfBounds) {
            System.err.println("Out out of object space, replacing obj[" + (GameManager.MAX_OBJECTS - 1) + "] with new drop");
            ap.gameManager.obj[GameManager.MAX_OBJECTS - 1] = drop;
        }
        counter++;
        if (counter == GameManager.CLEANUP_TARGET) {
            ap.gameManager.aSetter.cleanUpObjects();
            counter = 0;
        }
    }
}
