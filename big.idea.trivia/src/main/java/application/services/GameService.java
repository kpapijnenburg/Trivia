package application.services;

import api.interfaces.IGameService;
import game.model.MultiPlayerGame;
import game.model.SinglePlayerGame;
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
    private String baseUrl =  "http://localhost:8090/game";

    @Override
    public void saveSinglePlayer(SinglePlayerGame game) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl + "/savesingleplayer?");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("score", Integer.toString(game.getPlayer().getScore())));
        params.add(new BasicNameValuePair("userId", Integer.toString(game.getPlayer().getId())));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() != 200) {

        }
        client.close();
    }

    @Override
    public void saveMultiPlayer(MultiPlayerGame game) {

    }
}
