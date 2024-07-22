package etat.apothicon.main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundHandler {
    Clip clip;
    URL soundURL[] = new URL[30];

    public SoundHandler() {
        soundURL[0] = getClass().getClassLoader().getResource("sound/collision.wav");
        soundURL[1] = getClass().getClassLoader().getResource("sound/m1911-fire.wav");
        soundURL[2] = getClass().getClassLoader().getResource("sound/m1911-reload.wav");
        soundURL[3] = getClass().getClassLoader().getResource("sound/olympia-fire.wav");
        soundURL[4] = getClass().getClassLoader().getResource("sound/olympia-reload.wav");
        soundURL[5] = getClass().getClassLoader().getResource("sound/m14-fire.wav");
        soundURL[6] = getClass().getClassLoader().getResource("sound/m14-reload.wav");
        
        soundURL[7] = getClass().getClassLoader().getResource("sound/mp40-fire.wav");
        soundURL[8] = getClass().getClassLoader().getResource("sound/mp40-reload.wav");

        soundURL[9] = getClass().getClassLoader().getResource("sound/stakeout-fire.wav");
        soundURL[10] = getClass().getClassLoader().getResource("sound/stakeout-reload.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(soundURL[i]);
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
