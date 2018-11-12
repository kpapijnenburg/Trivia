package main.java.api;


import main.java.user.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserRepository repository = new UserRepository(new UserSqlContext());

    @RequestMapping(value = "/user" , method = RequestMethod.GET)
    public User login(@RequestParam(value = "name") String name, @RequestParam(value = "password") String password) throws Exception {
        return repository.login(name, password);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void register (@RequestParam(value = "name") String name, @RequestParam(value = "password")String password){
        try{
        repository.register(name, password);
        } catch (Exception e){
            throw e;
        }
    }
}
