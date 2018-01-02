package apolo_setup;

import javafx.fxml.FXML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;

public class GamepageController {

    @FXML
    private Button main1;
    @FXML
    private Circle node1, node2, node3;
    @FXML
    private StackPane scene;

    public void initialize() {
        // TODO

    }

    public void postInit() {
        String Path = System.getProperty("user.dir") + "/src/main/resources/hit.mp3";
        Media hit = new Media(new File(Path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);

        Timer timer = new Timer();
        node1.setVisible(true);
        node2.setVisible(true);
        node3.setVisible(true);
        TimerTask nodemove = new TimerTask() {
            int count = -420;
            int temp;

            @Override
            public void run() {
                // TODO Auto-generated method stub   
                node1.setTranslateY(count);
                if (count < 350) {
                    temp = count - 50;
                }
                node2.setTranslateY(temp);
                node3.setTranslateY(count + 30);
                count += 1;
            }
        };
        timer.schedule(nodemove, 0, 2);//execute speed 
        scene.setOnKeyPressed((Event event) -> {
            mediaPlayer.play();
            if (null != event.toString().substring(181)) {
                switch (event.toString().substring(181)) {
                    case " LEFT]":
                        
                        node1.setVisible(false);
                         {
                            try {
                                this.writefile(String.valueOf(node1.getTranslateY()));
                            } catch (IOException ex) {
                                Logger.getLogger(GamepageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case " RIGHT]":
                        node3.setVisible(false);
                         {
                            try {
                                this.writefile(String.valueOf(node2.getTranslateY()));
                            } catch (IOException ex) {
                                Logger.getLogger(GamepageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case " UP]":
                        node2.setVisible(false);
                        //System.out.println(event.toString().substring(181));
                         {
                            try {
                                this.writefile(String.valueOf(node3.getTranslateY()));
                            } catch (IOException ex) {
                                Logger.getLogger(GamepageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            //System.out.println(event.toString().substring(181));
        });

    }

    public void dispose() {

    }

    public void writefile(String pos) throws IOException {
        String path = System.getProperty("user.dir") + "/src/main/resources/test.txt";
        FileWriter fw = new FileWriter(path, true);
        fw.write(pos + " ");
        System.out.println("ok");
        fw.close();
    }
}
