package game.model;


import question.model.Category;
import question.model.Enums.Difficulty;
import question.model.Question;

import java.util.ArrayList;

public class Game {

    private int id;
    private Difficulty difficulty;
    private ArrayList<Question> questions;
    private Category category;
    //region Getter/Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //endregion

    private static Game instance = null;

    Game() {
        questions = new ArrayList<>();
    }

    Game(int id, Difficulty difficulty, ArrayList<Question> questions) {
        this.id = id;
        this.difficulty = difficulty;
        this.questions = questions;
    }



}
