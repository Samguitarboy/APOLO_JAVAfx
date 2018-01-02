package apolo_setup;

import javafx.fxml.FXML;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class GamepageController {

    @FXML
    private Button main1;
    @FXML
    private Circle node1, node2, node3;
    @FXML
    private StackPane scene;
    
    int score=0;

    public void initialize() {
        Circle circle = new Circle();
        circle.setVisible(true);
        circle.setId("newnode");
        circle.setTranslateY(500);
        circle.setTranslateX(200);
        
        try {
            this.fileclear();
        } catch (IOException ex) {
            Logger.getLogger(GamepageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Timer timer = new Timer();
        TimerTask nodemove = new TimerTask() {
            int count = 0;
            int temp = 0;
            int temp2 = 0;

            @Override
            public void run() { // 650 標準點 
                // TODO Auto-generated method stub   
                node1.setTranslateY(count);
                node2.setTranslateY(temp);
                node3.setTranslateY(temp2);
                count += 1;
                temp += 1;
                temp2 += 1;
                if (count > 800) {
                    count = 0;
                    node1.setVisible(true);
                }
                if (temp > 800) {
                    temp = -420;
                    node2.setVisible(true);
                }
                if (temp2 > 800) {
                    temp2 = 0;
                    node3.setVisible(true);
                }
            }
        };
        timer.schedule(nodemove, 0, 2);//execute speed 
        scene.setOnKeyPressed((Event event) -> {
            if (null != event.toString().substring(181)) {
                switch (event.toString().substring(181)) {
                    case " LEFT]":
                        node1.setVisible(false);
                        if (node1.getTranslateY()<670 && node1.getTranslateY()>630)
                            score+=100;
                        else if(node1.getTranslateY()>670 && node1.getTranslateY()<690 ||node1.getTranslateY()<630 && node1.getTranslateY()>610)
                            score+=50;
                            
                         {
                            try {
                                this.writefile(String.valueOf(node1.getTranslateY()));
                            } catch (IOException ex) {
                                Logger.getLogger(GamepageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case " RIGHT]":
                        node3.setVisible(false);
                        if (node1.getTranslateY()<670 && node1.getTranslateY()>630)
                            score+=100;
                        else if(node1.getTranslateY()>670 && node1.getTranslateY()<690 ||node1.getTranslateY()<630 && node1.getTranslateY()>610)
                            score+=50;
                            
                         {
                            try {
                                this.writefile(String.valueOf(node2.getTranslateY()));
                            } catch (IOException ex) {
                                Logger.getLogger(GamepageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    case " UP]":
                        node2.setVisible(false);
                        if (node1.getTranslateY()<670 && node1.getTranslateY()>630)
                            score+=100;
                        else if(node1.getTranslateY()>670 && node1.getTranslateY()<690 ||node1.getTranslateY()<630 && node1.getTranslateY()>610)
                            score+=50;
                            
                        //System.out.println(event.toString().substring(181));
                         {
                            try {
                                this.writefile(String.valueOf(node3.getTranslateY()));
                            } catch (IOException ex) {
                                Logger.getLogger(GamepageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
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

    public void postInit() {

    }

    public void dispose() {

    }

    public void fileclear() throws IOException {
        String path = System.getProperty("user.dir") + "/src/music/test.txt";
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("");
        }
    }

    public void writefile(String pos) throws IOException {
        String path = System.getProperty("user.dir") + "/src/music/test.txt";
        try (FileWriter fw = new FileWriter(path, true)) {
            fw.write(pos + " ");
            System.out.println(score);
        }
    }
}
