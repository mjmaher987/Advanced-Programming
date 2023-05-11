package project.View;

import java.io.IOException;
import java.net.URL;
//import java.security.acl.Group;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class test implements Initializable {

    @FXML
    Rectangle rectangle;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public void createScene(Pane pane) {
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                rectangle = new Rectangle(50,75);
                rectangle.setX(200 + 55 * i);
                rectangle.setY(50 + 82.5 * j);
                rectangle.setFill(Color.RED);
                rectangles.add(rectangle);
            }
        }
       
        pane.getChildren().addAll(rectangles);
        new MainView().changeScene(pane);
    }
}