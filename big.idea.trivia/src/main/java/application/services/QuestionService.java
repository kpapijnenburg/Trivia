package application.services;

import com.google.gson.*;
import game.model.Game;
import game.model.MultiPlayerGame;
import org.apache.commons.lang.StringEscapeUtils;
import question.model.Answer;
import question.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class QuestionService {

    private  String baseUrl  = "http://localhost:8090/question";
    private Gson jsonConverter = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public QuestionService() {

    }

    public List<Question> getQuestions(Game game) throws IOException {
        URL url = new URL(baseUrl + "s?categoryId=" + game.getCategory().getId() + "&difficulty=" + game.getDifficulty().toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        JsonArray jsonArray = jsonConverter.fromJson(new InputStreamReader(connection.getInputStream()), JsonArray.class);

        List<Question> questions = new ArrayList<>();

        for (JsonElement element: jsonArray){
            questions.add(jsonConverter.fromJson(element, Question.class));
        }

        for (Question question: questions){
            for (Answer answer: question.getAnswers()){
                StringEscapeUtils.unescapeHtml(answer.getAnswer());
            }
        }

        return questions;
    }

    public boolean checkAnswer(int questionId, String answer) throws IOException {
        String paramValue = "/check?questionId=" + questionId + "&answer=" + URLEncoder.encode(answer, "UTF-8");
        String urlString = baseUrl + paramValue;

        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        return jsonConverter.fromJson(new InputStreamReader(connection.getInputStream()), boolean.class);
    }

    public Question getQuestion(MultiPlayerGame game) throws IOException {
        URL url = new URL(baseUrl + "?categoryId=" + game.getCategory().getId() + "&difficulty=" + game.getDifficulty().toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        Question question = jsonConverter.fromJson(new InputStreamReader(connection.getInputStream()), Question.class);

        for (Answer answer: question.getAnswers()){
            StringEscapeUtils.unescapeHtml(answer.getAnswer());
        }

        return question;
    }
}
