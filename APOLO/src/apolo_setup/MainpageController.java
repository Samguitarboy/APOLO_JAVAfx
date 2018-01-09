package apolo_setup;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainpageController implements Initializable {

    @FXML
    private Label label, hello;
    @FXML
    private Pane sayhello;

    @FXML
    private void about() {
        String Path = System.getProperty("user.dir") + "/src/music/Apolo_change.mp3";
        Media hit = new Media(new File(Path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Apolo");
        alert.setHeaderText("About Apolo");
        alert.setGraphic(new ImageView(new Image("images/Sun.png")));
        alert.setContentText("Designed by Sam.Chen & Eddie.Lu");
        alert.showAndWait();
    }

    @FXML
    private void gotoPlayingPage(MouseEvent event) throws Exception {
        String Path = System.getProperty("user.dir") + "/src/music/Apolo_change.mp3";
        Media hit = new Media(new File(Path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        Parent main_page_parent = FXMLLoader.load(getClass().getResource("gamepage.fxml"));
        Scene main_page_scene = new Scene(main_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_page_scene);
        app_stage.show();

    }

    @FXML
    private void gotoDownloadPage(MouseEvent event) throws Exception {
        String Path = System.getProperty("user.dir") + "/src/music/Apolo_change.mp3";
        Media hit = new Media(new File(Path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        Parent main_page_parent = FXMLLoader.load(getClass().getResource("download.fxml"));
        Scene main_page_scene = new Scene(main_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_page_scene);
        app_stage.show();

    }

    @FXML
    private void gotoCreditPage(MouseEvent event) throws Exception {
        String Path = System.getProperty("user.dir") + "/src/music/Apolo_change.mp3";
        Media hit = new Media(new File(Path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        Parent main_page_parent = FXMLLoader.load(getClass().getResource("Credit.fxml"));
        Scene main_page_scene = new Scene(main_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_page_scene);
        app_stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        LoginController login = new LoginController();
        if (login.username != "") {
            hello.setText("HELLO, " + login.username + "~~~");
            TranslateTransition move = new TranslateTransition(Duration.millis(5000), hello);
            move.setByX(-1300);
            move.play();
            move.setOnFinished(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    sayhello.setVisible(false);
                }
            }
            );
        } else {
            sayhello.setVisible(false);
        }
    }

}
