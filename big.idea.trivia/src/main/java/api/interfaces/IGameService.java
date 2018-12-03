package api.interfaces;

import game.model.Game;

import java.io.IOException;

public interface IGameService {
    public void saveSinglePlayer(Game game) throws IOException;
    public void saveMultiPlayer(Game game);
}
