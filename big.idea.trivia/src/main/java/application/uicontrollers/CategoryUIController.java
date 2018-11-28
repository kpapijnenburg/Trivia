package application.uicontrollers;

import api.opentrivia.OpenTriviaDBService;
import application.Application;
import game.model.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import question.model.Category;

import java.io.IOException;

public class CategoryUIController {
    private Application application;

    public Button btn_choose;
    public ComboBox<Category> cmb_category;

    private OpenTriviaDBService openTriviaDBService;
    private Game game;

    public CategoryUIController() {
        openTriviaDBService = new OpenTriviaDBService();
    }

    public void initialize() throws IOException {
        this.application = Application.getInstance();

        ObservableList<Category> categories = FXCollections.observableArrayList(openTriviaDBService.getCategories());
        cmb_category.setItems(categories);
    }


    public void btnChooseClicked(ActionEvent actionEvent) throws IOException {
        game = Game.getInstance();
        Category category = cmb_category.getValue();

        if (category == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a category.");
            alert.showAndWait();
        } else {
            application.openStage("game_ui.fxml");

            Stage stageToClose = (Stage) btn_choose.getScene().getWindow();
            stageToClose.close();
        }
    }
}
