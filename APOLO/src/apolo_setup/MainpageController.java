package apolo_setup;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class MainpageController implements Initializable {

    @FXML
    private JFXButton stop;
    @FXML
    private Label label;

    @FXML
    private void playmp3(MouseEvent event) throws Exception {
        Playing producer = new Playing();
        Thread t = new Thread(producer);
        //t.setDaemon(true);
        t.start();
        stop.setOnAction(e -> {
            t.stop();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private static class Playing implements Runnable {
        @Override
        public void run() {
            try {
                ConvertMP32PCM.convertMP32PCM("songlist\\1.mp3", "songlist\\1.pcm");
                ConvertMP32PCM.playMP3("songlist\\1.mp3");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

}
