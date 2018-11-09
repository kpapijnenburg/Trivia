package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.model.User;


public class Application extends javafx.application.Application {
    public TextField txt_name;
    public Button btn_login;
    public TextField txt_password;


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login_ui.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void btnClicked(ActionEvent actionEvent) {
        String name = txt_name.getText();
        String password = txt_password.getText();

        User user = new User(name, password);



    }
}
