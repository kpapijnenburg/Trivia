package api.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameSqlContext implements IGameContext {
    @Override
    public void saveSingePlayerGame(int score, int userId) {
        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", "wachtwoord");
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Trivia.SingleplayerGame" +
                            " VALUES (?, ?)"
            );

            statement.setInt(1,score);
            statement.setInt(2, userId);

            statement.executeUpdate();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
