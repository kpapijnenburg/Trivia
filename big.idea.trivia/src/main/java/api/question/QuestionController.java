package api.question;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import question.model.Question;

import java.sql.SQLException;
import java.util.List;

@RestController
public class QuestionController {

    private QuestionRepository repository = new QuestionRepository(new QuestionSqlContext());

    @RequestMapping(value = "/question" , method = RequestMethod.GET)
    public List<Question> getQuestion(@RequestParam(value = "categoryId") int categoryId, @RequestParam(value = "difficulty") String difficulty) throws SQLException, ClassNotFoundException {
        return repository.getQuestions(categoryId, difficulty);
    }

}

//    private UserRepository repository = new UserRepository(new UserSqlContext());
//
//    @RequestMapping(value = "/user" , method = RequestMethod.GET)
//    public User login(@RequestParam(value = "name") String name, @RequestParam(value = "password") String password) throws IncorrectCredentialsException {
//        return repository.login(name, password);
//    }

