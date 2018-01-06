package apolo_setup;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

public class DownloadController implements Initializable {

    private String videoid;

    @FXML
    private Button download;

    @FXML
    private TextField songtitle, a;

    @FXML
    private WebView web;

    private WebEngine engine;

    @FXML
    private void gotoMain(MouseEvent event) throws Exception {
        engine.load("https://www.youtube.com");
        Parent main_page_parent = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
        Scene main_page_scene = new Scene(main_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_page_scene);
        app_stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        engine = web.getEngine();
        engine.load("https://www.youtube.com");
        songtitle.setText("");
        a.setText("");
        download.setDisable(true);
        songtitle.setOnMouseMoved(mouseHandler);
        a.setOnMouseMoved(mouseHandler);

        engine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                File file = new File("songlist/");
                String[] downloadableExtensions = {".doc", ".xls", ".zip", ".exe", ".rar", ".pdf", ".jar", ".png", ".jpg", ".gif", ".mp3"};
                for (String downloadAble : downloadableExtensions) {
                    if (newValue.endsWith(downloadAble)) {
                        try {
                            if (!file.exists()) {
                                file.mkdir();
                            }
                            File download = new File(file + "/" + songtitle.getText() + ".mp3");
                            if (download.exists()) {
                                Alert exist = new Alert(AlertType.INFORMATION);
                                exist.setTitle("ENJOY");
                                exist.setContentText("Have a good day!!!");
                                exist.showAndWait();
                                engine.load("https://www.youtube.com");
                                return;
                            }

                            Alert start = new Alert(AlertType.INFORMATION);
                            start.setTitle("start");
                            start.setContentText("Started Downloading");
                            start.showAndWait();

                            FileUtils.copyURLToFile(new URL(engine.getLocation()), download);

                            Alert done = new Alert(AlertType.INFORMATION);
                            done.setTitle("completed");
                            done.setContentText("Download is completed your download will be in: " + file.getAbsolutePath());
                            done.showAndWait();

                            songtoDB(songtitle.getText());
                            engine.load("https://www.youtube.com");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        download.setOnAction(e -> {
            String[] temp;
            temp = a.getText().split("youtu.be/");
            videoid = temp[1];
            a.setText("https://www.youtube.com/watch?v=" + temp[1]);
            engine.load("http://www.flvto.biz/");

            //engine.load("https://www.youtubeto.com/watch?v=" + temp[1]);
        });

    }

    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (0 == songtitle.getText().compareTo("") || 0 == a.getText().compareTo("")) {
                download.setDisable(true);
            } else {
                download.setDisable(false);
            }

        }

    };

    private void songtoDB(String title) {

        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String insertsong = "insert into Songlist values(N'" + title + "',null,CURRENT_DATE,'" + videoid + "')";
        MySQLConnector MSC_insert = new MySQLConnector();
        MSC_insert.connectDB(connectionStr);
        System.out.println(title);
        MSC_insert.doInsert(insertsong);
        //MSC_insert.closeconnection();
    }
    /*
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

        Matcher matcher = pattern.matcher(str);

        char ch;

        while (matcher.find()) {

            ch = (char) Integer.parseInt(matcher.group(2), 16);

            str = str.replace(matcher.group(1), ch + "");

        }

        return str;
    }*/
}
