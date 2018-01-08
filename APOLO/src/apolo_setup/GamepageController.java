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
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class GamepageController {

    private String songselect = "";
    @FXML
    private Circle node2;
    @FXML
    private Pane root;
    @FXML
    private StackPane scene;
    @FXML
    private Label scoreshow, hit;
    @FXML
    private JFXButton stop, stoptempo;
    @FXML
    private TitledPane pane;
    @FXML
    private ImageView left, up, right;
    @FXML
    private GridPane song;
    int score = 0;
    int leftkey = 0, upkey = 0, rightkey = 0;
    int tempindex = 1;
    Boolean start = false;
    Image nodeview = new Image("/images/start.png");

    public void initialize() {
        songDB();
    }

    private void beat_detect() {
        BeatAnalysis beat = new BeatAnalysis();
        DecimalFormat df = new DecimalFormat("##.000");

        Thread t;
        t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println(songselect + "~~~~~");
                    beat.getbeat(songselect);
                    pane.setVisible(false);

                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        int index = 1;
                        Double test = new Double("0.0");
                        Double two = new Double("2.110");

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            test = test + 0.001;
                            if (test > beat.getShowtime().get(index - tempindex) + 2.155) {
                                tempindex--;
                            }
                            scene.setOnKeyPressed(e -> {
                                if (e.getCode() == KeyCode.RIGHT) {
                                    //  System.out.println("RIGHT");
                                    right.setOpacity(1);
                                    rightkey = 0;
                                    // System.out.println("right");

                                    System.out.println(tempindex);
                                    System.out.println(beat.getShowtime().get(index - tempindex) + 2.149);
                                    System.out.println(test);
                                    if (test < beat.getShowtime().get(index - tempindex) + 2.149 + 0.2 && test > beat.getShowtime().get(index - tempindex) + 2.149 - 0.4) {
                                        score += 100;
                                        scoreshow.setText(String.valueOf(score));
                                        hit.setText("Perfect!");
                                        hit.setTextFill(Color.BLUE);
                                        tempindex--;
                                    }
                                    if (test > beat.getShowtime().get(index - tempindex) + 0.2 && test < beat.getShowtime().get(index - tempindex) + 1
                                            || test < beat.getShowtime().get(index - tempindex) + 2.149 - 0.4 && test > beat.getShowtime().get(index - tempindex) + 2.149 - 1) {
                                        score += 50;
                                        scoreshow.setText(String.valueOf(score));
                                        hit.setText("Great!");
                                        hit.setTextFill(Color.GREEN);
                                        tempindex--;
                                    }
                                    if (test > beat.getShowtime().get(index - tempindex) + 2.149 + 1
                                            || test < beat.getShowtime().get(index - tempindex) + 2.149 - 1 && test > beat.getShowtime().get(index - tempindex) + 2.149 - 2) {
                                        hit.setText("Fail!");
                                        hit.setTextFill(Color.RED);

                                    }
                                }
                                if (e.getCode() == KeyCode.LEFT) {
                                    // System.out.println("RIGHT");
                                    left.setOpacity(1);
                                    leftkey = 0;
                                    // System.out.println("left");
                                    // System.out.println(beat.getShowtime().get(index));
                                    System.out.println(tempindex);
                                    System.out.println(beat.getShowtime().get(index - tempindex) + 2.149);
                                    System.out.println(test);
                                    if (test < beat.getShowtime().get(index - tempindex) + 2.149 + 0.2 && test > beat.getShowtime().get(index - tempindex) + 2.149 - 0.4) {
                                        score += 100;
                                        scoreshow.setText(String.valueOf(score));
                                        hit.setText("Perfect!");
                                        hit.setTextFill(Color.BLUE);
                                        tempindex--;
                                    }
                                    if (test > beat.getShowtime().get(index - tempindex) + 0.2 && test < beat.getShowtime().get(index - tempindex) + 1
                                            || test < beat.getShowtime().get(index - tempindex) + 2.149 - 0.4 && test > beat.getShowtime().get(index - tempindex) + 2.149 - 1) {
                                        score += 50;
                                        scoreshow.setText(String.valueOf(score));
                                        hit.setText("Great!");
                                        hit.setTextFill(Color.GREEN);
                                        tempindex--;
                                    }
                                    if (test > beat.getShowtime().get(index - tempindex) + 2.149 + 1 
                                            || test < beat.getShowtime().get(index - tempindex) + 2.149 - 1 && test > beat.getShowtime().get(index - tempindex) + 2.149 - 2) {
                                        hit.setText("Fail");
                                        hit.setTextFill(Color.RED);
                                    }
                                    // System.out.println(test);

                                }
                                if (e.getCode() == KeyCode.UP) {
                                    //System.out.println("RIGHT");
                                    up.setOpacity(1);
                                    upkey = 0;
                                    //  System.out.println(beat.getShowtime().get(index ));
                                    System.out.println(tempindex);
                                    System.out.println(beat.getShowtime().get(index - tempindex) + 2.149);
                                    System.out.println(test);
                                    if (test < beat.getShowtime().get(index - tempindex) + 2.149 + 0.2 && test > beat.getShowtime().get(index - tempindex) + 2.149 - 0.4) {
                                        score += 100;
                                        scoreshow.setText(String.valueOf(score));
                                        hit.setText("Perfect!");
                                        hit.setTextFill(Color.BLUE);
                                        tempindex--;
                                    }
                                    if (test > beat.getShowtime().get(index - tempindex) + 0.2 && test < beat.getShowtime().get(index - tempindex) + 1
                                            || test < beat.getShowtime().get(index - tempindex) + 2.149 - 0.4 && test > beat.getShowtime().get(index - tempindex) + 2.149 - 1) {
                                        score += 50;
                                        scoreshow.setText(String.valueOf(score));
                                        hit.setText("Great!");
                                        hit.setTextFill(Color.GREEN);
                                        tempindex--;
                                    }
                                    if (test > beat.getShowtime().get(index - tempindex) + 2.149 + 1 
                                            || test < beat.getShowtime().get(index - tempindex) + 2.149 - 1 && test > beat.getShowtime().get(index - tempindex) + 2.149 - 2) {
                                        hit.setText("Fail");
                                        hit.setTextFill(Color.RED);
                                    }
                                    // System.out.println("mid");
                                    // System.out.println(test);
                                }

                            });

                            //System.out.println(Double.parseDouble(df.format(test)) + "~~~~~");
                            //System.out.println(beat.getShowtime());
                            if (1 == compare(Double.parseDouble(df.format(test)), beat.getShowtime().get(index))) {
                                if (beat.getShowornot().get(index) > Float.parseFloat(df.format(beat.getAverageEnergy()))) {
                                    //     System.out.println(beat.getShowtime().get(index));
                                    Platform.runLater(() -> {
                                        if (beat.getShowtime().get(index) * 1000 % 3 == 1) {
                                            newmidnode();

                                        }
                                        if (beat.getShowtime().get(index) * 1000 % 3 == 2) {
                                            newleftnode();
                                        }
                                        if (beat.getShowtime().get(index) * 1000 % 3 == 0) {
                                            newrightnode();
                                        }
                                        node2.setVisible(true);
                                    });
                                    index++;
                                    tempindex++;
                                } else {
                                    Platform.runLater(() -> {
                                        node2.setVisible(false);
                                    });
                                    index++;
                                    tempindex++;
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
                    },
                            0, 1);
                    Thread.sleep(
                            2000);
                    playmp3();

                    stoptempo.setOnAction(e
                            -> {
                        timer.cancel();
                    }
                    );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
        );
        t.start();
    }

    private void newleftnode() {
        DecimalFormat df = new DecimalFormat("##.000");

        ImageView leftnode = new ImageView(nodeview);
        leftnode.setTranslateX(-298);
        leftnode.setTranslateY(-500);
        scene.getChildren().add(leftnode);
        TranslateTransition move = new TranslateTransition(Duration.millis(2149), leftnode);
        move.setByY(750);
        move.play();
        move.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                scene.getChildren().remove(leftnode);

            }
        }
        );
    }

    private void newmidnode() {
        ImageView midnode = new ImageView(nodeview);

        midnode.setTranslateY(-500);
        scene.getChildren().add(midnode);
        TranslateTransition move = new TranslateTransition(Duration.millis(2149), midnode);
        move.setByY(750);
        move.play();
        move.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                scene.getChildren().remove(midnode);

            }
        }
        );
    }

    private void newrightnode() {
        ImageView rightnode = new ImageView(nodeview);
        rightnode.setTranslateX(298);
        rightnode.setTranslateY(-500);
        scene.getChildren().add(rightnode);
        TranslateTransition move = new TranslateTransition(Duration.millis(2149), rightnode);
        move.setByY(750);
        move.play();
        move.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                scene.getChildren().remove(rightnode);

            }
        }
        );
    }

    private void key_detection() {
        scene.setOnKeyPressed((Event event) -> {
            if (event.toString().substring(181) != null) {
                switch (event.toString().substring(181)) {
                    case "LEFT]":
                        left.setOpacity(1);
                        leftkey = 0;
                        hit.setText("Perfect!!");

                        break;
                    case "RIGHT]":
                        right.setOpacity(1);
                        rightkey = 0;
                        hit.setText("Great!");
                        break;
                    case "UP]":
                        up.setOpacity(1);
                        upkey = 0;
                        hit.setText("Fail!");
                        break;
                    default:
                        break;
                }

            }

            /*if (event.toString().substring(181) != null) {
                switch (event.toString().substring(181)) {
                    case "LEFT]":
                        left.setOpacity(1);
                        leftkey = 0;
                        node2.setVisible(false);
                        if (move.getFromY()< 670 && move.getFromY() > 630) {
                            score += 100;
                            hit.setText("Perfect!!");
                            hit.setTextFill(Color.BLUE);
                        } else if (move.getFromY() > 670 && move.getFromY() < 690 || move.getFromY() < 630 && move.getFromY() > 610) {
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
                        node2.setVisible(false);
                        if (move.getFromY() < 670 && move.getFromY() > 630) {
                            score += 100;
                            hit.setText("Perfect!!");
                            hit.setTextFill(Color.BLUE);
                        } else if (move.getFromY() > 670 && move.getFromY() < 690 || move.getFromY() < 630 && move.getFromY() > 610) {
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
                        if (move.getFromY() < 670 && move.getFromY() > 630) {
                            score += 100;
                            hit.setText("Perfect!!");
                            hit.setTextFill(Color.BLUE);
                        } else if (move.getFromY() > 670 && move.getFromY() < 690 || move.getFromY() < 630 && move.getFromY() > 610) {
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

            }*/
        }
        );
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
                    pane.setVisible(false);

                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        int index = 1;
                        Double test = new Double("0.0");
                        Double two = new Double("2.110");

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            test = test + 0.001;

                            //System.out.println(Double.parseDouble(df.format(test)) + "~~~~~");
                            //System.out.println(beat.getShowtime());
                            if (1 == compare(Double.parseDouble(df.format(test)), pitch.getShowtime().get(index))) {
                                if (pitch.getShowornot().get(index) == 1) {
                                    //System.out.println(beat.getShowtime().get(index));
                                    Platform.runLater(() -> {
                                        if (pitch.getShowtime().get(index) * 1000 % 3 == 1) {
                                            newmidnode();
                                        }
                                        if (pitch.getShowtime().get(index) * 1000 % 3 == 2) {
                                            newleftnode();
                                        }
                                        if (pitch.getShowtime().get(index) * 1000 % 3 == 0) {
                                            newrightnode();
                                        }
                                        node2.setVisible(true);
                                    });
                                    index++;
                                } else {
                                    Platform.runLater(() -> {
                                        node2.setVisible(false);
                                    });
                                    index++;
                                }
                            }
                            if (index == pitch.getShowtime().size()) {
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
                    Thread.sleep(2000);
                    playmp3();
                    stoptempo.setOnAction(e -> {
                        timer.cancel();
                    });

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
