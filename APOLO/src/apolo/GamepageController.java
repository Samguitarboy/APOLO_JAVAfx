package apolo;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import static java.lang.Double.compare;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javax.swing.ImageIcon;

public class GamepageController {

    @FXML
    private StackPane scene;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Label scoreshow, hit, scorenum, name;
    @FXML
    private JFXButton stop, stoptempo;
    @FXML
    private TitledPane pane;
    @FXML
    private ImageView left, up, right, countdown;
    @FXML
    private VBox song;
    @FXML
    private Pane cover;
    @FXML
    private Button backtomain1;

    int score = 0;
    int leftkey = 0, upkey = 0, rightkey = 0;
    private ArrayList<Double> lefttimes = new ArrayList<Double>();
    private ArrayList<Double> righttimes = new ArrayList<Double>();
    private ArrayList<Double> uptimes = new ArrayList<Double>();
    int tempindex = 1;
    private String songselect = "";
    LoginController login = new LoginController();

    public void initialize() {
        songlist_fromDB();
        scroll.vvalueProperty().bind(song.heightProperty());
    }

    private void beat_detect() {
        BeatAnalysis beat = new BeatAnalysis();
        NewNode addpoint = new NewNode(scene);
        InsertSQL insertdb = new InsertSQL();

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

                        @Override
                        public void run() {

                            test = test + 0.001;
                            if (test > beat.getShowtime().get(index - tempindex) + 2.155) {
                                tempindex--;
                            }

                            //right left up animate
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

                            //addcircle
                            if (1 == compare(Double.parseDouble(df.format(test)), beat.getShowtime().get(index))) {
                                if (beat.getShowornot().get(index) > Float.parseFloat(df.format(beat.getAverageEnergy()))) {
                                    //     System.out.println(beat.getShowtime().get(index));
                                    Platform.runLater(() -> {
                                        if (beat.getShowtime().get(index) * 1000 % 3 == 1) {
                                            addpoint.newmidnode();
                                            uptimes.add(beat.getShowtime().get(index) + 2.149);
                                        }
                                        if (beat.getShowtime().get(index) * 1000 % 3 == 2) {
                                            addpoint.newleftnode();
                                            lefttimes.add(beat.getShowtime().get(index) + 2.149);
                                        }
                                        if (beat.getShowtime().get(index) * 1000 % 3 == 0) {
                                            addpoint.newrightnode();
                                            righttimes.add(beat.getShowtime().get(index) + 2.149);
                                        }

                                    });
                                    index++;

                                } else {
                                    index++;

                                }
                            }

                            //click
                            scene.setOnKeyPressed(e -> {
                                if (e.getCode() == KeyCode.RIGHT) {
                                    right.setOpacity(1);
                                    rightkey = 0;
                                    /*
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

                                    }*/
                                }
                                if (e.getCode() == KeyCode.LEFT) {

                                    left.setOpacity(1);
                                    leftkey = 0;
                                    /*
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
                                    // System.out.println(test);*/

                                }
                                if (e.getCode() == KeyCode.UP) {

                                    up.setOpacity(1);
                                    upkey = 0;
                                    /*
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
                                    }*/

                                }

                            });

                            //end
                            if (index == beat.getShowtime().size()) {
                                this.cancel();
                                if (!"".equals(login.username)) {
                                    insertdb.recordtoDB(login.username, score, songselect);
                                    showrecord();
                                } else {
                                    cover.setVisible(true);
                                    backtomain1.setVisible(true);
                                }
                            }
                        }
                    }, 0, 1);
                    Platform.runLater(() -> {
                        ImageIcon loading = new ImageIcon("images/countdown.gif");
                        countdown.setImage(new Image("images/countdown.gif"));
                        countdown.setVisible(true);
                    });
                    Thread.sleep(2000);
                    //countdown.setVisible(false);
                    playmp3();

                    stoptempo.setOnAction(e -> {
                        timer.cancel();
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
        );
        t.start();
    }

    private void playmp3() throws Exception {
        PlayingSong producer = new PlayingSong(songselect);
        Thread t = new Thread(producer);
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

    private void showrecord() {
        cover.setVisible(true);
        backtomain1.setVisible(true);
        Platform.runLater(() -> {
            name.setText(login.username);
            scorenum.setText("" + score);
        });
    }

    private void songlist_fromDB() {
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
        scroll.setContent(song);
    }

    public void songlist(String result, int i) {
        HBox h = new HBox();
        song.getChildren().add(h);
        JFXButton space = new JFXButton("      ");
        space.setPrefWidth(1000);

        JFXButton temp = new JFXButton(result);
        temp.setStyle(" -fx-background-color: \n"
                + "        #090a0c,\n"
                + "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n"
                + "        linear-gradient(#20262b, #191d22),\n"
                + "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n"
                + "    -fx-background-radius: 5,4,3,5;\n"
                + "    -fx-background-insets: 0,1,2,0;\n"
                + "    -fx-text-fill: white;\n"
                + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n"
                + "    -fx-font-family: \"Arial\";\n"
                + "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n"
                + "    -fx-font-size: 12px;\n"
                + "    -fx-padding: 10 20 10 20;");
        temp.setPrefWidth(1500);
        temp.setAlignment(Pos.CENTER);
        temp.setOnAction(e -> {
            if (result != "") {
                songselect = result.trim();
                System.out.println(songselect + " ready to start!!!!");
                pane.setExpanded(false);
                beat_detect();

            }
        });

        JFXButton delete = new JFXButton("X");
        delete.setStyle("-fx-background-color: \n"
                + "        #ecebe9,\n"
                + "        rgba(0,0,0,0.05),\n"
                + "        linear-gradient(#dcca8a, #c7a740),\n"
                + "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\n"
                + "        linear-gradient(#f6ebbe, #e6c34d);\n"
                + "    -fx-background-insets: 0,9 9 8 9,9,10,11;\n"
                + "    -fx-background-radius: 50;\n"
                + "    -fx-padding: 10 20 10 20;\n"
                + "    -fx-font-family: \"Helvetica\";\n"
                + "    -fx-font-size: 10px;\n"
                + "    -fx-text-fill: #311c09;\n"
                + "    -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");

        delete.setOnAction(e -> {
            deleteSQL deletedb = new deleteSQL();
            if (!"".equals(result)) {
                System.out.println(result.trim() + " want to Delete!!!!");
                pane.setExpanded(false);
                deletedb.deletesong(result.trim());
                h.getChildren().clear();
                pane.setExpanded(true);
            }
        });
        h.getChildren().add(space);
        h.getChildren().add(temp);
        h.getChildren().add(delete);
        pane.setExpanded(true);

    }
}
