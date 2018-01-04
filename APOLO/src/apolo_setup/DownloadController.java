package apolo_setup;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
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
import javax.swing.event.ChangeListener;
import org.apache.commons.io.FileUtils;
        
public class DownloadController implements Initializable {

    @FXML
    private void gotoMain(MouseEvent event) throws Exception {
        Parent main_page_parent = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
        Scene main_page_scene = new Scene(main_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_page_scene);
        app_stage.show();

    }

    private String videoid;

    @FXML
    private Button download, sql;

    @FXML
    private TextField songtitle, a;

    @FXML
    private WebView web;

    private WebEngine engine;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        engine = web.getEngine();
        engine.load("https://www.youtube.com");
        songtitle.setText("");
        a.setText("");
        engine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                File file = new File("/songlist/");
                String[] downloadableExtensions = {".doc", ".xls", ".zip", ".exe", ".rar", ".pdf", ".jar", ".png", ".jpg", ".gif",".mp3"};
                for (String downloadAble : downloadableExtensions) {
                    if (newValue.endsWith(downloadAble)) {
                        try {
                            if (!file.exists()) {
                                file.mkdir();
                            }
                            File download = new File(file + "/" + newValue);
                            if (download.exists()) {
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Exists");
                                Dialogs.create().title("Exists").message("What you're trying to download already exists").showInformation();
                                return;
                            }
                            Dialogs.create().title("Downloading").message("Started Downloading").showInformation();
                            FileUtils.copyURLToFile(new URL(engine.getLocation()), download);
                            Dialogs.create().title("Download").message("Download is completed your download will be in: " + file.getAbsolutePath()).showInformation();
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
            engine.load("https://www.convyoutube.com/watch?v=" + temp[1]);
        });
        sql.setOnAction(e -> {
            songtoDB(songtitle.getText());
        }
        );
    }

    private void songtoDB(String title) {
        try {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            System.out.println(title);
            config con = new config();
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw());
            System.out.println(conn);
            System.out.println("Database Connection !");
            stmt = conn.createStatement();
            System.out.println(videoid);
            /*String insertsong = "insert into Songlist values(N'" + title + "',null,CURRENT_DATE,'" + videoid + "')";
            stmt.executeUpdate(insertsong);*/
            String SQL = "select * from Songlist";
            rs = stmt.executeQuery(SQL);

            ResultSetMetaData metaData = rs.getMetaData();
            int numCol = metaData.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= numCol; i++) {
                    System.out.print("\t\t" + rs.getObject(i));
                }
                System.out.println();
            }
            System.out.println();
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
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
