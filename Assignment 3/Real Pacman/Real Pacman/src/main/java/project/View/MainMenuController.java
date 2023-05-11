package project.View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import project.model.User;

public class MainMenuController implements Initializable {

    @FXML
    Button backToLoginPagebtn;
    @FXML
    Button importAndExportbtn;
    @FXML
    Button scoreboardBtn;
    @FXML
    Button profilebtn;
    @FXML
    Button shopbtn;
    @FXML
    Button deckbtn;
    @FXML
    Button duelbtn;
    @FXML
    Label userShow;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        userShow.setText("Username : " + User.getLastUser().getUsername());
        // TODO
    }

    public void backToLoginPage() throws IOException {
        new MainView().changeView("/project/fxml/loginPage.fxml");
    }

    public void goToProfile() throws IOException {
        new MainView().changeView("/project/fxml/profile.fxml");
    }

    public void scoreboardPage() throws IOException {
        new MainView().changeView("/project/fxml/scoreboardPage.fxml");
    }

    public void gotoSettings() throws IOException {
        new MainView().changeView("/project/fxml/settingsPage.fxml");
    }

    public void back() throws IOException {
        new MainView().changeView("/project/fxml/loginPage.fxml");
    }

    public void startGame() throws IOException {
        new MainView().changeView("/project/fxml/beforeStartGameMenu.fxml");
    }

}
