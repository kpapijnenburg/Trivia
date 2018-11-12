package main.java.application;

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


public class Application extends javafx.application.Application {
    public TextField txt_name;
    public Button btn_login;
    public TextField txt_password;



    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Application.class.getClassLoader().getResource("main/resources/login_ui.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void btnClicked(ActionEvent actionEvent) throws IOException {
        String name = txt_name.getText();
        String password = txt_password.getText();

        UserService userService = new UserService(name,password);

        try {
            User user = userService.login();
            JOptionPane.showMessageDialog(null, "Welcome " + user.getName() + "!" );
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"Username or password incorrect");
        }
    }
}
