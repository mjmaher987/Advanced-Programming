package project.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import project.model.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    String[][] answer;
    @FXML
    Button upButton;
    @FXML
    Button downButton;
    @FXML
    Label lifeLabel;
    @FXML
    GridPane myBoard;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lifeLabel.setText(String.valueOf(User.getLastUser().getPacmanLife()));
        createMap();
        // TODO
    }

    public void createMap() {
        answer = CreateMaze.answer(20, 20);
        InputStream inputStream = null;
        for (int i = 0; i < 41; i++) {
            for (int j = 0; j < 41; j++) {
                if (answer[i][j].equals("1")) {
                    try {
                        inputStream = new FileInputStream("C:/Users/98993/Desktop/Real Pacman/src/main/resources/project/css/Blackout-hashtag-activism (1).jpg");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        inputStream = new FileInputStream("C:/Users/98993/Desktop/Real Pacman/src/main/resources/project/css/white.png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                Image image = new Image(inputStream);
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                imageView.setFitHeight(14);
                imageView.setFitWidth(14);
                myBoard.add(imageView, i, j);
            }
        }
    }

    public void decreaseLife() {
        User.getLastUser().decreasePacmanLife();
        lifeLabel.setText(String.valueOf(User.getLastUser().getPacmanLife()));
    }

    public void increaseLife() {
        User.getLastUser().increasePacmanLife();
        lifeLabel.setText(String.valueOf(User.getLastUser().getPacmanLife()));
    }

    public void back(ActionEvent actionEvent) throws IOException {
        new MainView().changeView("/project/fxml/mainMenu.fxml");
    }

    public void saveMap(ActionEvent actionEvent) {
        User.getLastUser().saveThisMap(answer);
    }
}
