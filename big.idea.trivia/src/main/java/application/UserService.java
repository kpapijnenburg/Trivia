package main.java.application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.user.model.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UserService {
    private String name;
    private String password;
    private static String baseUrl = "http://localhost:8080/user?";
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

    public UserService(String name, String password) {
        this.name = name;
        this.password = password;
    }

    private URL buildUrl() throws MalformedURLException {
        return new URL(baseUrl
                + "name=" + name
                + "&password=" + password);
    }

    public User login() throws IOException {
        URL url = buildUrl();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        return jsonConverter.fromJson(new InputStreamReader(connection.getInputStream()), User.class);
    }
}
