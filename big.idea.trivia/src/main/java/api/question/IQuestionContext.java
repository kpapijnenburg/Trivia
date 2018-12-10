package api.question;

import question.model.Answer;
import question.model.Question;

import java.sql.SQLException;
import java.util.List;

public interface IQuestionContext {
    List<Question> getQuestions(int categoryId, String difficulty) throws ClassNotFoundException, SQLException;
    Answer getCorrectAnswer(int questionId);
}
