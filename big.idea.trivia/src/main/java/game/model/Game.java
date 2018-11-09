package game.model;

public class Game {
    private int id;
    private Player[] players;

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
    //endregion


    public Game() {

    }

    public Game(int id, Player[] players) {
        this.id = id;
        this.players = players;
    }
}
