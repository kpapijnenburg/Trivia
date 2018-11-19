package question.model;

import question.model.Enums.Type;

public class Question {

    private Category category;
    private Type type;
    private Answer[] answers;

    //region Getters/Setter
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public Question(Category category, Type type, Answer[] answers) {
        this.category = category;
        this.type = type;
        this.answers = answers;
    }
}
