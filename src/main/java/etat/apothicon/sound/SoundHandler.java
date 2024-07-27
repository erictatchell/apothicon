package etat.apothicon.sound;

import java.net.URL;
import java.util.Vector;

import javax.sound.sampled.*;

public class SoundHandler implements Runnable {
    public static Vector<Clip> vector = new Vector<>();
    static final int vector_size = 10;
    static URL[] gunSounds = new URL[30];
    static URL[] impactSounds = new URL[30];
    static URL[] interactSounds = new URL[30];

    static URL[] roundChangeMusic = new URL[10];

    public SoundHandler() {

    }

    // typically called before calling play()
    public static synchronized void consolidate() {
        while (vector_size < vector.size()) {
            Clip myClip = vector.get(0);
            if (myClip.isRunning())
                break;
            myClip.close();
            vector.remove(0);
        }
        if (vector_size * 2 < vector.size())
            System.out.println("warning: audio consolidation lagging");
    }
    public static void play(int i, SoundType t) {
        try {
            AudioInputStream stream  = null;
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
                case ROUND_CHANGE:
                    stream = AudioSystem.getAudioInputStream(roundChangeMusic[i]);
            }
            final Clip myClip = AudioSystem.getClip();
            vector.add(myClip);
            myClip.open(stream);
            myClip.start();
        } catch (Exception myException) {
            myException.printStackTrace();
        }
    }


    @Override
    public void run() {
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

        roundChangeMusic[0] = getClass().getClassLoader().getResource("sound/round-change-1.wav");
    }
}
