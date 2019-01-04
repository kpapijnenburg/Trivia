package game.model;


import user.model.User;

public class Player extends User {
    private int playeId;
    private int strikes;
    private int score;

    //region Getter/Setter


    public int getPlayeId() {
        return playeId;
    }

    public void setPlayeId(int playeId) {
        this.playeId = playeId;
    }

    public int getStrikes() {
        return strikes;
    }

    public void setStrikes(int strikes) {
        this.strikes += strikes;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }
    //endregion


    public Player() {

    }

    public Player(int playerId, int strikes, int score) {
        this.playeId = playerId;
        this.strikes = strikes;
        this.score = score;
    }

    public Player(String name,  int strikes, int score) {
        super(name);
        this.playeId = super.getId();
        this.strikes = strikes;
        this.score = score;
    }
}
