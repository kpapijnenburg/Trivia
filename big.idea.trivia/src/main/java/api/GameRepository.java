package api;

public class GameRepository {
    private final IGameContext context;

    public GameRepository(IGameContext context) {
        this.context = context;
    }

    public void saveSinglePlayerGame(int score, int userId) {
        context.saveSingePlayerGame(score, userId);
    }
}
