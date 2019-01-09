package api.User;


import api.exceptions.IncorrectCredentialsException;
import user.model.User;
import websocket.shared.Message;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
        // todo hash password.
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

    @Override
    public User getByCredentials(String name, String password) throws IncorrectCredentialsException {
        User user = null;

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", "wachtwoord");
            PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM Trivia.[User] WHERE [Password] = ? AND  [Name] = ?"
            );

            statement.setString(1, name);
            statement.setString(2, password);

            // Execute query
            ResultSet result = statement.executeQuery();

            // Add items to list
            while (result.next()) {
                user = new User(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("password")
                );
            }

            // Close connections
            result.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        if (user != null) {
            return user;
        } else {
            throw new IncorrectCredentialsException("Incorrect username or password.");
        }
    }

    byte[] hashPassword(String password){

        byte[] hashedPassword = null;

        // Create a SALT
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);

        hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashedPassword;

    }

}
