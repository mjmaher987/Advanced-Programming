package project.model;

import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class User {
    private static ArrayList<User> allUsers;
    static {
        allUsers = new ArrayList<>();
    }
    private String username;
    private String password;
    private String[][] lastMapOnline;
    private int pacmanLife;
    private String[][][] allSavedMaps;
    private int numberOfSavedMaps;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.pacmanLife = 3;
        this.numberOfSavedMaps = 0;
        this.allSavedMaps = new String[10][][];
        allUsers.add(this);
    }

    public User findUserByUsername(String username) {
        return allUsers.get(allUsers.indexOf(username));
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getPacmanLife() {
        return this.pacmanLife;
    }

    public void increasePacmanLife() {
        if (this.pacmanLife < 5) this.pacmanLife += 1;
    }

    public void decreasePacmanLife() {
        if (this.pacmanLife > 2) this.pacmanLife -= 1;
    }

    public static User getLastUser() {
        return allUsers.get(allUsers.size() - 1);
    }


    public void saveThisMap(String[][] myBoard) {
        this.allSavedMaps[numberOfSavedMaps] = myBoard;
        this.numberOfSavedMaps++;
    }

    public int getNumberOfSavedMaps() {
        return this.numberOfSavedMaps;
    }

    public String[][] getSavedMapByNumber(int number) {
        return allSavedMaps[number];
    }

    public void setLastMapOnline(String[][] lastMap) {
        this.lastMapOnline = lastMap;
    }

    public String[][] getLastMapOnline() {
        return this.lastMapOnline;
    }
}
