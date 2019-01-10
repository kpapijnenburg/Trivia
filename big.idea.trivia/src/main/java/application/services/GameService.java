package application.services;

import api.interfaces.IGameService;
import application.model.MultiPlayerGame;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameService implements IGameService {
    private String baseUrl = "http://localhost:8090/game";

    @Override
    public void saveSinglePlayer(int score, int userId) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl + "/savesingleplayer?");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("score", Integer.toString(score)));
        params.add(new BasicNameValuePair("userId", Integer.toString(userId)));

        try {

            httpPost.setEntity(new UrlEncodedFormEntity(params));

            CloseableHttpResponse response = client.execute(httpPost);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveMultiPlayer(MultiPlayerGame game) {

    }

}
