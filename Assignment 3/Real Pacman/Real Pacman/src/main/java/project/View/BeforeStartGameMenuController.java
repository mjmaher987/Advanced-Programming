package project.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import project.model.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class BeforeStartGameMenuController implements Initializable {
    private User user;
    private String[][] currentMapAsString;
    private int mapNumber;
    private int totalMapNumber;
    @FXML
    Button previousMapButton;
    @FXML
    Button nextMapButton;
    @FXML
    Button backButton;
    @FXML
    Button startButton;
    @FXML
    Label mapNumberLabel;
    @FXML
    GridPane board;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.user = User.getLastUser();
        totalMapNumber = user.getNumberOfSavedMaps();
        if (totalMapNumber > 0) {
            createMaze(0);
        }
    }

    private void createMaze(int number) {
        mapNumber = number;
        mapNumberLabel.setText(String.valueOf(number + 1));
        currentMapAsString = user.getSavedMapByNumber(number);
        InputStream inputStream = null;
        for (int i = 0; i < 41; i++) {
            for (int j = 0; j < 41; j++) {
                if (currentMapAsString[i][j].equals("1")) {
                    try {
                        inputStream = new FileInputStream("C:/Users/98993/Desktop/Real" +
                                " Pacman/src/main/resources/project/css/Blackout-hashtag-activism (1).jpg");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        inputStream = new FileInputStream("C:/Users/98993/Desktop/Real " +
                                "Pacman/src/main/resources/project/css/white.png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                Image image = new Image(inputStream);
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                imageView.setFitHeight(14);
                imageView.setFitWidth(14);
                board.add(imageView, i, j);
            }
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        new MainView().changeView("/project/fxml/mainMenu.fxml");
    }

    public void start(ActionEvent actionEvent) {
        if (totalMapNumber > 0) {
            user.setLastMapOnline(currentMapAsString);
            System.out.println("Let's go");
        }
    }

    public void previousMap(ActionEvent actionEvent) {
        if (mapNumber > 0) {
            createMaze(mapNumber - 1);
        }
    }

    public void nextMap(ActionEvent actionEvent) {
        if (totalMapNumber - 1 > mapNumber) {
            createMaze(mapNumber + 1);
        }
    }
}
