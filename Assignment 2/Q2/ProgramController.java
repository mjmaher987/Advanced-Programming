import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgramController {
    Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean exitBoolean = false;
        while (!exitBoolean) {
            String scannedString = scanner.nextLine();
            if (gotoRegisterMenu(scannedString)) {
                checkRegisterMenuProblems(scannedString);
            } else if (gotoLoginMenu(scannedString)) {
                checkLoginMenuProblems(scannedString);
            } else if (gotoRemoveMenu(scannedString)) {
                checkRemoveMenuProblems(scannedString);
            } else if (gotoListUsersMenu(scannedString)) {
                list_users();
            } else if (gotoHelpMenu(scannedString)) {
                helpMenu();
            } else if (gotoExitMenu(scannedString)) {
                exitBoolean = true;
                System.out.println("program ended");
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

    private void helpMenu() {
        System.out.println("register [username] [password]\n" +
                "login [username] [password]\n" +
                "remove [username] [password]\n" +
                "list_users\n" +
                "help\n" +
                "exit");
    }

    private void checkRemoveMenuProblems(String scannedString) {
        Matcher matcher = matcherFinder(scannedString, "^remove (\\S+) (\\S+)$");
        String userName = "";
        String password = "";
        if (matcher.find()) {
            userName = matcher.group(1);
            password = matcher.group(2);
        }
        if (!booleanMatcherFinder(userName, "^([a-zA-Z0-9_]+)$")) {
            System.out.println("username format is invalid");
        } else if (!booleanMatcherFinder(password, "^([a-zA-Z0-9_]+)$")) {
            System.out.println("password format is invalid");
        } else if (!Player.doesItExists(userName)) {
            System.out.println("no user exists with this username");
        } else if (!Player.isCorrectPassword(userName, password)) {
            System.out.println("incorrect password");
        } else {
            Player.removeUser(userName);
            System.out.println("removed " + userName + " successfully");
        }
    }

    private void checkLoginMenuProblems(String scannedString) {
        Matcher matcher = matcherFinder(scannedString, "^login (\\S+) (\\S+)$");
        String userName = "";
        String password = "";
        if (matcher.find()) {
            userName = matcher.group(1);
            password = matcher.group(2);
        }
        if (!booleanMatcherFinder(userName, "^([a-zA-Z0-9_]+)$")) {
            System.out.println("username format is invalid");
        } else if (!booleanMatcherFinder(password, "^([a-zA-Z0-9_]+)$")) {
            System.out.println("password format is invalid");
        } else if (!Player.doesItExists(userName)) {
            System.out.println("no user exists with this username");
        } else if (!Player.isCorrectPassword(userName, password)) {
            System.out.println("incorrect password");
        } else {
            System.out.println("login successful");
            new MainMenu(scanner).run(userName);
        }
    }

    private void checkRegisterMenuProblems(String scannedString) {
        Matcher matcher = matcherFinder(scannedString, "^register (\\S+) (\\S+)$");
        String userName = "";
        String password = "";
        if (matcher.find()) {
            userName = matcher.group(1);
            password = matcher.group(2);
        }
        if (!booleanMatcherFinder(userName, "^[a-zA-Z0-9_]+$")) {
            System.out.println("username format is invalid");
        } else if (!booleanMatcherFinder(password, "^[a-zA-Z0-9_]+$")) {
            System.out.println("password format is invalid");
        } else if (Player.doesItExists(userName)) {
            System.out.println("a user exists with this username");
        } else {
            new Player(userName, password);
            System.out.println("register successful");
        }
    }

    private boolean gotoRegisterMenu(String scannedString) {
        return booleanMatcherFinder(scannedString, "^register (\\S+) (\\S+)$");
    }

    private boolean gotoLoginMenu(String scannedString) {
        return booleanMatcherFinder(scannedString, "^login (\\S+) (\\S+)$");
    }

    private boolean gotoRemoveMenu(String scannedString) {
        return booleanMatcherFinder(scannedString, "^remove (\\S+) (\\S+)$");
    }

    private boolean gotoListUsersMenu(String scannedString) {
        return booleanMatcherFinder(scannedString, "^list_users$");
    }

    private boolean gotoHelpMenu(String scannedString) {
        return booleanMatcherFinder(scannedString, "^help$");
    }

    private boolean gotoExitMenu(String scannedString) {
        return "exit".equals(scannedString);
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
