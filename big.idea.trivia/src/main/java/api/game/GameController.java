package api.game;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private GameRepository repository = new GameRepository(new GameSqlContext());

    @RequestMapping(value = "/game/savesingleplayer", method = RequestMethod.POST)
    public void saveSingePlayerGame(@RequestParam(value = "score") int score, @RequestParam("userId") int userId){
        repository.saveSinglePlayerGame(score, userId);
    }

}
