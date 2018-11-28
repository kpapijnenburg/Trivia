package application.uicontrollers;

import api.opentrivia.OpenTriviaDBService;
import game.model.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import question.model.Category;

import java.io.IOException;
import java.util.Objects;

public class CategoryUIController {
    public Button btn_choose;
    public ComboBox<Category> cmb_category;

    private OpenTriviaDBService openTriviaDBService;
    private Game game;

    public CategoryUIController() {
        openTriviaDBService = new OpenTriviaDBService();
    }

    public void initialize() throws IOException {
        ObservableList<Category> categories = FXCollections.observableArrayList(openTriviaDBService.getCategories());
        cmb_category.setItems(categories);
    }


    public void btnChooseClicked(ActionEvent actionEvent) throws IOException {
        game = Game.getInstance();
        Category category = cmb_category.getValue();

        if (category == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a category.");
            alert.showAndWait();
        }
        else {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("game_ui.fxml")));
            Stage stage = new Stage();
            stage.setResizable(false);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            Stage stageToClose = (Stage) btn_choose.getScene().getWindow();
            stageToClose.close();
        }
    }
}
