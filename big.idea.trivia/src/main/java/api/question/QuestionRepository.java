package api.question;

import question.model.Answer;
import question.model.Enums.Difficulty;
import question.model.Question;

import java.sql.SQLException;
import java.util.List;

public class QuestionRepository {
    private IQuestionContext context;

    public QuestionRepository(IQuestionContext context) {
        this.context = context;
    }

    public List<Question> getQuestions(int categoryId, Difficulty difficulty) throws SQLException, ClassNotFoundException {
        return context.getQuestions(categoryId, difficulty);
    }

    public boolean checkAnswer(int questionId, String answer){
        Answer answerFromDb = context.getCorrectAnswer(questionId);

        return answer.equals(answerFromDb.getAnswer());
    }
}
