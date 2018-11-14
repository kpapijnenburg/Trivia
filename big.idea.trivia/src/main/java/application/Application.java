package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.user.model.User;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;


public class Application extends javafx.application.Application {
    public TextField txt_name;
    public Button btn_login;
    public TextField txt_password;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login_ui.fxml")));

        Scene scene = new Scene(root, 387, 157);

        primaryStage.setTitle("Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void btnClicked() {
        String name = txt_name.getText();
        String password = txt_password.getText();

        UserService userService = new UserService();

        if (name.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username or password is empty");
        }

        else {

            try {
                User user = userService.login(name, password);
                JOptionPane.showMessageDialog(null, "Welcome " + user.getName() + "!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Username or password incorrect");
            }
        }
    }
}
