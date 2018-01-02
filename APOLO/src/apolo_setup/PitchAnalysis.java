package apolo_setup;

import be.hogent.tarsos.dsp.AudioDispatcher;
import be.hogent.tarsos.dsp.AudioEvent;
import be.hogent.tarsos.dsp.pitch.PitchDetectionHandler;
import be.hogent.tarsos.dsp.pitch.PitchDetectionResult;
import be.hogent.tarsos.dsp.pitch.PitchProcessor;
import be.hogent.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;
import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

public class PitchAnalysis {

    private double timestamp = 0;
    private float pitchrecord = 0;
    private int count = 0;

    public void getpitch() throws Exception {
        PitchDetectionHandler handler = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult,
                    AudioEvent audioEvent) {
                count++;
                compare(audioEvent.getTimeStamp(), pitchDetectionResult.getPitch());
                if (count == 12) {
                    System.out.println(timestamp + " " + pitchrecord);
                    count=0;
                    timestamp = 0;
                    pitchrecord = 0;
                }

            }
        };
        AudioInputStream audioInputStream = getPcmAudioInputStream("songlist\\2.mp3");
        AudioDispatcher adp = new AudioDispatcher(audioInputStream, 2048, 0);

        adp.addAudioProcessor(new PitchProcessor(PitchEstimationAlgorithm.YIN, 44100, 2048, handler));
        adp.run();
    }

    private void compare(double time, float input) {
        if (input == -1) {
            pitchrecord = 0;
        } else if (input > pitchrecord) {
            timestamp = time;
            pitchrecord = input;
        }
    }

    private static AudioInputStream getPcmAudioInputStream(String mp3filepath) {
        File mp3 = new File(mp3filepath);
        AudioInputStream audioInputStream = null;
        AudioFormat targetFormat = null;
        try {
            AudioInputStream in = null;
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(mp3);
            AudioFormat baseFormat = in.getFormat();
            targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }
}
