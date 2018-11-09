package game.model;

import user.model.User;

class Player extends User {
    private int id;
    private int strikes;
    private int score;

    //region Getter/Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStrikes() {
        return strikes;
    }

    public void setStrikes(int strikes) {
        this.strikes = strikes;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    //endregion


    public Player() {

    }

    public Player(int id, int strikes, int score) {
        this.id = id;
        this.strikes = strikes;
        this.score = score;
    }

    public Player(String name, String password, int id, int strikes, int score) {
        super(name, password);
        this.id = id;
        this.strikes = strikes;
        this.score = score;
    }
}
