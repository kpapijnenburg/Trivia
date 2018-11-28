package api.opentrivia;

import com.google.gson.*;
import game.model.Game;
import question.model.Answers;
import question.model.Category;
import question.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OpenTriviaDBService {
    private Gson jsonConverter = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public List<Category> getCategories() throws IOException {
        URL url = new URL("https://opentdb.com/api_category.php");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        JsonObject json = jsonConverter.fromJson(new InputStreamReader(connection.getInputStream()), JsonObject.class);

        List<Category> categories = new ArrayList<>();

        for (JsonElement element : json.getAsJsonArray("trivia_categories")) {
            if (element.isJsonObject()) {
                JsonObject object = (JsonObject) element;
                int id = object.get("id").getAsInt();
                String name = object.get("name").getAsString();

                categories.add(new Category(id, name));
            }
        }

        return categories;
    }

    public ArrayList<Question> getQuestions(Game game) throws IOException {
        int category = game.getCategory().getId();
        String difficulty = game.getDifficulty().toString().toLowerCase();

        URL url = new URL("https://opentdb.com/api.php?amount=3&category=" + category + "&difficulty=" + difficulty + "&type=multiple");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        JsonObject json = jsonConverter.fromJson(new InputStreamReader(connection.getInputStream()), JsonObject.class);

        ArrayList<Question> questions = new ArrayList<>();

        for (JsonElement element: json.getAsJsonArray("results")){
            JsonObject object = (JsonObject) element;

            String question = object.get("question").getAsString();
            String correctAnswer = object.get("correct_answer").getAsString();
            ArrayList<String> incorrectAnswers = new ArrayList<>();

            for (JsonElement answer: object.getAsJsonArray("incorrect_answers")){
                incorrectAnswers.add(answer.toString());
            }

            Answers answers = new Answers(correctAnswer, incorrectAnswers);

            questions.add(new Question(question, answers));
        }

        return questions;
    }
}
