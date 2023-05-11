package project.View;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

//import com.google.gson.GsonBuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainView extends Application {
    private static Stage stage;
    private Pane root;

    @Override
    public void start(Stage stage) throws IOException {
        URL welcomeUrl = getClass().getResource("/project/fxml/loginPage.fxml");
        root = FXMLLoader.load(welcomeUrl);
        MainView.stage = stage;
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
        System.out.println("dasdasd");
    }

    public void changeView(String fxml) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(root);
    }

    public void changeScene(Parent parent){
      stage.getScene().setRoot(parent);
    }

    public static Stage getStage() {
        return stage;
    }
}
