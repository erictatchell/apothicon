package etat.apothicon.sound;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundHandler {
    Clip clip;
    URL gunSounds[] = new URL[30];
    URL impactSounds[] = new URL[30];
    URL interactSounds[] = new URL[30];

    public SoundHandler() {
        gunSounds[0] = getClass().getClassLoader().getResource("sound/m1911-fire.wav");
        gunSounds[1] = getClass().getClassLoader().getResource("sound/m1911-reload.wav");
        gunSounds[2] = getClass().getClassLoader().getResource("sound/olympia-fire.wav");
        gunSounds[3] = getClass().getClassLoader().getResource("sound/olympia-reload.wav");
        gunSounds[4] = getClass().getClassLoader().getResource("sound/m14-fire.wav");
        gunSounds[5] = getClass().getClassLoader().getResource("sound/m14-reload.wav");
        gunSounds[6] = getClass().getClassLoader().getResource("sound/mp40-fire.wav");
        gunSounds[7] = getClass().getClassLoader().getResource("sound/mp40-reload.wav");
        gunSounds[8] = getClass().getClassLoader().getResource("sound/stakeout-fire.wav");
        gunSounds[9] = getClass().getClassLoader().getResource("sound/stakeout-reload.wav");

        impactSounds[0] = getClass().getClassLoader().getResource("sound/collision.wav");
        impactSounds[1] = getClass().getClassLoader().getResource("sound/kill1.wav");
        impactSounds[2] = getClass().getClassLoader().getResource("sound/kill2.wav");
        impactSounds[3] = getClass().getClassLoader().getResource("sound/kill3.wav");
        impactSounds[4] = getClass().getClassLoader().getResource("sound/headshot1.wav");
        impactSounds[5] = getClass().getClassLoader().getResource("sound/hit1.wav");
        impactSounds[6] = getClass().getClassLoader().getResource("sound/hit2.wav");
        impactSounds[7] = getClass().getClassLoader().getResource("sound/hit3.wav");
        impactSounds[8] = getClass().getClassLoader().getResource("sound/hit4.wav");
        impactSounds[9] = getClass().getClassLoader().getResource("sound/hit5.wav");
        impactSounds[10] = getClass().getClassLoader().getResource("sound/hit6.wav");

        interactSounds[0] = getClass().getClassLoader().getResource("sound/purchase.wav");
    }

    public void setFile(int i, SoundType t) {
        try {
            AudioInputStream stream = null;
            switch (t) {
                case IMPACT:
                    stream = AudioSystem.getAudioInputStream(impactSounds[i]);
                    break;
                case GUN:
                    stream = AudioSystem.getAudioInputStream(gunSounds[i]);
                    break;
                case INTERACT:
                    stream = AudioSystem.getAudioInputStream(interactSounds[i]);
                    break;
            }
            clip = AudioSystem.getClip();
            clip.open(stream);

        } catch (Exception e) {
        }

    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

}
