package api.opentrivia;

import com.google.gson.*;
import question.model.Category;

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
}
