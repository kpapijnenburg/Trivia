package models;

import models.Enums.Difficulty;
import models.Enums.Type;

public class Question {

    private Category category;
    private Difficulty difficulty;
    private Type type;
    private Answer[] answers;

    //region Getters/Setter
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answer[] answers) {
        this.answers = answers;
    }
    //endregion

    public Question(){

    }

    public Question(Category category, Difficulty difficulty, Type type, Answer[] answers) {
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;
        this.answers = answers;
    }
}
