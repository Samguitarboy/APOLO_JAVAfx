package apolo;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import v4lk.lwbd.BeatDetector;
import v4lk.lwbd.BeatDetector.DetectorSensitivity;
import v4lk.lwbd.decoders.Decoder;
import v4lk.lwbd.decoders.JLayerMp3Decoder;
import v4lk.lwbd.util.Beat;

public class BeatAnalysis {

    private float AverageEnergy = 0;
    private ArrayList<Double> showtime = new ArrayList<Double>();
    private ArrayList<Float> showornot = new ArrayList<Float>();

    public ArrayList<Double> getShowtime() {
        return showtime;
    }

    public ArrayList<Float> getShowornot() {
        return showornot;
    }

    public int getsize() {
        return showtime.size();
    }

    public void getbeat(String song) throws Exception {
        File audioFile = new File("songlist/" + song + ".mp3");
        FileInputStream stream = new FileInputStream(audioFile);
        Decoder decoder = new JLayerMp3Decoder(stream);
        Beat[] beats = BeatDetector.detectBeats(decoder, DetectorSensitivity.LOW);
        DecimalFormat df = new DecimalFormat("##.000");
        for (int i = 0; i < beats.length; i++) {
            System.out.println(Double.parseDouble(df.format((double) beats[i].timeMs / 1000)) + " " + Float.parseFloat(df.format((float) beats[i].energy)));
            AverageEnergy = AverageEnergy + beats[i].energy;
            System.out.println(AverageEnergy);
            showtime.add(Double.parseDouble(df.format((double)beats[i].timeMs/1000)));
            showornot.add(beats[i].energy);
        }
    }

    public float getAverageEnergy() {
        return (float)AverageEnergy/(showtime.size()*5/2);
    }
}
