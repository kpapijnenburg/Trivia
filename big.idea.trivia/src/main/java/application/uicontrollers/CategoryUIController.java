package application.uicontrollers;

import application.Application;
import game.model.Game;
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
    public ComboBox<String> cmb_category;

    private Game game;
    private ObservableList<Category> categories;

    public CategoryUIController() {

    }

    public void initialize() throws IOException {
        this.application = Application.getInstance();

        //todo categroy repository maken.
//        game = Game.getInstance();
//        categories = FXCollections.observableArrayList(openTriviaDBService.getCategories());
//
//        ArrayList<String> names = new ArrayList<>();
//
//        for (Category category: categories){
//            names.add(category.getName());
//        }
//
//        ObservableList<String> observableList = FXCollections.observableList(names);
//
//        cmb_category.setItems(observableList);
    }


    public void btnChooseClicked(ActionEvent actionEvent) throws IOException {
        String name = cmb_category.getValue();

        Category category = null;

        for (Category categoryInList: categories){
            if (categoryInList.getName().equals(name)){
                category = categoryInList;
            }
        }

        game.setCategory(category);

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
