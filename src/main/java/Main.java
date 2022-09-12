import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("createUser(): ");
        HttpUtils.createUser();

        System.out.println("\nupdateUser(): ");
        HttpUtils.updateUser();

        System.out.println("\ndeleteUser(): ");
        HttpUtils.deleteUser();

        System.out.println("\ngetAllUsers(): ");
        HttpUtils.getAllUsers();

        System.out.println("\ngetUserById(): ");
        HttpUtils.getUserById();

        System.out.println("\ngetUserByUserName(): ");
        HttpUtils.getUserByUserName();

        System.out.println("\nwriteCommentsToFile(): ");
        HttpUtils.writeCommentsToFile();

        System.out.println("\ngetOpenTasks(): ");
        HttpUtils.getOpenTasks();
    }
}
