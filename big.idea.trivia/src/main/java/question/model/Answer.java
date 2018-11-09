package question.model;

public class Answer {
    private String correctAnswer;
    private String[] falseAnswers;

    //region Getter/Setter
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String[] getFalseAnswers() {
        return falseAnswers;
    }

    public void setFalseAnswers(String[] falseAnswers) {
        this.falseAnswers = falseAnswers;
    }
    //endregion

    public Answer(String correctAnswer, String[] falseAnswers) {
        this.correctAnswer = correctAnswer;
        this.falseAnswers = falseAnswers;
    }

    public Answer() {

    }
}
