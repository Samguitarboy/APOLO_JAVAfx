package apolo_setup;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DownloadController implements Initializable {

    @FXML
    private void gotoMain(MouseEvent event) throws Exception {
        Parent main_page_parent = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
        Scene main_page_scene = new Scene(main_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_page_scene);
        app_stage.show();

    }
    private String dloadurl;
    private String videoid;
    private String mp3filepath = "songlist\\1.mp3";
    private String pcmfilepath = "songlist\\1.pcm";

    @FXML
    private Button backtomain, download;


    @FXML
    private TextField website, a;

    @FXML
    private WebView web;

    private WebEngine engine;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        engine = web.getEngine();
        engine.load("https://www.youtube.com");
        website.setText("");
        a.setText("");
        try {
                PitchAnalysis pitch = new PitchAnalysis();
                pitch.getpitch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        download.setOnAction(e -> {
            String[] temp;
            temp = a.getText().split("youtu.be/");
            engine.load("https://www.convyoutube.com/watch?v=" + temp[1]);
        });
    }
/*
    private void songtoDB(String title) {
        try {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            config con = new config();
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw());
            System.out.println(conn);
            System.out.println("Database Connection !");
            stmt = conn.createStatement();
            System.out.println(videoid);
            /*String insertsong = "insert into Songlist values(N'" + title + "',null,CURRENT_DATE,'" + videoid + "')";
            stmt.executeUpdate(insertsong);*//*
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
