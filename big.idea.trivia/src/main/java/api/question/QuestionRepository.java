package api.question;

import question.model.Enums.Difficulty;
import question.model.Question;

import java.util.List;

public class QuestionRepository {
    IQuestionContext context;

    public QuestionRepository(IQuestionContext context) {
        this.context = context;
    }

    public List<Question> getAll(int categoryId, Difficulty difficulty){
        return context.getQuestions(categoryId, difficulty);
    }

    public boolean checkAnswer(int questionId, String answer){
        return context.checkAnswer(questionId, answer);
    }
}
