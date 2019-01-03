package api.interfaces;

import game.model.MultiPlayerGame;
import game.model.SinglePlayerGame;

import java.io.IOException;

public interface IGameService {
    void saveSinglePlayer(SinglePlayerGame game) throws IOException;
    void saveMultiPlayer(MultiPlayerGame game);
}
