package api.question;

import question.model.Enums.Difficulty;
import question.model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionSqlContext implements IQuestionContext {
    //todo conext implementeren.

    @Override
    public List<Question> getQuestions(int categoryId, Difficulty difficulty) {
        ArrayList<Question> questions = new ArrayList<>();

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", "wachtwoord");
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Trivia.Question\n" +
                            "INNER JOIN Trivia.Answer ON Trivia.Answer.Questionid = Trivia.Question.id\n" +
                            "WHERE Trivia.Question.Categoryid = ?" +
                            "AND Trivia.Question.difficulty = ?"
            );

//            statement.setInt(1, categoryId);
//            statement.setString(2, difficulty.toString());
//
//            ResultSet result = statement.executeUpdate();
//
//            // Add items to list
//            while (result.next()) {
//                questions.add(new Question(
//
//                ));
//            }

            // Close connections
//            result.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    @Override
    public boolean checkAnswer(int questionId, String answer) {
        return false;
    }
}
