package api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private GameRepository repository = new GameRepository(new GameSqlContext());

    @RequestMapping(value = "/game/singleplayer", method = RequestMethod.POST)
    public void saveSingePlayerGame(@RequestParam(value = "score") int score, @RequestParam("userId") int userId){
        repository.saveSinglePlayerGame(score, userId);
    }

//    private UserRepository repository = new UserRepository(new UserSqlContext());
//
//    @RequestMapping(value = "/user" , method = RequestMethod.GET)
//    public User login(@RequestParam(value = "name") String name, @RequestParam(value = "password") String password) throws IncorrectCredentialsException {
//        return repository.login(name, password);
//    }
//
//    @RequestMapping(value = "/user", method = RequestMethod.POST)
//    public void register (@RequestParam(value = "name") String name, @RequestParam(value = "password")String password) throws NonUniqueUsernameException {
//        repository.register(new User(name, password));
//    }
}
