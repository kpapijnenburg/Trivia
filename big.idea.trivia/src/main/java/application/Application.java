package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user.model.User;

import java.io.IOException;
import java.util.Objects;


public class Application extends javafx.application.Application {

    public static User currentUser;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login_ui.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Welcome to Trivia!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage openStage(String fxmlResource) throws IOException {
        Parent root;

        root = FXMLLoader.load(Objects.requireNonNull(Application.class.getClassLoader().getResource(fxmlResource)));

        Stage stage = new Stage();
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        return stage;
    }


}
