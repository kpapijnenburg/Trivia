package question;

import api.question.QuestionRepository;
import org.junit.Assert;
import org.junit.Test;
import question.model.Enums.Difficulty;
import question.model.Question;

import java.util.ArrayList;

public class QuestionRepositoryTests {
    ArrayList<Question> questions;
    QuestionRepository repository = new QuestionRepository(new QuestionTestContext());

    @Test
    public void getQuestions_ShouldReturnThreeQuestions_WhenInvoked(){
        ArrayList<Question> questions = (ArrayList<Question>) repository.getAll(1, Difficulty.EASY);

        Assert.assertEquals(3, questions.size());
    }

    @Test
    public void checkAnswer_ShouldReturnTrue_WhenGivenCorrectAnswer(){
        String answer = "testTrue";
        boolean result;

        result = repository.checkAnswer(1, answer);

        Assert.assertTrue(result);

    }

    @Test
    public void checkAnswer_ShouldReturnTrue_WhenGivenIncorrectAnswer(){
        String answer = "testFalse";
        boolean result;

        result = repository.checkAnswer(1, answer);

        Assert.assertFalse(result);
    }

}

//    @Test
//    public void Login_ShouldLoginUser_WhenUsingStandardUser() {
//        Exception e = null;
//
//        try {
//            userRepository.login(user.getName(), user.getPassword());
//        } catch (Exception ex) {
//            e = ex;
//        }
//
//        Assert.assertNull(e);
//
//    }
