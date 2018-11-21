package application.uicontrollers;

import api.exceptions.NonUniqueUsernameException;
import application.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.model.User;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class RegisterUIController {
    public TextField txt_name;
    public PasswordField txt_password;
    public Button btn_register;
    public Button btn_back;


    public void btnRegisterClicked(ActionEvent actionEvent) {
        String name = txt_name.getText();
        String password = txt_password.getText();

        User user = new User(name, password);

        UserService userService = new UserService();

        try {
            userService.register(user);
            JOptionPane.showMessageDialog(null, "User successfully registered.");

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login_ui.fxml.fxml")));
            Stage stage = new Stage();
            stage.setResizable(false);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                NonUniqueUsernameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void btnBackClicked(ActionEvent actionEvent) {

    }
}
