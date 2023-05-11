import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Shop {
    String username;

    public void run(String username, Scanner scanner) {
        this.username = username;
        Player player = Player.getPlayerWithUsername(username);
        boolean backBoolean = false;
        while (!backBoolean) {
            String scanned = scanner.nextLine().trim();
            if (booleanMatcherFinder(scanned, "^show-amount$")) System.out.println(player.getAmount());
            else if (booleanMatcherFinder(scanned, "^help$")) help();
            else if (booleanMatcherFinder(scanned, "^back$")) backBoolean = true;
            else if (booleanMatcherFinder(scanned, "^buy (\\S+) (\\d+)$")) buy(scanned);
            else {
                System.out.println("invalid command");
            }
        }
    }

    private void buy(String scanned) {
        Matcher matcher = matcherFinder(scanned, "^buy (\\S+) (\\d+)$");
        String kala = "";
        String number = "";
        if (matcher.find()) {
            kala = matcher.group(1);
            number = matcher.group(2);
        }
        if (!kala.equals("mine") && !kala.equals("antiaircraft") && !kala.equals("airplane") &&
                !kala.equals("scanner") && !kala.equals("invisible"))
            System.out.println("there is no product with this name");
        else if (Integer.parseInt(number) <= 0) System.out.println("invalid number");
        else {
            Player player = Player.getPlayerWithUsername(username);
            int currentAmount = player.getAmount();
            int numberOfKala = Integer.parseInt(number);
            if (booleanMatcherFinder(kala, "^mine$")) mine(player, currentAmount, numberOfKala);
            else if (booleanMatcherFinder(kala, "^antiaircraft$")) antiAirCraft(player, numberOfKala, currentAmount);
            else if (booleanMatcherFinder(kala, "^airplane$")) airPlane(player, currentAmount, numberOfKala);
            else if (booleanMatcherFinder(kala, "^scanner$")) scanner(player, currentAmount, numberOfKala);
            else if (booleanMatcherFinder(kala, "^invisible$")) invisible(player, currentAmount, numberOfKala);
        }
    }

    private void invisible(Player player, int currentAmount, int numberOfKala) {
        if (currentAmount < 20 * numberOfKala) {
            System.out.println("you don't have enough money");
        } else {
            player.reduceAmount(20 * numberOfKala);
            player.increaseInvisible(numberOfKala);
        }
    }

    private void scanner(Player player, int currentAmount, int numberOfKala) {
        if (currentAmount < 9 * numberOfKala) {
            System.out.println("you don't have enough money");
        } else {
            player.reduceAmount(9 * numberOfKala);
            player.increaseScanner(numberOfKala);
        }
    }

    private void airPlane(Player player, int currentAmount, int numberOfKala) {
        if (currentAmount < 10 * numberOfKala) {
            System.out.println("you don't have enough money");
        } else {
            player.reduceAmount(10 * numberOfKala);
            player.increaseAirPlane(numberOfKala);
        }
    }

    private void antiAirCraft(Player player, int numberOfKala, int currentAmount) {
        if (currentAmount < 30 * numberOfKala) {
            System.out.println("you don't have enough money");
        } else {
            player.reduceAmount(30 * numberOfKala);
            player.increaseAntiAirCraft(numberOfKala);
        }
    }

    private void mine(Player player, int currentAmount, int numberOfKala) {
        if (currentAmount < 1 * numberOfKala) {
            System.out.println("you don't have enough money");
        } else {
            player.reduceAmount(1 * numberOfKala);
            player.increaseMine(numberOfKala);
        }
    }

    private void help() {
        System.out.println("buy [product] [number]\n" +
                "show-amount\n" +
                "help\n" +
                "back");
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
