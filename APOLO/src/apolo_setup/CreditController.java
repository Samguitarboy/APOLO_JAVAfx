/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apolo_setup;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreditController implements Initializable {

    @FXML
    private VBox showname, showscore, showsong;

    @FXML
    private void gotoMain(MouseEvent event) throws Exception {
        Parent main_page_parent = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
        Scene main_page_scene = new Scene(main_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_page_scene);
        app_stage.show();

    }

    public void PNAME(String result, int i) {

        JFXButton n = new JFXButton(result);
        n.setStyle(" -fx-background-color: \n"
                + "        #ecebe9,\n"
                + "        rgba(0,0,0,0.05),\n"
                + "        linear-gradient(#dcca8a, #c7a740),\n"
                + "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\n"
                + "        linear-gradient(#f6ebbe, #e6c34d);\n"
                + "    -fx-background-insets: 0,9 9 8 9,9,10,11;\n"
                + "    -fx-background-radius: 50;\n"
                + "    -fx-padding: 10 20 10 20;\n"
                + "    -fx-font-family: \"Helvetica\";\n"
                + "    -fx-font-size: 20px;\n"
                + "    -fx-text-fill: #311c09;\n"
                + "    -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
        n.setAlignment(Pos.CENTER);

        showname.getChildren().add(n);

    }

    public void PSONG(String result, int i) {

        JFXButton g = new JFXButton(result);
        g.setStyle(" -fx-background-color: \n"
                + "        #ecebe9,\n"
                + "        rgba(0,0,0,0.05),\n"
                + "        linear-gradient(#dcca8a, #c7a740),\n"
                + "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\n"
                + "        linear-gradient(#f6ebbe, #e6c34d);\n"
                + "    -fx-background-insets: 0,9 9 8 9,9,10,11;\n"
                + "    -fx-background-radius: 50;\n"
                + "    -fx-padding: 10 20 10 20;\n"
                + "    -fx-font-family: \"Helvetica\";\n"
                + "    -fx-font-size: 20px;\n"
                + "    -fx-text-fill: #311c09;\n"
                + "    -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
        g.setAlignment(Pos.CENTER);

        showsong.getChildren().add(g);

    }

    public void PSCORE(String result, int i) {

        JFXButton s = new JFXButton(result);
        s.setStyle(" -fx-background-color: \n"
                + "        #ecebe9,\n"
                + "        rgba(0,0,0,0.05),\n"
                + "        linear-gradient(#dcca8a, #c7a740),\n"
                + "        linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%),\n"
                + "        linear-gradient(#f6ebbe, #e6c34d);\n"
                + "    -fx-background-insets: 0,9 9 8 9,9,10,11;\n"
                + "    -fx-background-radius: 50;\n"
                + "    -fx-padding: 10 20 10 20;\n"
                + "    -fx-font-family: \"Helvetica\";\n"
                + "    -fx-font-size: 20px;\n"
                + "    -fx-text-fill: #311c09;\n"
                + "    -fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);");
        s.setAlignment(Pos.CENTER);

        showscore.getChildren().add(s);

    }

    private void creditDB() {
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String songnum = "select count(*) from playrecord";
        String personname = "select Name from playrecord";
        String personscore = "select score from playrecord";
        String personsong = "select songname from playrecord";
        MySQLConnector MSC = new MySQLConnector();
        MSC.connectDB(connectionStr);
        //number
        MSC.doquery(songnum);
        int count = Integer.valueOf(MSC.getResult().toString().trim());
        MSC.clearresult();
        //name
        MSC.doquery(personname);
        for (int i = 0; i < count; i++) {
            PNAME(MSC.getResult().toString().split("\n")[i], i);
        }
        MSC.clearresult();
        //score
        MSC.doquery(personscore);
        for (int i = 0; i < count; i++) {
            PSCORE(MSC.getResult().toString().split("\n")[i], i);
        }
        MSC.clearresult();
        //song
        MSC.doquery(personsong);
        for (int i = 0; i < count; i++) {
            PSONG(MSC.getResult().toString().split("\n")[i], i);
        }
        MSC.closeconnection();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        creditDB();
    }

}
