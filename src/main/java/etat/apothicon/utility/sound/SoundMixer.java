package etat.apothicon.utility.sound;

import javax.sound.sampled.*;

public class SoundMixer {
    private SourceDataLine line;
    private AudioFormat format;

    public SoundMixer() {
        format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playSound(byte[] audioData) {
        if (line != null) {
            line.write(audioData, 0, audioData.length);
        }
    }

    // Method to mix multiple audio streams
    public void mixAndPlay(byte[]... audioData) {
        if (line != null) {
            for (byte[] data : audioData) {
                line.write(data, 0, data.length);
            }
        }
    }
}
