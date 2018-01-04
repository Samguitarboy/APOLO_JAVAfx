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

public class GamepageController {

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
    private GridPane song;
    int score = 0;

    public void initialize() {
        songDB();
        PitchAnalysis pitch = new PitchAnalysis();
        DecimalFormat df = new DecimalFormat("##.000");
        Thread t;
        t = new Thread(new Runnable() {
            
            public void run() {
                try {
                    pitch.getpitch();
                    playmp3();

                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        int index = 1;
                        Double test = 0.0;

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            test = test + 0.001;
                            System.out.println(Double.parseDouble(df.format(test)) + "~~~~~");
                            System.out.println(pitch.getShowtime().get(index));
                            if (1==compare(Double.parseDouble(df.format(test)) , pitch.getShowtime().get(index))) {
                                if (pitch.getShowornot().get(index) == 1) {
                                    System.out.println(pitch.getShowtime().get(index));
                                    node2.setVisible(true);
                                    index++;
                                } else {
                                    node2.setVisible(false);
                                    index++;
                                }
                            }
                            if(index==pitch.getShowtime().size())
                                this.cancel();
                        }
                        
                    }, 0, 1);  
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        });
        t.start();

        /*scene.setOnKeyPressed((Event event) -> {
                    if (null != event.toString().substring(181)) {
                        switch (event.toString().substring(181)) {
                            case " LEFT]":
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
                                 {
                                    try {
                                        writefile(String.valueOf(node1.getTranslateY()));
                                    } catch (IOException ex) {
                                        Logger.getLogger(GamepageController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                break;
                            case " RIGHT]":
                                node3.setVisible(false);
                                if (node1.getTranslateY() < 670 && node1.getTranslateY() > 630) {
                                    score += 100;
                                } else if (node1.getTranslateY() > 670 && node1.getTranslateY() < 690 || node1.getTranslateY() < 630 && node1.getTranslateY() > 610) {
                                    score += 50;
                                }

                                 {
                                    try {
                                        writefile(String.valueOf(node2.getTranslateY()));
                                    } catch (IOException ex) {
                                        Logger.getLogger(GamepageController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                break;
                            case " UP]":
                                node2.setVisible(false);
                                if (node1.getTranslateY() < 670 && node1.getTranslateY() > 630) {
                                    score += 100;
                                } else if (node1.getTranslateY() > 670 && node1.getTranslateY() < 690 || node1.getTranslateY() < 630 && node1.getTranslateY() > 610) {
                                    score += 50;
                                }

                                //System.out.println(event.toString().substring(181));
                                 {
                                    try {
                                        writefile(String.valueOf(node3.getTranslateY()));
                                    } catch (IOException ex) {
                                        Logger.getLogger(GamepageController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }); */
    }

    private void playmp3() throws Exception {
        Playing producer = new Playing();
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
        temp.setOnAction(e -> {
            System.out.println(result + " ready to start!!!!");
        });
        song.add(temp, 0, i);

    }

    private static class Playing implements Runnable {

        @Override
        public void run() {
            try {
                ConvertMP32PCM.playMP3("songlist\\1.mp3");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }
}
