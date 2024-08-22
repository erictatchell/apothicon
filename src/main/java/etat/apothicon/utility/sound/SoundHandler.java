package etat.apothicon.utility.sound;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.sound.sampled.*;

public class SoundHandler implements Runnable {
    static URL[] gunSounds = new URL[30];
    static URL[] impactSounds = new URL[30];
    static URL[] interactSounds = new URL[30];
    static URL[] roundChangeMusic = new URL[10];

    static final Map<SoundType, Map<Integer, Clip>> preloadedClips = new HashMap<>();

    public SoundHandler() {
        populateArrays();
        preloadAllSounds();
    }

    // asked grok to come up with this
    private void preloadAllSounds() {
        for (SoundType type : SoundType.values()) {
            Map<Integer, Clip> typeClips = new HashMap<>();
            URL[] sounds = switch (type) {
                case IMPACT -> impactSounds;
                case GUN -> gunSounds;
                case INTERACT -> interactSounds;
                case ROUND_CHANGE -> roundChangeMusic;
                default -> null;
            };
            if (sounds != null) {
                for (int i = 0; i < sounds.length; i++) {
                    try {
                        AudioInputStream stream = AudioSystem.getAudioInputStream(sounds[i]);
                        Clip clip = AudioSystem.getClip();
                        clip.open(stream);
                        typeClips.put(i, clip);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            preloadedClips.put(type, typeClips);
        }
    }

    // grok tweaked this, came paired with preloadAllSounds()
    public static void play(int i, SoundType t) {
        Clip myClip = preloadedClips.get(t).get(i);
        if (myClip != null) {
            myClip.setFramePosition(0); // Reset to start
            myClip.start();
            myClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    myClip.setFramePosition(0); // Reset for next play
                }
            });
        } else {
            System.out.println("Clip not found for " + t + " at index " + i);
        }
    }

    private void populateArrays() {
        gunSounds[0] = getClass().getClassLoader().getResource("sound/m1911-fire.wav");
        gunSounds[1] = getClass().getClassLoader().getResource("sound/m1911-pap-fire.wav");
        gunSounds[2] = getClass().getClassLoader().getResource("sound/m1911-reload.wav");
        gunSounds[3] = getClass().getClassLoader().getResource("sound/olympia-fire.wav");
        gunSounds[4] = getClass().getClassLoader().getResource("sound/olympia-pap-fire.wav");
        gunSounds[5] = getClass().getClassLoader().getResource("sound/olympia-reload.wav");
        gunSounds[6] = getClass().getClassLoader().getResource("sound/m14-fire.wav");
        gunSounds[7] = getClass().getClassLoader().getResource("sound/m14-pap-fire.wav");
        gunSounds[8] = getClass().getClassLoader().getResource("sound/m14-reload.wav");
        gunSounds[9] = getClass().getClassLoader().getResource("sound/mp40-fire.wav");
        gunSounds[10] = getClass().getClassLoader().getResource("sound/mp40-pap-fire.wav");
        gunSounds[11] = getClass().getClassLoader().getResource("sound/mp40-reload.wav");
        gunSounds[12] = getClass().getClassLoader().getResource("sound/stakeout-fire.wav");
        gunSounds[13] = getClass().getClassLoader().getResource("sound/stakeout-pap-fire.wav");
        gunSounds[14] = getClass().getClassLoader().getResource("sound/stakeout-reload.wav");

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

    @Override
    public void run() {

    }
}
