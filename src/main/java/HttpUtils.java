
import dto.Comment;
import dto.Tasks;
import dto.User;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.List;

public class HttpUtils {
    private static final String TEST_USERS = "https://jsonplaceholder.typicode.com/users";
    private static final String TEST_USER_POSTS = "https://jsonplaceholder.typicode.com/users/1/posts";
    private static final String TEST_POSTS = "https://jsonplaceholder.typicode.com/posts/10/comments";
    private static final String TEST_FIRST_USER = "https://jsonplaceholder.typicode.com/users/1/todos";

    public static void createUser() throws IOException {
        URL url = new URL(TEST_USERS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(Files.readAllBytes(new File("user.json").toPath()));
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        System.out.println("POST response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            SubUtils.getUserObject(response);
        } else {
            System.out.println("POST request not worked");
        }
    }

    public static void updateUser() throws IOException {
        int userId = 2;
        URL url = new URL(TEST_USERS + "/" + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setConnectTimeout(2000);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        OutputStream os = connection.getOutputStream();
        os.write(Files.readAllBytes(new File("user.json").toPath()));
        os.flush();
        os.close();

        int responseCode = connection.getResponseCode();
        System.out.println("PUT response code: " + responseCode);
        if(responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            SubUtils.getUserObject(response);
        } else {
            System.out.println("PUT request not worked");
        }
    }

    public static void deleteUser() throws IOException {
        int userId = 9;
        URL url = new URL(TEST_USERS + "/" + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        System.out.println("DELETE response code: " + responseCode);
    }

    public static List<User> getAllUsers() throws IOException {
        URL url = new URL(TEST_USERS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        System.out.println("GET response code: " + responseCode);
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
        return SubUtils.getListUsers(response);
    }

    public static void getUserById() throws IOException {
        int userId = 2;
        URL url = new URL(TEST_USERS + "/" + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        System.out.println("GET response code: " + responseCode);
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
            SubUtils.getUserObject(response);
        } else {
            System.out.println("GET request not worked");
        }
    }

    public static void getUserByUserName() throws IOException {
        String userName = "Kamren";
        URL url = new URL(TEST_USERS + "?username=" + userName);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        System.out.println("GET response code: " + responseCode);
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
            System.out.println(response);
        } else {
            System.out.println("GET request not worked");
        }
    }

    public static List<Comment> writeCommentsToFile() throws IOException {
        SubUtils utils = new SubUtils();
        int maxId = utils.getLastPost(TEST_USER_POSTS);

        URL url = new URL(TEST_POSTS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        System.out.println("GET response code: " + responseCode);
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
        return SubUtils.getListComments(response);
    }


    public static List<Tasks> getOpenTasks() throws IOException {
        URL url = new URL(TEST_FIRST_USER);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        System.out.println("GET response code: " + responseCode);
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
        return SubUtils.getTasksList(response);
    }
}
