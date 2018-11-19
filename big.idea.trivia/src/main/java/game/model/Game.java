package game.model;


import question.model.Enums.Difficulty;
import question.model.Question;

public class Game {
    private int id;
    private Player[] players;
    private Difficulty difficulty;
    private Question[] questions;
    //region Getter/Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    //endregion


    public Game() {

    }

    public Game(int id, Player[] players, Difficulty difficulty, Question[] questions) {
        this.id = id;
        this.players = players;
        this.difficulty = difficulty;
        this.questions = questions;
    }
}
