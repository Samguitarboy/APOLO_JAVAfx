package apolo_setup;

import be.hogent.tarsos.dsp.AudioDispatcher;
import be.hogent.tarsos.dsp.AudioEvent;
import be.hogent.tarsos.dsp.pitch.PitchDetectionHandler;
import be.hogent.tarsos.dsp.pitch.PitchDetectionResult;
import be.hogent.tarsos.dsp.pitch.PitchProcessor;
import be.hogent.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;
import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

public class PitchAnalysis {

    private double timestamp = 0;
    private float pitchrecord = -1;
    private int count = 0;

    private ArrayList<Double> showtime = new ArrayList<Double>();
    private ArrayList<Integer> showornot = new ArrayList<Integer>();

    public ArrayList<Double> getShowtime() {
        return showtime;
    }

    public ArrayList<Integer> getShowornot() {
        return showornot;
    }
    

    public void getpitch() throws Exception {
        PitchDetectionHandler handler = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult,
                    AudioEvent audioEvent) {
                count++;
                compare(audioEvent.getTimeStamp(), pitchDetectionResult.getPitch());
                if (count == 12) {
                    System.out.println(timestamp + " " + pitchrecord);
                    showtime.add(timestamp);
                    if(pitchrecord==0.0)
                        showornot.add(0);
                    else
                        showornot.add(1);
                    count=0;
                    timestamp = 0;
                    pitchrecord = -1;
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
            timestamp = time;
            pitchrecord = 0;
        } else if (input > pitchrecord && pitchrecord!=0) {
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
