import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
    private static HashMap<String, String> allPlayers;
    private static ArrayList<Player> players;
    private static int numberGenerator;

    static {
        players = new ArrayList<>();
    }

    static {
        allPlayers = new HashMap<>();
    }

    static {
        numberGenerator = 0;
    }

    private String username;
    private String password;
    private MapHome[][] currentMap;
    private int score;
    private int regularWinsNumber;
    private int irregularWinsNumber;
    private int regularLossesNumber;
    private int irregularLossesNumber;
    private int drawsNumber;
    private int amount;
    private int mine;
    private int antiaircraft;
    private int airplane;
    private int scanner;
    private int invisible;

    public Player(String username, String password) {
        this.currentMap = new MapHome[11][11];
        this.username = username;
        this.password = password;
        this.regularWinsNumber = 0;
        this.irregularWinsNumber = 0;
        this.drawsNumber = 0;
        this.regularLossesNumber = 0;
        this.irregularLossesNumber = 0;
        this.amount = 50;
        this.score = 0;
        this.mine = 0;
        this.airplane = 0;
        this.antiaircraft = 0;
        this.scanner = 0;
        this.invisible = 0;
        allPlayers.put(username, password);
        players.add(this);
    }

    public static boolean doesItExists(String username) {
        for (String user : allPlayers.keySet()) {
            if (user.equals(username)) return true;
        }
        return false;
    }

    public static boolean isCorrectPassword(String username, String password) {
        for (String user : allPlayers.keySet()) {
            if (user.equals(username)) {
                return allPlayers.get(user).equals(password);
            }
        }
        return false;
    }

    public static void removeUser(String username) {
        allPlayers.remove(username);
        players.remove(Player.getPlayerWithUsername(username));
    }

    public static HashMap<String, String> getAllPlayersString() {
        return allPlayers;
    }

    public static ArrayList<Player> getALlPlayersPlayer() {
        return players;
    }

    public static Player getPlayerWithUsername(String username) {
        for (Player user : players) {
            if (user.username.equals(username)) return user;
        }
        return new Player("", "");
    }

    public int getScore() {
        return this.score;
    }

    public int getWinNumber() {
        return this.regularWinsNumber + this.irregularWinsNumber;
    }

    public int getLossesNumber() {
        return this.regularLossesNumber + this.irregularLossesNumber;
    }

    public int getDrawsNumber() {
        return this.drawsNumber;
    }

    public String getUsername() {
        return this.username;
    }

    public int getAirplane() {
        return this.airplane;
    }

    public int getScanner() {
        return this.scanner;
    }

    public int getMine() {
        return this.mine;
    }

    public int getAntiaircraft() {
        return this.antiaircraft;
    }

    public int getInvisible() {
        return this.invisible;
    }

    public int getShipNumberGenerator() {
        numberGenerator++;
        return numberGenerator;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setMap(MapHome[][] currentMap) {
        this.currentMap = currentMap;
    }

    public MapHome[][] getCurrentMap() {
        return this.currentMap;
    }

    public void reduceAntiAircraft() {
        this.antiaircraft = getAntiaircraft() - 1;
    }

    public void addRegularWin() {
        this.regularWinsNumber += 1;
        this.score += 3;

    }

    public void addIrregularWin() {
        this.irregularWinsNumber += 1;
        this.score += 2;
    }

    public void addDrawsNumber() {
        this.drawsNumber += 1;
        this.score += 1;
    }

    public void addRegularLosses() {
        this.regularLossesNumber += 1;
    }

    public void addIrregularLosses() {
        this.irregularLossesNumber += 1;
        this.score -= 1;
    }

    public void reduceAmount(int reducing) {
        this.amount = getAmount() - reducing;
    }

    public void increaseAmount(int increasing) {
        this.amount = getAmount() + increasing;
    }

    public void increaseAntiAirCraft(int aaAmount) {
        this.antiaircraft = getAntiaircraft() + aaAmount;
    }

    public void reduceMine() {
        this.mine = getMine() - 1;
    }

    public void increaseMine(int mineAmount) {
        this.mine = getMine() + mineAmount;
    }

    public void reduceAirPlane() {
        this.airplane = getAirplane() - 1;
    }

    public void increaseAirPlane(int number) {
        this.airplane = getAirplane() + number;
    }

    public void reduceScanner() {
        this.scanner = getScanner() - 1;
    }

    public void increaseScanner(int number) {
        this.scanner = getScanner() + number;
    }

    public void reduceInvisible() {
        this.invisible = getInvisible() - 1;
    }

    public void increaseInvisible(int number) {
        this.invisible = getInvisible() + number;
    }


}
