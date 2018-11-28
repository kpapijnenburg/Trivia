package question.model;

import question.model.Enums.Type;

public class Question {

    private String question;
    private Category category;
    private Type type;
    private Answers answers;

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

    public Answers getAnswers() {
        return answers;
    }

    public void setAnswers(Answers answers) {
        this.answers = answers;
    }
    //endregion

    public Question() {

    }

    public Question(String question, Category category, Type type, Answers answers) {
        this.question = question;
        this.category = category;
        this.type = type;
        this.answers = answers;
    }

    public Question(String question, Answers answers) {
        this.question = question;
        this.answers = answers;
    }
}
