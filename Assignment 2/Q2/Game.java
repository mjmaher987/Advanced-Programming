import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {

    private String[] players;
    private MapHome[][][] board;
    private int[][] shipsOfPlayers;
    Scanner scanner;

    public Game(String playerOne, String playerTwo, Scanner scanner11) {
        this.scanner = scanner11;
        this.players = new String[2];
        this.players[0] = playerOne;
        this.board = new MapHome[2][11][11];
        this.shipsOfPlayers = new int[2][4];
        this.players[1] = playerTwo;
    }

    public void run() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                board[0][j][i] = new MapHome(players[0]);
                board[1][j][i] = new MapHome(players[1]);
            }
        }
        Player.getPlayerWithUsername(players[0]).setMap(board[0]);
        Player.getPlayerWithUsername(players[1]).setMap(board[1]);
        arrangeTheMap();
        new RealGameMenu().run(players[0], players[1], scanner);
    }

    private void arrangeTheMap() {
        for (int i = 0; i < 2; i++) {
            boolean finishArranging = false;
            while (!finishArranging) {
                String scanned = scanner.nextLine();
                if (booleanMatcherFinder(scanned, "^put S(\\d+) (\\d+),(\\d+) -(\\S)$")) checkPutProblems(scanned, i);
                else if (booleanMatcherFinder(scanned, "^put-mine (\\d+),(\\d+)$")) checkMineProblem(scanned, i);
                else if (booleanMatcherFinder(scanned, "^put-antiaircraft (\\d+) -(\\S)$")) checkAntiAircraft(scanned, i);
                else if (booleanMatcherFinder(scanned, "^invisible (\\d+),(\\d+)$")) invisible(scanned, i);
                else if (booleanMatcherFinder(scanned, "^finish-arranging$")) {
                    if (shipsOfPlayers[i][0] == 4 && shipsOfPlayers[i][1] == 3 && shipsOfPlayers[i][2] == 2 && shipsOfPlayers[i][3] == 1) {
                        finishArranging = true;
                        System.out.println("turn completed");
                    } else {
                        System.out.println("you must put all ships on the board");
                    }
                } else if (booleanMatcherFinder(scanned, "^help$")) helpMenu();
                else if (booleanMatcherFinder(scanned, "^show-my-board$")) showMyBoard(i);
                else {
                    System.out.println("invalid command");
                }
            }
        }
    }

    private void invisible(String scanned, int i) {
        Matcher matcher = matcherFinder(scanned, "^invisible (\\d+),(\\d+)$");
        int x = 0, y = 0;
        if (matcher.find()) {
            x = Integer.parseInt(matcher.group(1));
            y = Integer.parseInt(matcher.group(2));
        }
        String username = "";
        username = players[i];
        Player player = Player.getPlayerWithUsername(username);
        MapHome[][] map = player.getCurrentMap();
        if (x > 10 || y > 10 || x < 1 || y < 1) System.out.println("wrong coordination");
        else if (player.getInvisible() == 0) System.out.println("you don't have enough invisible");
        else if (!map[x][y].doesItHaveAnyShip()) System.out.println("there is no ship on this place on the board");
        else if (!map[x][y].isItVisible()) System.out.println("this place has already made invisible");
        else {
            player.reduceInvisible();
            map[x][y].makeInvisible();
        }
    }

    private void checkAntiAircraft(String scanned, int i) {
        Matcher matcher = matcherFinder(scanned, "^put-antiaircraft (\\d+) -(\\S)$");
        String username = players[i];
        Player player = Player.getPlayerWithUsername(username);
        MapHome[][] map = player.getCurrentMap();
        int position = 0;
        String type = "";
        if (matcher.find()) {
            position = Integer.parseInt(matcher.group(1));
            type = matcher.group(2);
        }
        if (position > 10 || position < 1) System.out.println("wrong coordination");
        else if (position + 2 > 10) System.out.println("off the board");
        else if (!type.equals("v") && !type.equals("h")) System.out.println("invalid direction");
        else if (player.getAntiaircraft() == 0) System.out.println("you don't have enough antiaircraft");
        else {
            player.reduceAntiAircraft();
            AntiAirCraft antiAirCraft = new AntiAirCraft();
            int code = antiAirCraft.getCode();
            if (type.equals("v")) {
                for (int j = 1; j < 11; j++) {
                    map[position][j].addAirCraft(code);
                    map[position + 1][j].addAirCraft(code);
                    map[position + 2][j].addAirCraft(code);
                }
            } else {
                for (int j = 1; j < 11; j++) {
                    map[j][position].addAirCraft(code);
                    map[j][position + 1].addAirCraft(code);
                    map[j][position + 2].addAirCraft(code);
                }
            }
        }
    }

    private void checkMineProblem(String scanned, int i) {
        Matcher matcher = matcherFinder(scanned, "^put-mine (\\d+),(\\d+)$");
        int x = 0, y = 0;
        if (matcher.find()) {
            x = Integer.parseInt(matcher.group(1));
            y = Integer.parseInt(matcher.group(2));
        }
        continueMine(scanned, i, x, y);
    }

    private void continueMine(String scanned, int i, int x, int y) {
        String username = "";
        username = players[i];
        Player player = Player.getPlayerWithUsername(username);
        MapHome[][] map = player.getCurrentMap();
        if (x > 10 || y > 10 || x < 1 || y < 1) System.out.println("wrong coordination");
        else if (player.getMine() == 0) {
            System.out.println("you don't have enough mine");
        } else if (!map[x][y].isItFree()) System.out.println("collision with the other ship or mine on the board");
        else {
            map[x][y].setMine();
            player.reduceMine();
        }
    }

    private void helpMenu() {
        System.out.println("put S[number] [x],[y] [-h|-v]\n" +
                "put-mine [x],[y]\n" +
                "put-antiaircraft [s] [-h|-v]\n" +
                "invisible [x],[y]\n" +
                "show-my-board\n" +
                "help\n" +
                "finish-arranging");
    }

    private void showMyBoard(int i1) {
        MapHome[][] board1;
        board1 = board[i1];
        for (int i = 1; i < 11; i++) {
            System.out.print("|");
            for (int j = 1; j < 11; j++) {
                MapHome map = board1[j][i];
                if (map.doesItHaveAnyMine()) {
                    if (map.destroyedBefore()) System.out.print("MX");
                    else {
                        System.out.print("Mm");
                    }
                } else if (map.doesItHaveAnyShip()) {
                    if (map.destroyedBefore()) System.out.print("D" + map.getShipTypeNumber());
                    else if (map.isItVisible()) System.out.print("S" + map.getShipTypeNumber());
                    else {
                        System.out.print("I" + map.getShipTypeNumber());
                    }
                } else if (map.isBombInSea()) System.out.print("XX");
                else if (map.doesAnyAirCraftsExists()) System.out.print("AA");
                else {
                    System.out.print("  ");
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }

    private void checkPutProblems(String scanned, int i) {
        Matcher matcher = matcherFinder(scanned, "^put S(\\d+) (\\d+),(\\d+) -(\\S)$");
        int shipNumber = 0, xNumber = 0, yNumber = 0, playerNumber = i + 1;
        String directionCharacter = "";
        if (matcher.find()) {
            shipNumber = Integer.parseInt(matcher.group(1));
            xNumber = Integer.parseInt(matcher.group(2));
            yNumber = Integer.parseInt(matcher.group(3));
            directionCharacter = matcher.group(4);
        }
        if (shipNumber > 4 || shipNumber < 1) {
            System.out.println("invalid ship number");
        } else if (xNumber > 10 || yNumber > 10 || xNumber < 1 || yNumber < 1) {
            System.out.println("wrong coordination");
        } else if (!directionCharacter.equals("v") && !directionCharacter.equals("h")) {
            System.out.println("invalid direction");
        } else if (directionCharacter.equals("v") && yNumber + shipNumber - 1 > 10) {
            System.out.println("off the board");
        } else if (directionCharacter.equals("h") && xNumber + shipNumber - 1 > 10) {
            System.out.println("off the board");
        } else if (playerNumber == 1) {
            continuePut(shipNumber, xNumber, yNumber, directionCharacter, players[0], 1);
        } else if (playerNumber == 2) {
            continuePut(shipNumber, xNumber, yNumber, directionCharacter, players[1], 2);
        }
    }


    private void continuePut(int shipNumber, int xNumber, int yNumber, String directionCharacter, String username, int number) {
        Player player = Player.getPlayerWithUsername(username);
        if (shipsOfPlayers[number - 1][shipNumber - 1] == 5 - shipNumber)
            System.out.println("you don't have this type of ship");
        else {
            MapHome[][] map = player.getCurrentMap();
            boolean free = true;
            if (directionCharacter.equals("v")) free = checkFreeForPut(1, shipNumber, number - 1, xNumber, yNumber);
            else if (directionCharacter.equals("h"))
                free = checkFreeForPut(shipNumber, 1, number - 1, xNumber, yNumber);
            if (!free) System.out.println("collision with the other ship or mine on the board");
            else {
                int shipCode = player.getShipNumberGenerator();
                if (directionCharacter.equals("v")) {
                    for (int i = 0; i < shipNumber; i++) {
                        map[xNumber][yNumber + i].setShipCodeNumber(shipCode);
                        map[xNumber][yNumber + i].setShipTypeNumber(shipNumber);
                    }
                } else if (directionCharacter.equals("h")) {
                    for (int i = 0; i < shipNumber; i++) {
                        map[xNumber + i][yNumber].setShipCodeNumber(shipCode);
                        map[xNumber + i][yNumber].setShipTypeNumber(shipNumber);
                    }
                }
                shipsOfPlayers[number - 1][shipNumber - 1]++;
            }
        }
    }

    private boolean checkFreeForPut(int iMax, int jMax, int number, int xNumber, int yNumber) {
        boolean free = true;
        for (int i = 0; i < iMax; i++) {
            for (int j = 0; j < jMax; j++) {
                if (!board[number][xNumber + i][yNumber + j].isItFree()) free = false;
            }
        }
        return free;
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
