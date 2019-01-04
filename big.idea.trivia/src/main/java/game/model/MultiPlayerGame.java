package game.model;

import question.model.Enums.Difficulty;
import question.model.Question;

import java.util.ArrayList;

public class MultiPlayerGame extends Game {

    private Player playerA;
    private Player PlayerB;

    public Player getPlayerA() {
        return playerA;
    }

    public void setPlayerA(Player playerA) {
        this.playerA = playerA;
    }

    public Player getPlayerB() {
        return PlayerB;
    }

    private static MultiPlayerGame instance = null;

    public static MultiPlayerGame getInstance() {
        if (instance == null) {
            instance = new MultiPlayerGame();
        }

        return instance;
    }

    public void setPlayerB(Player playerB) {
        PlayerB = playerB;
    }

    public MultiPlayerGame() {

    }

    public MultiPlayerGame(int id, Difficulty difficulty, ArrayList<Question> questions, Player playerA, Player playerB) {
        super(id, difficulty, questions);
        this.playerA = playerA;
        PlayerB = playerB;
    }

    @Override
    public String toString() {
        try {
            return this.playerA.getName() + "'s game " + "difficulty: " + this.getDifficulty();
        } catch (Exception e) {
            return "Error with game.";
        }
    }
}
