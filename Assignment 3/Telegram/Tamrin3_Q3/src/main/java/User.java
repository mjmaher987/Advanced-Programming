import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private static ArrayList<User> allUsers;
    static {
        allUsers = new ArrayList<>();
    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public static User getUserByUsername(String username) {
        for (User allUser : allUsers) {
            if (allUser.getUsername() == username) return allUser;
        }
        return null;
    }

    private String getUsername(){
        return this.username;
    }

    private String getPassword(){
        return this.password;
    }

    public static boolean doesUsernameAndPasswordMatch(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) return false;
        return user.getPassword().equals(password);
    }

    public static boolean isThisUsernameValid(String username) {
        return allUsers.contains(getUserByUsername(username));
    }
}
