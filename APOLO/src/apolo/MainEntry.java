package apolo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainEntry extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("loginpage.fxml"));
        
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("images/Sun.png"));
        primaryStage.setTitle("APOLO");
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
