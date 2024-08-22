package etat.apothicon.utility.sound;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;

public class SoundHandler implements Runnable {
    static URL[] gunSounds = new URL[120];
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

    private URL[] fillGunSounds(String[] guns) {
        int i = 0;
        URL[] sounds = new URL[128];
        for (String gun : guns) {
            for (int m = 0; m < 1; m++, i++) {
                sounds[i] = getClass().getClassLoader().getResource("sound/" + gun + "-fire.wav");
            }
            for (int k = 0; k < 1; k++, i++) {
                sounds[i] = getClass().getClassLoader().getResource("sound/" + gun + "-pap-fire.wav");
            }
            sounds[i] = getClass().getClassLoader().getResource("sound/" + gun + "-reload.wav");
        }
        return sounds;
    }

    private void populateArrays() {
        String[] guns = new String[]{"m1911", "olympia", "m14", "mp40", "stakeout"};
        gunSounds = fillGunSounds(guns);

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

