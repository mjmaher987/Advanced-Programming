import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RealGameMenu {
    String[] username;
    MapHome[][][] map;
    Player[] players;
    int[] inGameScores;

    public void run(String firstUsername, String secondUsername, Scanner scan) {
        setEveryThing(firstUsername, secondUsername);
        goToTheGame(scan);
    }

    private void goToTheGame(Scanner scanner) {
        boolean endGameBoolean = false, forfeit = false;
        int forfeitor = -1;
        Nobat.nobat = 0;
        while (!endGameBoolean) {
            Nobat.nobat = Nobat.nobat % 2;
            String scanned = scanner.nextLine();
            if (gameEnded(0) || gameEnded(1)) endGameBoolean = true;
            else if (booleanMatcherFinder(scanned, "^help$")) helpMenu();
            else if (booleanMatcherFinder(scanned, "^forfeit$")) {
                forfeitor = Nobat.nobat;
                forfeitMenu(Nobat.nobat);
                endGameBoolean = true;
                forfeit = true;
            } else if (booleanMatcherFinder(scanned, "^show-turn$")) showTurn(Nobat.nobat);
            else if (booleanMatcherFinder(scanned, "^show-my-board$")) showMyBoard(Nobat.nobat);
            else if (booleanMatcherFinder(scanned, "^show-rival-board$")) showRivalBoard(Nobat.nobat);
            else if (booleanMatcherFinder(scanned, "^bomb (\\d+),(\\d+)$")) bomb(scanned, Nobat.nobat);
            else if (booleanMatcherFinder(scanned, "^put-airplane (\\d+),(\\d+) -(\\S)$"))
                airplane(scanned, Nobat.nobat);
            else if (booleanMatcherFinder(scanned, "^scanner (\\d+),(\\d+)$")) scannerMenu(scanned, Nobat.nobat);
            else {
                System.out.println("invalid command");
            }
            if (gameEnded(0) || gameEnded(1)) endGameBoolean = true;
        }
        calculateScores();
        checkFinalEveryThing(forfeit, forfeitor);
    }

    private void checkFinalEveryThing(boolean forfeit, int forfeitor) {
        if (forfeit) {
            players[forfeitor].reduceAmount(50);
            players[forfeitor].addIrregularLosses();
            players[(forfeitor + 1) % 2].increaseAmount(inGameScores[(forfeitor + 1) % 2]);
            players[(forfeitor + 1) % 2].addIrregularWin();
        } else if (gameEnded(0) && gameEnded(1)) {
            System.out.println("draw");
            players[0].increaseAmount(25 + inGameScores[0]);
            players[0].addDrawsNumber();
            players[1].increaseAmount(25 + inGameScores[1]);
            players[1].addDrawsNumber();
        } else if (gameEnded(0) && !gameEnded(1)) {
            players[0].increaseAmount(inGameScores[0]);
            players[0].addRegularLosses();
            players[1].increaseAmount(50 + inGameScores[1]);
            players[1].addRegularWin();
            System.out.println(username[1] + " is winner");
        } else if (gameEnded(1) && !gameEnded(0)) {
            players[1].increaseAmount(inGameScores[1]);
            players[1].addRegularLosses();
            players[0].increaseAmount(50 + inGameScores[0]);
            players[0].addRegularWin();
            System.out.println(username[0] + " is winner");
        }
    }

    private void setEveryThing(String firstUsername, String secondUsername) {
        this.username = new String[2];
        this.username[0] = firstUsername;
        this.username[1] = secondUsername;
        this.players = new Player[2];
        this.players[0] = Player.getPlayerWithUsername(firstUsername);
        this.players[1] = Player.getPlayerWithUsername(secondUsername);
        this.map = new MapHome[2][11][11];
        this.map[0] = players[0].getCurrentMap();
        this.map[1] = players[1].getCurrentMap();
        this.inGameScores = new int[2];
        this.inGameScores[0] = 0;
        this.inGameScores[1] = 0;
    }

    private void calculateScores() {
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                MapHome map1 = map[0][j][i];
                MapHome map2 = map[1][j][i];
                if (map1.doesItHaveAnyShip() && map1.destroyedBefore()) inGameScores[1]++;
                if (map1.doesItHaveAnyMine() && map1.destroyedBefore()) inGameScores[1]--;
                if (map2.doesItHaveAnyShip() && map2.destroyedBefore()) inGameScores[0]++;
                if (map2.doesItHaveAnyMine() && map2.destroyedBefore()) inGameScores[0]--;
            }
        }
    }

    private boolean gameEnded(int player) {
        boolean end = true;
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                MapHome map1 = map[player][j][i];
                if (map1.doesItHaveAnyShip() && !map1.destroyedBefore()) end = false;
            }
        }
        return end;
    }

    private void scannerMenu(String scanned, int nobat) {
        Matcher matcher = matcherFinder(scanned, "^scanner (\\d+),(\\d+)$");
        int x = 0, y = 0;
        if (matcher.find()) {
            x = Integer.parseInt(matcher.group(1));
            y = Integer.parseInt(matcher.group(2));
        }
        if (x > 10 || y > 10 || x < 1 || y < 1) System.out.println("wrong coordination");
        else if (x + 2 > 10 || y + 2 > 10) System.out.println("off the board");
        else if (players[nobat].getScanner() == 0) System.out.println("you don't have scanner");
        else {
            continueScanner(nobat, x, y);
        }
    }

    private void continueScanner(int nobat, int x, int y) {
        players[nobat].reduceScanner();
        MapHome[][] rivalMap = map[(nobat + 1) % 2];
        int count = 0;
        while (count < 3) {
            System.out.print("|");
            int count2 = 0;
            while (count2 < 3) {
                if (rivalMap[x + count2][count + y].doesItHaveAnyShip() && rivalMap[x + count2][count + y].isItVisible()
                        && !rivalMap[x + count2][count + y].destroyedBefore()) {
                    System.out.print("SX|");
                } else {
                    System.out.print("  |");
                }
                count2++;
            }
            System.out.println();
            count++;
        }
    }

    private void airplane(String scanned, int nobat) {
        Matcher matcher = matcherFinder(scanned, "^put-airplane (\\d+),(\\d+) -(\\S)$");
        int x = 0, y = 0;
        String direction = "";
        if (matcher.find()) {
            x = Integer.parseInt(matcher.group(1));
            y = Integer.parseInt(matcher.group(2));
            direction = matcher.group(3);
        }
        if (x > 10 || y > 10 || x < 1 || y < 1) System.out.println("wrong coordination");
        else if (!direction.equals("v") && !direction.equals("h")) System.out.println("invalid direction");
        else if (direction.equals("h") && (x + 4 > 10 || y + 1 > 10)) System.out.println("off the board");
        else if (direction.equals("v") && (y + 4 > 10 || x + 1 > 10)) System.out.println("off the board");
        else if (players[nobat].getAirplane() == 0) System.out.println("you don't have airplane");
        else if (doesItHaveAntiAirCraft(x, y, (nobat + 1) % 2, direction)) {
            players[nobat].reduceAirPlane();
            System.out.println("the rival's antiaircraft destroyed your airplane");
            removeFirstAntiAirCraft(x, y, (nobat + 1) % 2, direction);
        } else {
            continueAirPlane(nobat, x, y, direction);
        }
    }

    private void continueAirPlane(int nobat, int x, int y, String direction) {
        players[nobat].reduceAirPlane();
        if (doesItHaveAntShips(x, y, (nobat + 1) % 2, direction)) {
            System.out.println(destroyShipsAndMines(x, y, (nobat + 1) % 2, direction) + " pieces of rival's ships was damaged");
        } else {
            System.out.println("target not found");
        }
        if (direction.equals("h")) putAirPlaneBombInSea(nobat, x, y, 2, 5);
        else {
            putAirPlaneBombInSea(nobat, x, y, 5, 2);
        }
    }


    private void putAirPlaneBombInSea(int nobat, int x, int y, int iMax, int jMax) {
        for (int i = 0; i < iMax; i++) {
            for (int j = 0; j < jMax; j++) {
                if (!map[(nobat + 1) % 2][j + x][i + y].destroyedBefore())
                    map[(nobat + 1) % 2][j + x][i + y].setBombInSea();
            }
        }
    }

    private int destroyShipsAndMines(int x, int y, int i, String direction) {
        if (direction.equals("h")) return continueDestroying(2, 5, i, x, y);
        else {
            return continueDestroying(5, 2, i, x, y);
        }
    }

    private int continueDestroying(int iMax, int jMax, int i, int x, int y) {
        int number = 0;
        MapHome[][] map1 = map[i];
        for (int count = 0; count < iMax; count++) {
            for (int count2 = 0; count2 < jMax; count2++) {
                if (map1[count2 + x][count + y].doesItHaveAnyShip() && !map1[count2 + x][count + y].destroyedBefore()) {
                    number++;
                    map1[count2 + x][count + y].setDestroyedShip();
                    int code = map1[count2 + x][count + y].getShipCodeNumber();
                    if (AreCompleteDestroyedShips(i, x + count2, y + count))
                        completeDestroyShipsWithThisCodeForThatPlayer(code, i);
                } else if (map1[count2 + x][count + y].doesItHaveAnyMine() && !map1[count2 + x][count + y].destroyedBefore()) {
                    map1[count2 + x][count + y].setDestroyedMine();
                    if (!map[(i + 1) % 2][count2 + x][count + y].destroyedBefore() && map[(i + 1) % 2][count2 + x][count + y].doesItHaveAnyShip()) {
                        int code = map[(i + 1) % 2][count2 + x][count + y].getShipCodeNumber();
                        if (AreCompleteDestroyedShips((i + 1) % 2, x + count2, y + count))
                            completeDestroyShipsWithThisCodeForThatPlayer(code, (i + 1) % 2);
                        map[(i + 1) % 2][count2 + x][count + y].setDestroyedShip();
                    } else if (!map[(i + 1) % 2][count2 + x][count + y].destroyedBefore() && map[(i + 1) % 2][count2 + x][count + y].doesItHaveAnyMine()) {
                        map[(i + 1) % 2][count2 + x][count + y].setDestroyedMine();
                    } else if (!map[(i + 1) % 2][count2 + x][count + y].destroyedBefore())
                        map[(i + 1) % 2][count2 + x][count + y].setBombInSea();
                }
            }
        }
        return number;
    }

    private void completeDestroyShipsWithThisCodeForThatPlayer(int shipCode, int playerNumber) {
        MapHome[][] map1 = map[playerNumber];
        for (int i1 = 1; i1 < 11; i1++) {
            for (int j1 = 1; j1 < 11; j1++) {
                if (map1[j1][i1].getShipCodeNumber() == shipCode)
                    map1[j1][i1].setCompleteShipDestroyed();
            }
        }
    }

    private boolean doesItHaveAntShips(int x, int y, int i, String direction) {
        if (direction.equals("h")) {
            return checkExistingShipsWithDirection(2, 5, i, x, y);
        } else {
            return checkExistingShipsWithDirection(5, 2, i, x, y);
        }
    }

    private boolean checkExistingShipsWithDirection(int iMax, int jMax, int player, int x, int y) {
        MapHome[][] map1 = map[player];
        for (int count = 0; count < iMax; count++) {
            for (int count2 = 0; count2 < jMax; count2++) {
                if (map1[count2 + x][count + y].doesItHaveAnyShip() && !map1[count2 + x][count + y].destroyedBefore()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void removeFirstAntiAirCraft(int x, int y, int i, String direction) {
        int minimum = 10000;
        if (direction.equals("h")) minimum = getMinimumAntiAirCraft(2, 5, i, x, y);
        else {
            minimum = getMinimumAntiAirCraft(5, 2, i, x, y);
        }
        removeThisAntiAirCraftForThisPlayer(minimum, i);
    }

    private void removeThisAntiAirCraftForThisPlayer(int minimum, int player) {
        MapHome[][] map1 = map[player];
        for (int count = 1; count < 11; count++) {
            for (int count2 = 1; count2 < 11; count2++) {
                if (map1[count2][count].doesAnyAirCraftsExists()) {
                    map1[count2][count].removeThisAntiAirCraft(minimum);
                }
            }
        }
    }

    private int getMinimumAntiAirCraft(int iMax, int jMax, int player, int x, int y) {
        int minimum = 100000;
        MapHome[][] map1 = map[player];
        for (int count = 0; count < iMax; count++) {
            for (int count2 = 0; count2 < jMax; count2++) {
                if (map1[count2 + x][count + y].doesAnyAirCraftsExists()) {
                    if (map1[count2 + x][count + y].getAntiAirCraftNumber() < minimum) {
                        minimum = map1[count2 + x][count + y].getAntiAirCraftNumber();
                    }
                }
            }
        }
        return minimum;
    }


    private boolean doesItHaveAntiAirCraft(int x, int y, int i, String direction) {
        MapHome[][] map1 = map[i];
        if (direction.equals("h")) {
            for (int count = 0; count < 2; count++) {
                for (int count2 = 0; count2 < 5; count2++) {
                    if (map1[count2 + x][count + y].doesAnyAirCraftsExists()) return true;
                }
            }
        } else if (direction.equals("v")) {
            for (int count = 0; count < 5; count++) {
                for (int count2 = 0; count2 < 2; count2++) {
                    if (map1[count2 + x][count + y].doesAnyAirCraftsExists()) return true;
                }
            }
        }
        return false;
    }

    private void bomb(String scanned, int nobat) {
        Matcher matcher = matcherFinder(scanned, "^bomb (\\d+),(\\d+)$");
        int rival = (nobat + 1) % 2, x = 0, y = 0;
        MapHome[][] map11 = map[rival];
        if (matcher.find()) {
            x = Integer.parseInt(matcher.group(1));
            y = Integer.parseInt(matcher.group(2));
        }
        if (x > 10 || y > 10 || x < 1 || y < 1) System.out.println("wrong coordination");
        else if (map11[x][y].destroyedBefore()) System.out.println("this place has already destroyed");
        else if (map11[x][y].doesItHaveAnyShip()) {
            if (!AreCompleteDestroyedShips(rival, x, y)) System.out.println("the rival's ship was damaged");
            else {
                System.out.println("the rival's ship" + map11[x][y].getShipTypeNumber() + " was destroyed");
                int code = map11[x][y].getShipCodeNumber();
                completeDestroyShipsWithThisCodeForThatPlayer(code, rival);
            }
            map11[x][y].setDestroyedShip();
        } else if (map11[x][y].doesItHaveAnyMine()) continueBombBecauseOfMine(map11, x, y, nobat);
        else {
            continueBombBecauseOfBombInSea(map11, x, y);
        }
    }

    private void continueBombBecauseOfBombInSea(MapHome[][] map11, int x, int y) {
        System.out.println("the bomb fell into sea");
        map11[x][y].setBombInSea();
        System.out.println("turn completed");
        Nobat.nobat = (Nobat.nobat + 1) % 2;
    }

    private void continueBombBecauseOfMine(MapHome[][] map11, int x, int y, int nobat) {
        System.out.println("you destroyed the rival's mine");
        map11[x][y].setDestroyedMine();

        if (!map[nobat][x][y].destroyedBefore() && map[nobat][x][y].doesItHaveAnyShip()) {
            int code = map[nobat][x][y].getShipCodeNumber();
            if (AreCompleteDestroyedShips(nobat, x, y)) {
                completeDestroyShipsWithThisCodeForThatPlayer(code, nobat);
            }
            map[nobat][x][y].setDestroyedShip();
        } else if (!map[nobat][x][y].destroyedBefore() && map[nobat][x][y].doesItHaveAnyMine()) {
            map[nobat][x][y].setDestroyedMine();
        } else if (!map[nobat][x][y].destroyedBefore()) {
            map[nobat][x][y].setBombInSea();
        }
        System.out.println("turn completed");
        Nobat.nobat = (Nobat.nobat + 1) % 2;
    }

    private boolean AreCompleteDestroyedShips(int player, int x, int y) {
        MapHome[][] map11 = map[player];
        int code = map11[x][y].getShipCodeNumber();
        boolean completeDestroy = true;
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                if (map11[j][i].getShipCodeNumber() == code && !map11[j][i].destroyedBefore() && (j != x || i != y))
                    completeDestroy = false;
            }
        }
        return completeDestroy;
    }

    private void showRivalBoard(int nobat) {
        for (int i = 1; i < 11; i++) {
            System.out.print("|");
            for (int j = 1; j < 11; j++) {
                MapHome map1 = map[(nobat + 1) % 2][j][i];
                if (map1.doesItHaveAnyShip() && map1.destroyedBefore()) {
                    if (map1.isCompleteShipDestroyed()) System.out.print("D" + map1.getShipTypeNumber());
                    else {
                        System.out.print("DX");
                    }
                } else if (map1.doesItHaveAnyMine() && map1.destroyedBefore()) System.out.print("MX");
                else if (map1.isBombInSea()) System.out.print("XX");
                else {
                    System.out.print("  ");
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }

    private void showMyBoard(int nobat) {
        nobat = nobat % 2;
        MapHome[][] boardFirstPlayer = map[nobat];
        for (int i = 1; i < 11; i++) {
            System.out.print("|");
            for (int j = 1; j < 11; j++) {
                MapHome map11 = boardFirstPlayer[j][i];
                if (map11.doesItHaveAnyMine()) {
                    if (map11.destroyedBefore()) System.out.print("MX");
                    else {
                        System.out.print("Mm");
                    }
                } else if (map11.doesItHaveAnyShip()) {
                    if (map11.destroyedBefore()) System.out.print("D" + map11.getShipTypeNumber());
                    else if (map11.isItVisible()) System.out.print("S" + map11.getShipTypeNumber());
                    else {
                        System.out.print("I" + map11.getShipTypeNumber());
                    }
                } else if (map11.isBombInSea()) System.out.print("XX");
                else if (map11.doesAnyAirCraftsExists()) System.out.print("AA");
                else {
                    System.out.print("  ");
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }

    public static class Nobat {
        public static int nobat;
    }

    private void showTurn(int nobat) {
        String turnUsername = username[nobat];
        System.out.println(turnUsername + "'s turn");
    }

    private void forfeitMenu(int nobat) {
        String loser = username[nobat];
        String winner = username[(nobat + 1) % 2];
        System.out.println(loser + " is forfeited");
        System.out.println(winner + " is winner");
    }

    private void helpMenu() {
        System.out.println("bomb [x],[y]\n" +
                "put-airplane [x],[y] [-h|-v]\n" +
                "scanner [x],[y]\n" +
                "show-turn\n" +
                "show-my-board\n" +
                "show-rival-board\n" +
                "help\n" +
                "forfeit");
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
