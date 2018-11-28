package question.model;

import java.util.ArrayList;

public class Answers {
    private String correctAnswer;
    private ArrayList<String> falseAnswers;

    //region Getter/Setter
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<String> getFalseAnswers() {
        return falseAnswers;
    }

    public void setFalseAnswers(ArrayList<String> falseAnswers) {
        this.falseAnswers = falseAnswers;
    }
    //endregion

    public Answers(String correctAnswer, ArrayList<String> falseAnswers) {
        this.correctAnswer = correctAnswer;
        this.falseAnswers = falseAnswers;
    }

    public Answers() {

    }
}
