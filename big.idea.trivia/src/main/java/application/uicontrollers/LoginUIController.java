package application.uicontrollers;

import application.Application;
import application.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.model.User;

import javax.swing.*;
import java.io.IOException;

public class LoginUIController {

    public TextField txt_name;
    public Button btn_login;
    public Button btn_register;
    public PasswordField txt_password;

    @FXML
    public void btnLoginClicked(ActionEvent actionEvent) {
        // Get user input from fields
        String name = txt_name.getText();
        String password = txt_password.getText();

        // Create necessary service.
        UserService userService = new UserService();

        // Check if fields have been filled in.
        if (name.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username or password is empty");
        } else {
            // Try to login the user with the specified username and password.
            try {
                User user = userService.login(name, password);
                // Set the currentUser to the logged in user.
                Application.currentUser = user;
                JOptionPane.showMessageDialog(null, "Welcome " + user.getName() + "!");

                // After succesful login the homepage UI scene is created and loaded.
                Application.openStage("homepage_ui.fmxl");

                // the current stage is closed.
                Stage stageToClose = (Stage) btn_login.getScene().getWindow();
                stageToClose.close();

                // Catch incorrect user info errors.
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }

        }
    }

    public void btnRegisterClicked(ActionEvent actionEvent) throws IOException {
        Application.openStage("register_ui.fxml");

        Stage stageToClose = (Stage) btn_login.getScene().getWindow();
        stageToClose.close();
    }
}
