package apolo_setup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    public Boolean loginornot = false;
    public static String username = "";
    @FXML
    private Label sameerror;

    @FXML
    private JFXTextField userid_input;

    @FXML
    private JFXButton signin;

    @FXML
    private void gotoMain(MouseEvent event) throws Exception {
        Parent main_page_parent = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
        Scene main_page_scene = new Scene(main_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_page_scene);
        app_stage.show();

    }

    @FXML
    public void duplicateorstart(MouseEvent event) throws Exception {
        config con = new config();
        String connectionStr = "jdbc:mysql://" + con.getUrlstr() + "/" + con.getDBName() + "?user=" + con.getUserstr() + "&password=" + con.getPw();
        String duplicate = "select Username from userinfo where Username = '" + userid_input.getText() + "';";
        MySQLConnector duplicateornot = new MySQLConnector();
        duplicateornot.connectDB(connectionStr);
        System.out.println(userid_input.getText());
        System.out.println("WOW FROM DB DUPLICATE~~~~" + duplicateornot.result.toString());

        duplicateornot.doquery(duplicate);
        if (0 == duplicateornot.result.toString().compareTo("")) {
            String insertname = "insert into userinfo values(N'" + userid_input.getText() + "',null,CURRENT_DATE)";
            duplicateornot.doInsert(insertname);
            loginornot = true;
            username = userid_input.getText();
            System.out.println(username);
            Parent main_page_parent = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
            Scene main_page_scene = new Scene(main_page_parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            app_stage.setScene(main_page_scene);

            app_stage.show();
        } else {
            sameerror.setVisible(true);
        }
    }
    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (0 == userid_input.getText().compareTo("")) {
                signin.setDisable(true);
            } else {
                signin.setDisable(false);
            }

        }

    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userid_input.setOnMouseMoved(mouseHandler);
        signin.setOnAction(e -> {
            System.out.println(userid_input.getText());
        });

    }

}
