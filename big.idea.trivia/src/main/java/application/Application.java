package application;

import javafx.event.ActionEvent;
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
    public TextField txt_registerPage_password;
    public TextField txt_registerPage_name;
    public Button btn_registerPage_register;
    public Button btn_registerPage_back;

    private Scene login,register, homepage;

    private User currentUser;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login_ui.fxml")));
        this.login = new Scene(root, 387, 157);
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(login);
        primaryStage.show();
    }

    @FXML
    public void btnLoginClicked() throws IOException {
        // Get user input from fields
        String name = txt_name.getText();
        String password = txt_password.getText();

        // Create necessary service.
        UserService userService = new UserService();

        // Check if fields have been filled in.
        if (name.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username or password is empty");
        }
        
        else {
        // Try to login the user with the specified username and password.
            try {
                User user = userService.login(name, password);
                // Set the currentUser to the logged in user.
                currentUser = user;
                JOptionPane.showMessageDialog(null, "Welcome " + user.getName() + "!");
            // Catch incorrect user info errors.
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Username or password incorrect");
            } finally {
            // After succesful login the homepage UI scene is created and loaded.
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homepage_ui.fxml")));
                Stage stage = new Stage();
                this.homepage = new Scene(root);
                stage.setScene(homepage);
                stage.show();
                
            // the current stage is closed.
                Stage stageToClose = (Stage) btn_login.getScene().getWindow();
                stageToClose.close();
            }
        }
    }

    public void btnRegisterClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("register_ui.fxml")));
        Stage stage = new Stage();
        this.register = new Scene(root);
        stage.setScene(register);
        stage.show();

        Stage stageToClose = (Stage) btn_login.getScene().getWindow();
        stageToClose.close();
    }

    public void btnRegisterRegisterPageClicked(ActionEvent actionEvent) {
        String name = txt_registerPage_name.getText();
        String password = txt_registerPage_password.getText();

        User user = new User(name, password);

        UserService userService = new UserService();

        try {
            userService.register(user);
            JOptionPane.showMessageDialog(null, "User succesfully registered.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
