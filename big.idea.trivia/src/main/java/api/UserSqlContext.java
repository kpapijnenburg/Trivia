package api;


import user.model.User;

import java.sql.*;
import java.util.ArrayList;

public class UserSqlContext implements IUserContext {

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<>();

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", "wachtwoord");
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Trivia.[User]";

            // Execute query
            ResultSet result = statement.executeQuery(query);

            // Add items to list
            while (result.next()) {
                users.add(new User(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("password")
                ));
            }

            // Close connections
            result.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public boolean create(User user) {
        boolean result = false;

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", "wachtwoord");
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Trivia.[User]" +
                            " VALUES (?, ?)"
            );

            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());

            result = statement.executeUpdate() > 0;


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
