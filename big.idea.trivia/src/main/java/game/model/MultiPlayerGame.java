package game.model;

import question.model.Enums.Difficulty;
import question.model.Question;

import java.util.ArrayList;

public class MultiPlayerGame extends Game {

    private String gameName;
    private Player playerA;
    private Player PlayerB;
    //todo gamestates toevoegen.
    private static MultiPlayerGame instance = null;

    public String getGameName() {
        if (gameName == null) {
            setGameName();
            return gameName;
        } else {
            return gameName;
        }
    }

    private void setGameName() {
        if (playerA != null) {
            this.gameName = playerA.getName() + "'s game";
        }
    }

    public Player getPlayerA() {
        return playerA;
    }

    public void setPlayerA(Player playerA) {
        this.playerA = playerA;
    }

    public Player getPlayerB() {
        return PlayerB;
    }

    public static void setInstance(MultiPlayerGame instance){
        MultiPlayerGame.instance = instance;
    }

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
        if (playerA != null) {
            try {
                return this.playerA.getName() + "'s game " + "difficulty: " + this.getDifficulty();
            } catch (Exception e) {
                e.printStackTrace();
                return "Error with game.";
            }
        }
        else return "";
    }
}
