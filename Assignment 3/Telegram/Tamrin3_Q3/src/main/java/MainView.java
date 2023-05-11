import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainView {
    private static Scanner scanner;
    private static User onlineUser;
    private static int portNumberForListen;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;



    static {
        onlineUser = null;
        portNumberForListen = -1;
    }

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        runProgram();
    }

    private static void runProgram() {
        while(true) {
            String input = scanner.nextLine();
            input = input.trim();
            System.out.println(findCommands(input));
        }
    }

    private static String findCommands(String input) {
        if (MainMenuPatterns.isItCreateUserPattern(input)) {
            return createUser(input);
        }
        if (MainMenuPatterns.isItLoginUserPattern(input)) {
            return loginUser(input);
        }
        if (MainMenuPatterns.isItLogoutUserPattern(input)) {
            return logoutUser(input);
        }
        if (onlineUser == null) {
            return "you must login to access this feature";
        }
        if (MainMenuPatterns.isItSetPortPattern(input)) {
            return setPort(input);
        }
        if (MainMenuPatterns.isItChangePortPattern(input)) {
            return changePort(input);
        }
        if (MainMenuPatterns.isItClosePortPattern(input)) {
            return closePort(input);
        }
        if (MainMenuPatterns.isItUnfocusedSendMessage(input)) {
            return UnfocusedSendMessage(input);
        }
        if (MainMenuPatterns.isItFocusedSendMessage(input)) {
            return focusedSendMessage(input);
        }
        if (MainMenuPatterns.isItShowContactsPattern(input)) {
            return showContacts();
        }
        if (MainMenuPatterns.isItShowSpecifiedContactUsingUsername(input)) {
            return showSpecifiedContact(input);
        }
        if (MainMenuPatterns.isItShowSendersPattern(input)) {
            return showSenders();
        }
        if (MainMenuPatterns.isItShowMessagesPattern(input)) {
            return showMessages();
        }
        if (MainMenuPatterns.isItShowCountMessagesPattern(input)) {
            return showCountMessages();
        }
        if (MainMenuPatterns.isItShowCountSendersPattern(input)) {
            return showCountSenders();
        }
        if (MainMenuPatterns.isItShowMessagesFromSpecifiedUsername(input)) {
            return showMessagesFromSpecifiedUsername(input);
        }
        if (MainMenuPatterns.isItShowCountMessagesFromSpecifiedUsername(input)) {
            return showCountMessagesFromSpecifiedUsername(input);
        }

    }

    private static String showCountMessagesFromSpecifiedUsername(String input) {
    }

    private static String showMessagesFromSpecifiedUsername(String input) {
    }

    private static String showCountSenders() {

    }

    private static String showCountMessages() {

    }

    private static String showMessages() {
    }

    private static String showSenders() {
    }

    private static String showSpecifiedContact(String input) {
    }

    private static String showContacts() {
    }

    private static String focusedSendMessage(String input) {
        return null;
    }

    private static String UnfocusedSendMessage(String input) {
    }
    
    private static String changePort(String input) {
        return null;
    }

    private static String closePort(String input) {
        int enteredPortNumber = MainMenuPatterns.getPortNumberForClosePort(input);
        if (portNumberForListen != enteredPortNumber || socket.isClosed()) {
            return "the port you specified was not open";
        }
        try {
            socket.close();
            serverSocket.close();
            dataInputStream.close();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        portNumberForListen = -1;
//        portNumberForListen = enteredPortNumber;
//        try {
//            serverSocket = new ServerSocket(portNumberForListen);
//            while (true) {
//                socket = serverSocket.accept();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "success";

    }

    private static String setPort(String input) {
        if (portNumberForListen != -1) {
            return "the port is already set";
        }
        return setPortAndStart(input);
    }

    private static String setPortAndStart(String input) {
        //TODO --> invalid number?
        portNumberForListen = MainMenuPatterns.getPortNumberForSetPort(input);
        try {
            serverSocket = new ServerSocket(portNumberForListen);
            while (true) {
                socket = serverSocket.accept();
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    private static String logoutUser(String input) {
        if (onlineUser == null) {
            return "you must login before logging out";
        }
        onlineUser = null;
        return "success";
    }

    private static String loginUser(String input) {
        String username = MainMenuPatterns.getUsernameForLoginUser(input);
        String password = MainMenuPatterns.getPasswordForLoginUser(input);
        if (User.getUserByUsername(username) == null) {
            return "user not found";
        }
        if (!User.doesUsernameAndPasswordMatch(username, password)) {
            return "incorrect password";
        }
        return "success";
    }

    private static String createUser(String input) {
        String username = MainMenuPatterns.getUsernameForCreateUser(input);
        String password = MainMenuPatterns.getPasswordForCreateUser(input);
        if (User.getUserByUsername(username) == null) {
            return "this username is unavailable";
        }
        if (!User.isThisUsernameValid(username)) {
            return "this username is unavailable";
        }
        new User(username, password);
        return "success";
    }
}
