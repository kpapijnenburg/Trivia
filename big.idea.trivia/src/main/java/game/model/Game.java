package game.model;


import question.model.Category;
import question.model.Enums.Difficulty;
import question.model.Question;

import java.util.ArrayList;

public class Game {

    private int id;
    private ArrayList<Player> players;
    private Difficulty difficulty;
    private ArrayList<Question> questions;
    private Category category;
    //region Getter/Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //endregion

    private static Game instance = null;

    public Game() {
        players = new ArrayList<>();
        questions = new ArrayList<>();
    }

    public Game(int id, Difficulty difficulty, ArrayList<Question> questions) {
        this.id = id;
        this.players = new ArrayList<>();
        this.difficulty = difficulty;
        this.questions = questions;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }

        return instance;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }
}
