package apolo_setup;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

import static java.lang.Double.compare;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

public class GamepageController {

    private String songselect = "";
    @FXML
    private Circle node1, node2, node3;
    @FXML
    private StackPane scene;
    @FXML
    private Label scoreshow, hit;
    @FXML
    private JFXButton stop;
    @FXML
    private TitledPane pane;
    @FXML
    private ImageView left, up, right;
    @FXML
    private GridPane song;
    int score = 0;
    int leftkey = 0, upkey = 0, rightkey = 0;

    Image nodeview = new Image("http://hajsoftutorial.com/im/car.png");

    public void initialize() {
        songDB();
        //  TranslateTransition nodemove =new TranslateTransition(Duration.millis(1000),node1);

        scene.setOnKeyPressed((Event event) -> {
            if (event.toString().substring(181) != null) {
                switch (event.toString().substring(181)) {
                    case "LEFT]":
                        left.setOpacity(1);
                        leftkey = 0;
                        node1.setVisible(false);
                        if (node1.getTranslateY() < 670 && node1.getTranslateY() > 630) {
                            score += 100;
                            hit.setText("Perfect!!");
                            hit.setTextFill(Color.BLUE);
                        } else if (node1.getTranslateY() > 670 && node1.getTranslateY() < 690 || node1.getTranslateY() < 630 && node1.getTranslateY() > 610) {
                            score += 50;
                            hit.setText("Great!");
                            hit.setTextFill(Color.GREEN);
                        } else {
                            hit.setText("Fail!");
                            hit.setTextFill(Color.RED);
                        }
                        break;
                    case "RIGHT]":
                        right.setOpacity(1);
                        rightkey = 0;
                        node3.setVisible(false);
                        if (node3.getTranslateY() < 670 && node3.getTranslateY() > 630) {
                            score += 100;
                            hit.setText("Perfect!!");
                            hit.setTextFill(Color.BLUE);
                        } else if (node3.getTranslateY() > 670 && node3.getTranslateY() < 690 || node3.getTranslateY() < 630 && node3.getTranslateY() > 610) {
                            score += 50;
                            hit.setText("Great!");
                            hit.setTextFill(Color.GREEN);
                        } else {
                            hit.setText("Fail!");
                            hit.setTextFill(Color.RED);
                        }
                        break;
                    case "UP]":
                        up.setOpacity(1);
                        upkey = 0;
                        node2.setVisible(false);
                        if (node2.getTranslateY() < 670 && node2.getTranslateY() > 630) {
                            score += 100;
                            hit.setText("Perfect!!");
                            hit.setTextFill(Color.BLUE);
                        } else if (node2.getTranslateY() > 670 && node2.getTranslateY() < 690 || node2.getTranslateY() < 630 && node2.getTranslateY() > 610) {
                            score += 50;
                            hit.setText("Great!");
                            hit.setTextFill(Color.GREEN);
                        } else {
                            hit.setText("Fail!");
                            hit.setTextFill(Color.RED);
                        }
                        //System.out.println(event.toString().substring(181));
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void beat_detect() {
        BeatAnalysis beat = new BeatAnalysis();
        DecimalFormat df = new DecimalFormat("##.000");
        newleftnode();
        newmidnode();
        newrightnode();
        Thread t;
        t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println(songselect + "~~~~~");
                    beat.getbeat(songselect);
                    pane.setVisible(false);
                    playmp3();

                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        int index = 1;
                        Double test = 0.0;

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            test = test + 0.001;
                            //System.out.println(Double.parseDouble(df.format(test)) + "~~~~~");
                            //System.out.println(beat.getShowtime());
                            if (1 == compare(Double.parseDouble(df.format(test)), beat.getShowtime().get(index))) {
                                if (beat.getShowornot().get(index) > Float.parseFloat(df.format(beat.getAverageEnergy()))) {
                                    System.out.println(beat.getShowtime().get(index));
                                    node2.setVisible(true);
                                    index++;
                                } else {
                                    node2.setVisible(false);
                                    index++;
                                }
                            }
                            if (index == beat.getShowtime().size()) {
                                this.cancel();
                            }
                            leftkey++;
                            upkey++;
                            rightkey++;
                            if (leftkey == 85) {
                                left.setOpacity(0.5);
                            }
                            if (rightkey == 85) {
                                right.setOpacity(0.5);
                            }
                            if (upkey == 85) {
                                up.setOpacity(0.5);
                            }
                        }
                    }, 0, 1);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        t.start();
    }

    private void newleftnode() {
        ImageView leftnode = new ImageView(nodeview);
        leftnode.setTranslateX(-298);
        leftnode.setTranslateY(-500);
        scene.getChildren().add(leftnode);
        TranslateTransition move = new TranslateTransition(Duration.millis(3000), leftnode);
        move.setByY(1000);
        move.play();
    }

    private void newmidnode() {
        ImageView midnode = new ImageView(nodeview);
        midnode.setTranslateY(-500);
        scene.getChildren().add(midnode);
        TranslateTransition move = new TranslateTransition(Duration.millis(3000), midnode);
        move.setByY(1000);
        move.play();
    }

    private void newrightnode() {
        ImageView rightnode = new ImageView(nodeview);
        rightnode.setTranslateX(298);
        rightnode.setTranslateY(-500);
        scene.getChildren().add(rightnode);
        TranslateTransition move = new TranslateTransition(Duration.millis(3000), rightnode);
        move.setByY(1000);
        move.play();
    }

    private void pitch_detect() {
        PitchAnalysis pitch = new PitchAnalysis();
        DecimalFormat df = new DecimalFormat("##.000");
        Thread t;
        t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println(songselect + "~~~~~");
                    pitch.getpitch(songselect);
                    pane.setExpanded(false);
                    playmp3();

                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        int index = 1;
                        Double test = 0.0;

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            test = test + 0.001;
                            //System.out.println(Double.parseDouble(df.format(test)) + "~~~~~");
                            System.out.println(pitch.getShowtime().get(index));
                            /*if (1 == compare(Double.parseDouble(df.format(test)), pitch.getShowtime().get(index))) {
                                if (pitch.getShowornot().get(index) == 1) {
                                    System.out.println(pitch.getShowtime().get(index));
                                    node2.setVisible(true);
                                    index++;
                                } else {
                                    node2.setVisible(false);
                                    index++;
                                }
                            }
                            if (index == pitch.getShowtime().size()) {
                                this.cancel();
                            }
                            rightkey++;
                            leftkey++;
                            upkey++;
                            if (upkey == 85) {
                                up.setOpacity(0.5);
                            }
                            if (rightkey == 85) {
                                right.setOpacity(0.5);
                            }
                            if (leftkey == 85) {
                                left.setOpacity(0.5);
                            }*/
                        }
                    }, 0, 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        t.start();
    }

    private void playmp3() throws Exception {
        Playing producer = new Playing(songselect);
        Thread t = new Thread(producer);
        //t.setDaemon(true);
        t.start();
        stop.setOnAction(e -> {
            t.stop();
        });
    }

    @FXML
    private void gotoMain(MouseEvent event) throws Exception {

        Parent main_page_parent = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
        Scene main_page_scene = new Scene(main_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_page_scene);
        app_stage.show();

    }

    private void songDB() {
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String songnum = "select count(*) from Songlist";
        String songtitles = "select Song_Title from Songlist";

        MySQLConnector MSC = new MySQLConnector();
        MSC.connectDB(connectionStr);
        //Song_number
        MSC.doquery(songnum);
        int count = Integer.valueOf(MSC.getResult().toString().trim());
        MSC.clearresult();
        //Song_title
        MSC.doquery(songtitles);
        for (int i = 0; i < count; i++) {
            songlist(MSC.getResult().toString().split("\n")[i], i);
        }
        MSC.closeconnection();
        pane.setContent(song);
    }

    public void songlist(String result, int i) {
        Button temp = new Button(result);
        song.add(temp, 0, i);
        temp.setOnAction(e -> {
            if (result != "") {
                songselect = result.trim();
                System.out.println(songselect + " ready to start!!!!");
                pane.setExpanded(false);
                beat_detect();
            }
        });
    }

    private static class Playing implements Runnable {

        private String play_song = "";

        public Playing(String songpath) {
            this.play_song = songpath;
        }

        @Override
        public void run() {
            try {
                ConvertMP32PCM.playMP3("songlist\\" + play_song + ".mp3");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }
}
