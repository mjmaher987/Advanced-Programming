package project.View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import project.model.User;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    // @FXML
    // private static Label upLabel;
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Label downLabel;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //
    }

    public void loginUser(ActionEvent ev) {
//        if (usernameField.getText().equals("") || passwordField.getText().equals("")) {
//            downLabel.setStyle("-fx-text-fill:red;-fx-padding:4 0 8 0;-fx-font-weight:bold");
//            downLabel.setText("FILL FIELDS");
//            return;
//        }
//        if (Storage.getUserByName(usernameField.getText()) == null) {
//            downLabel.setStyle("-fx-text-fill:red;-fx-padding:4 0 8 0;-fx-font-weight:bold");
//            downLabel.setText("THIS USER DOES NOT EXIST");
//        } else if (Storage.getUserByName(usernameField.getText()).getPassword().equals(passwordField.getText())) {
//            downLabel.setStyle("-fx-text-fill:red;-fx-padding:4 0 8 0;-fx-font-weight:bold");
//            downLabel.setText("INCORRECT PASSWORD");
//        }
//        User.setOnlineUser(Storage.getUserByName(usernameField.getText()));
        new User(usernameField.getText(), passwordField.getText());
        try {
            new MainView().changeView("/project/fxml/mainMenu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
