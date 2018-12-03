package application;

import api.exceptions.NonUniqueUsernameException;
import api.interfaces.IUserService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import user.model.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {
    private String name;
    private String password;
    private static String baseUrl = "http://localhost:8090/user?";
    private Gson jsonConverter = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserService() {

    }


    private URL buildUrl(String name, String password) throws MalformedURLException {
        return new URL(baseUrl
                + "name=" + name
                + "&password=" + password);
    }

    public User login(String name, String password) throws IOException {
        URL url = buildUrl(name, password);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

       return jsonConverter.fromJson(new InputStreamReader(connection.getInputStream()), User.class);

    }

    public void register(User user) throws IOException, NonUniqueUsernameException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("name", user.getName()));
        params.add(new BasicNameValuePair("password", user.getPassword()));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new NonUniqueUsernameException("Username is already taken.");
        }
        client.close();

    }
}
