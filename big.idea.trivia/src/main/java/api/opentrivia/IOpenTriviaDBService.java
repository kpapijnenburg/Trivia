package api.opentrivia;

import question.model.Category;
import question.model.Question;

import java.util.ArrayList;
import java.util.List;

public interface IOpenTriviaDBService {
    List<Category> getCategories();
    ArrayList<Question> getQuestions();
}
