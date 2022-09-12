import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dto.Comment;
import dto.Post;
import dto.Tasks;
import dto.User;
import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class SubUtils {
    public static List<User> getListUsers(StringBuffer response) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<User>>(){}.getType();
        List<User> userList = gson.fromJson(String.valueOf(response), type);
        Gson gs = new GsonBuilder().setPrettyPrinting().create();
        String json = gs.toJson(userList);
        System.out.println(json);
        return gson.fromJson(String.valueOf(response), type);
    }

    public static void getUserObject (StringBuffer response) {
        Type typeToken = TypeToken
                .getParameterized(User.class, HttpUtils.class)
                .getType();
        User userObject = new Gson().fromJson(String.valueOf(response), typeToken);
        Gson gs = new GsonBuilder().setPrettyPrinting().create();
        String json = gs.toJson(userObject);

        System.out.println(json);
    }

    public static List<Tasks> getTasksList(StringBuffer response) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Tasks>>(){}.getType();
        List<Tasks> listTasks = gson.fromJson(String.valueOf(response), type);

        //find open task
        List<Tasks> openTasks = listTasks.stream()
                .filter(it -> it.getCompleted() == "false")
                .collect(Collectors.toList());

        Gson gs = new GsonBuilder().setPrettyPrinting().create();
        String json = gs.toJson(openTasks);

        System.out.println(json);
        return gson.fromJson(String.valueOf(response), type);
    }

    public static List<Comment> getListComments(StringBuffer response) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Comment>>(){}.getType();
        List<Comment> userList = gson.fromJson(String.valueOf(response), type);
        Gson gs = new GsonBuilder().setPrettyPrinting().create();
        String json = gs.toJson(userList);
        System.out.println(json);

        try(BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter("user-1-post-10-comments.json"))) {
            bufferedWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.fromJson(String.valueOf(response), type);
    }

    public int getLastPost(String url) throws IOException {
        URL urlToPost = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlToPost.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        StringBuffer response = new StringBuffer();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } else {
            System.out.println("GET request not worked");
        }

        //convert json => Java object
        Type typeToken = TypeToken
                .getParameterized(List.class, Post.class)
                .getType();
        List<Post> postsList = new Gson().fromJson(String.valueOf(response), typeToken);

        //find lastPost
        Post lastPost = postsList.stream()
                .max(Comparator.comparing(Post::getId))
                .orElseThrow(NoSuchElementException::new);
        System.out.println("MaxID: " + lastPost.getId());
        System.out.println("Last post: " + lastPost);

        int lastPostId = lastPost.getId();
        return lastPostId;
    }
}


