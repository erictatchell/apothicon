package etat.apothicon.main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundHandler {
    Clip clip;
    URL soundURL[] = new URL[30];

    public SoundHandler() {
        soundURL[0] = getClass().getClassLoader().getResource("sound/m1911.wav");
        soundURL[1] = getClass().getClassLoader().getResource("sound/m1911-reload.wav");

        soundURL[2] = getClass().getClassLoader().getResource("sound/olympia-fire.wav");
        soundURL[3] = getClass().getClassLoader().getResource("sound/olympia-reload.wav");
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
