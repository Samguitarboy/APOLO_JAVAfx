package apolo;

import java.text.DecimalFormat;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class NewNode {

    private StackPane scene;

    public NewNode(StackPane stackpane) {
        scene = stackpane;
    }

    Image nodeview = new Image("/images/start.png");

    public void newleftnode() {
        DecimalFormat df = new DecimalFormat("##.000");

        ImageView leftnode = new ImageView(nodeview);
        leftnode.setTranslateX(-298);
        leftnode.setTranslateY(-500);
        scene.getChildren().add(leftnode);
        TranslateTransition move = new TranslateTransition(Duration.millis(2149), leftnode);
        move.setByY(750);
        move.play();
        move.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                scene.getChildren().remove(leftnode);

            }
        }
        );
    }

    public void newmidnode() {
        ImageView midnode = new ImageView(nodeview);

        midnode.setTranslateY(-500);
        scene.getChildren().add(midnode);
        TranslateTransition move = new TranslateTransition(Duration.millis(2149), midnode);
        move.setByY(750);
        move.play();
        move.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                scene.getChildren().remove(midnode);

            }
        }
        );
    }

    public void newrightnode() {
        ImageView rightnode = new ImageView(nodeview);
        rightnode.setTranslateX(298);
        rightnode.setTranslateY(-500);
        scene.getChildren().add(rightnode);
        TranslateTransition move = new TranslateTransition(Duration.millis(2149), rightnode);
        move.setByY(750);
        move.play();
        move.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                scene.getChildren().remove(rightnode);

            }
        }
        );
    }
}
