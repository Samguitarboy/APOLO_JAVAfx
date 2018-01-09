/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apolo_setup;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class CreditController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private void gotoMain(MouseEvent event) throws Exception {
        Parent main_page_parent = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
        Scene main_page_scene = new Scene(main_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_page_scene);
        app_stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
