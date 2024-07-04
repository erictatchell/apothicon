package etat.apothicon.perk;

import etat.apothicon.main.Apothicon;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class JuggernogMachine extends PerkMachine {

    public JuggernogMachine(Apothicon ap) {
        super(ap);

        this.x = 100;
        this.y = 100;
        render();
    }

    public void render() {
        try {
            this.machine = ImageIO.read(new File("src/main/resources/perks/juggernog-machine.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
