package api.question;

import question.model.Answer;
import question.model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionSqlContext implements IQuestionContext {
    @Override
    public List<Question> getQuestions(int categoryId, String difficulty) throws ClassNotFoundException, SQLException {
        ArrayList<Question> questions = new ArrayList<>();

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", "wachtwoord");
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT TOP 3 * FROM Trivia.Question WHERE Trivia.Question.Categoryid = ? AND Trivia.Question.difficulty = ? ORDER BY NEWID()"
            );

            statement.setInt(1, categoryId);
            statement.setString(2, difficulty);

            ResultSet result = statement.executeQuery();

            // Add items to list
            while (result.next()) {
                questions.add(new Question(
                        result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        null

                ));
            }

            //Close connections
            result.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        connectAnswers(questions);

        return questions;
    }

    private void connectAnswers(ArrayList<Question> questions) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", "wachtwoord");

        for (Question question : questions) {
            ArrayList<Answer> answers = new ArrayList<>();

            try {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM Trivia.Answer WHERE Trivia.Answer.Questionid = ?"
                );

                statement.setInt(1, question.getId());

                ResultSet result = statement.executeQuery();

                // Add items to list
                while (result.next()) {
                    answers.add(new Answer(
                            result.getInt(1),
                            result.getInt(2),
                            result.getString(3),
                            result.getBoolean(4)
                    ));
                }

                Collections.shuffle(answers);
                question.setAnswers(answers);

                //Close connections
                result.close();
                statement.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        connection.close();
    }


    @Override
    public Answer getCorrectAnswer(int questionId) {
        Answer answer = null;
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", "wachtwoord");
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Trivia.Answer WHERE Trivia.Answer.Questionid = ? AND Trivia.Answer.IsCorrect = 1"
            );

            statement.setInt(1, questionId);

            ResultSet result = statement.executeQuery();

            // Add items to list
            while (result.next()) {
                answer = new Answer(
                        result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getBoolean(4)
                );
            }

            //Close connections
            result.close();
            statement.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return answer;
    }
}



