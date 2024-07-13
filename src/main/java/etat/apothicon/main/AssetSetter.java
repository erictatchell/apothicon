package etat.apothicon.main;

import etat.apothicon.object.DoubleTapMachine;
import etat.apothicon.object.JuggernogMachine;
import etat.apothicon.object.MuleKickMachine;
import etat.apothicon.object.QuickReviveMachine;
import etat.apothicon.object.SpeedColaMachine;

public class AssetSetter {
    Apothicon ap;

    public AssetSetter(Apothicon ap) {
        this.ap = ap;
    }

    public void setObject() {
        ap.obj[0] = new QuickReviveMachine();
        ap.obj[0].worldY = 42 * ap.tileSize;
        ap.obj[0].worldX = 35 * ap.tileSize;
        
        ap.obj[1] = new DoubleTapMachine();
        ap.obj[1].worldY = 21 * ap.tileSize;
        ap.obj[1].worldX = 47 * ap.tileSize;
        
        ap.obj[2] = new SpeedColaMachine();
        ap.obj[2].worldY = 30 * ap.tileSize;
        ap.obj[2].worldX = 24 * ap.tileSize;
    
        ap.obj[3] = new JuggernogMachine();
        ap.obj[3].worldY = 6 * ap.tileSize;
        ap.obj[3].worldX = 24 * ap.tileSize;
        
        ap.obj[4] = new MuleKickMachine();
        ap.obj[4].worldY = 23 * ap.tileSize;
        ap.obj[4].worldX = 2 * ap.tileSize;
        
    }
}
