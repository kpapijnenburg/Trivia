package api;


import api.exceptions.IncorrectCredentialsException;
import api.exceptions.NonUniqueUsernameException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import user.model.User;

@RestController
public class UserController {

    private UserRepository repository = new UserRepository(new UserSqlContext());

    @RequestMapping(value = "/user" , method = RequestMethod.GET)
    public User login(@RequestParam(value = "name") String name, @RequestParam(value = "password") String password) throws IncorrectCredentialsException {
        return repository.login(name, password);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void register (@RequestParam(value = "name") String name, @RequestParam(value = "password")String password) throws NonUniqueUsernameException {
        repository.register(new User(name, password));
    }
}
