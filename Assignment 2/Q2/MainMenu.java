import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    Scanner scanner;

    public MainMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run(String username) {
        boolean logoutBoolean = false;
        while (!logoutBoolean) {
            String scannedString = scanner.nextLine();
            if (gotoNewGameMenu(scannedString)) {
                newGameMenu(scannedString, username);
            } else if (gotoListUsersMenu(scannedString)) {
                list_users();
            } else if (gotoScoreBoardMenu(scannedString)) {
                scoreBoard();
            } else if (gotoShopMenu(scannedString)) {
                shop(username);
            } else if (gotoHelpMenu(scannedString)) {
                helpMenu();
            } else if (gotoLogoutMenu(scannedString)) {
                logoutBoolean = true;
                logout();
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void list_users() {
        HashMap<String, String> allPlayers = new HashMap<>();
        allPlayers = Player.getAllPlayersString();
        ArrayList<String> users = new ArrayList<>();
        for (String user : allPlayers.keySet()) {
            users.add(user);
        }
        Collections.sort(users);
        for (String user : users) {
            System.out.println(user);
        }
    }

    private void newGameMenu(String scannedString, String username) {
        Matcher matcher = matcherFinder(scannedString, "^new_game (\\S+)$");
        String enteredUsername = "";
        if (matcher.find()) {
            enteredUsername = matcher.group(1);
        }
        if (!booleanMatcherFinder(enteredUsername, "^([a-zA-Z0-9_]+)$")) {
            System.out.println("username format is invalid");
        } else if (enteredUsername.equals(username)) {
            System.out.println("you must choose another player to start a game");
        } else if (!Player.doesItExists(enteredUsername)) {
            System.out.println("no user exists with this username");
        } else {
            System.out.print("new game started successfully between ");
            System.out.println(username + " and " + enteredUsername);
            new Game(username, enteredUsername, scanner).run();
        }
    }

    private void scoreBoard() {
        ArrayList<Player> players = new ArrayList<Player>();
        players = Player.getALlPlayersPlayer();
        Player[] all = new Player[players.size()];
        for (int i = 0; i < players.size(); i++) {
            all[i] = players.get(i);
        }
        sort(all);
        for (Player player : all) {
            System.out.print(player.getUsername() + " ");
            System.out.print(player.getScore() + " ");
            System.out.print(player.getWinNumber() + " ");
            System.out.print(player.getDrawsNumber() + " ");
            System.out.println(player.getLossesNumber());
        }
    }

    private Player[] sort(Player[] all) {
        for (int i = 0; i < all.length; i++) {
            for (int j = i + 1; j < all.length; j++) {
                if (all[i].getScore() < all[j].getScore()) swap(all, i, j);
                else if (all[i].getScore() == all[j].getScore()) {
                    if (all[i].getWinNumber() < all[j].getWinNumber()) swap(all, i, j);
                    else if (all[i].getWinNumber() == all[j].getWinNumber()) {
                        if (all[i].getDrawsNumber() < all[j].getDrawsNumber()) swap(all, i, j);
                        else if (all[i].getDrawsNumber() == all[j].getDrawsNumber()) {
                            if (all[i].getLossesNumber() > all[j].getLossesNumber()) swap(all, i, j);
                            else if (all[i].getLossesNumber() == all[j].getLossesNumber()) {
                                if (all[i].getUsername().compareTo(all[j].getUsername()) > 0) swap(all, i, j);
                            }
                        }
                    }
                }
            }
        }
        return all;
    }

    private void swap(Player[] all, int i, int j) {
        Player temp = all[i];
        all[i] = all[j];
        all[j] = temp;
    }

    private void shop(String username) {
        new Shop().run(username, scanner);
    }

    private void logout() {
        System.out.println("logout successful");
    }

    private void helpMenu() {
        System.out.println("new_game [username]\n" +
                "scoreboard\n" +
                "list_users\n" +
                "shop\n" +
                "help\n" +
                "logout");
    }

    private boolean gotoNewGameMenu(String scannedString) {
        return booleanMatcherFinder(scannedString, "^new_game (\\S+)$");
    }

    private boolean gotoListUsersMenu(String scannedString) {
        return booleanMatcherFinder(scannedString, "^list_users$");
    }

    private boolean gotoScoreBoardMenu(String scannedString) {
        return booleanMatcherFinder(scannedString, "^scoreboard$");
    }

    private boolean gotoShopMenu(String scannedString) {
        return booleanMatcherFinder(scannedString, "^shop$");
    }

    private boolean gotoHelpMenu(String scannedString) {
        return booleanMatcherFinder(scannedString, "^help$");
    }

    private boolean gotoLogoutMenu(String scannedString) {
        return booleanMatcherFinder(scannedString, "^logout$");
    }

    private boolean booleanMatcherFinder(String inputString, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.find();
    }

    private Matcher matcherFinder(String inputString, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(inputString);
        return matcher;
    }
}
