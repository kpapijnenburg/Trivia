package application;

import com.google.gson.*;
import game.model.Game;
import question.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QuestionService {

    private  String baseUrl  = "http://localhost:8090/question";
    private Gson jsonConverter = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public QuestionService() throws MalformedURLException {

    }

    public List<Question> getQuestions(Game game) throws IOException {
        URL url = new URL(baseUrl + "?categoryId=" + game.getCategory().getId() + "&difficulty=" + game.getDifficulty().toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        JsonArray jsonArray = jsonConverter.fromJson(new InputStreamReader(connection.getInputStream()), JsonArray.class);

        List<Question> questions = new ArrayList<>();

        for (JsonElement element: jsonArray){
            questions.add(jsonConverter.fromJson(element, Question.class));
        }

        return questions;
    }

}
