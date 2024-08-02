package etat.apothicon.utility.sound;

import java.net.URL;
import java.util.Vector;
import javax.sound.sampled.*;

public class SoundHandler implements Runnable {
    public static Vector<Clip> vector = new Vector<>();
    static final int MAX_VECTOR_SIZE = 5;
    static URL[] gunSounds = new URL[30];
    static URL[] impactSounds = new URL[30];
    static URL[] interactSounds = new URL[30];
    static URL[] roundChangeMusic = new URL[10];

    public SoundHandler() {
        Thread clipMonitor = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    consolidate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        clipMonitor.setDaemon(true);
        clipMonitor.start();
    }

    // Typically called before calling play()
    public static synchronized void consolidate() {
        for (int i = 0; i < vector.size(); i++) {
            Clip myClip = vector.get(i);
            if (!myClip.isRunning()) {
                myClip.close();
                vector.remove(i);
                i--; // Adjust the index after removal
            }
        }
        if (MAX_VECTOR_SIZE * 2 < vector.size())
            System.out.println("Warning: audio consolidation lagging");
    }

    public static void play(int i, SoundType t) {
        try {
            AudioInputStream stream = switch (t) {
                case IMPACT -> AudioSystem.getAudioInputStream(impactSounds[i]);
                case GUN -> AudioSystem.getAudioInputStream(gunSounds[i]);
                case INTERACT -> AudioSystem.getAudioInputStream(interactSounds[i]);
                case ROUND_CHANGE -> AudioSystem.getAudioInputStream(roundChangeMusic[i]);
                default -> null;
            };
            final Clip myClip = AudioSystem.getClip();
            vector.add(myClip);
            myClip.open(stream);
            myClip.start();
            myClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    myClip.close();
                    vector.remove(myClip);
                }
            });
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
        roundChangeMusic[1] = getClass().getClassLoader().getResource("sound/round-change-3.wav");
    }
}
