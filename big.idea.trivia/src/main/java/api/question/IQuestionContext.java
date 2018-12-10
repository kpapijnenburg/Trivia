package api.question;

import question.model.Enums.Difficulty;
import question.model.Question;

import java.util.List;

public interface IQuestionContext {
    List<Question> getQuestions(int categoryId, Difficulty difficulty);
    boolean checkAnswer(int questionId, String answer);
}
