import java.util.Scanner;
import java.util.regex.*;
import java.lang.String;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Pattern pattern = Pattern.compile("Message\\{ messageId=%[\\d\\s]*-[B-GI[J-LNP-RU-Z]]{1}[b-gi[j-lnp-ru-z]]{4}\\$([\\d]{4}|[\\d]{2})\\%, " +
                "from\\=User\\{ firstName='(.{1,35})', isBot=(.{1,6}), lastName='(.{0,35})', userName='([a-zA-Z]{1}[\\w]{3,30}[^_]|)' \\}," +
                " date=([\\d]{14}), text='(.{0,70})', location=([-\\d]*[\\.]*[\\d]*) \\}");
        Matcher matcher = pattern.matcher(scan.nextLine());
        String startTime = scan.nextLine(), endTime = scan.nextLine(), currentPosition = scan.nextLine();
        boolean continuePrinting = true;
        while (continuePrinting) {
            int firstTime = Integer.parseInt(startTime), secondTime = Integer.parseInt(endTime), minusCounter = 0, dotCounter = 0;
            float theLocationThatWasSentForMe, position;
            if (pleaseCountTheNumberOfDotsInThisString(currentPosition) >= 2) theLocationThatWasSentForMe = -10000000;
            else theLocationThatWasSentForMe = Float.parseFloat(currentPosition);
            if (matcher.find()) {
                int time = Integer.parseInt(String.valueOf(justGiveMeTheFirstEightCharacters(matcher.group(6))));
                for (int k = 0; k < matcher.group(8).length(); k++) {
                    if (matcher.group(8).charAt(k) == '-') ++minusCounter;
                    if (matcher.group(8).charAt(k) == '.') ++dotCounter;
                }
                if (minusCounter <= 1 && dotCounter <= 1) position = Float.parseFloat(matcher.group(8));
                else position = -100000;
                if (isTimeAcceptable(time, firstTime, secondTime) && isRoboticAnswerAcceptable(matcher.group(3))
                        && isLocationAcceptable(theLocationThatWasSentForMe, position) && position != -100000) {
                    if (!isItARobot(matcher.group(3)) && !robot.robotBoolean)
                        printEveryThing(matcher.group(2), matcher.group(4), matcher.group(7), matcher.group(6));
                    if (robot.robotBoolean) robot.robotBoolean = false;
                }
                if (isItARobot(matcher.group(3)) && isRoboticAnswerAcceptable(matcher.group(3)) && position != -100000) robot.robotBoolean = true;
            } else continuePrinting = false;
        }
    }

    private static int pleaseCountTheNumberOfDotsInThisString(String currentPosition) {
        int count = 0;
        for (int i = 0; i < currentPosition.length(); i++) {
            if (currentPosition.charAt(i) == '.') count++;
        }
        return count;
    }

    private static char[] justGiveMeTheFirstEightCharacters(String whatTimeWasThisMessageSent) {
        char[] temporary = new char[8];
        for (int k = 0; k < 8; k++) {
            temporary[k] = whatTimeWasThisMessageSent.charAt(k);
        }
        return temporary;
    }

    public static class robot {
        public static boolean robotBoolean;
    }

    public static boolean isTimeAcceptable(int time, int firstTime, int secondTime) {
        return time >= firstTime && time <= secondTime;
    }

    public static boolean isLocationAcceptable(float theLocationThatWasSentForMe, float position) {
        float difference = theLocationThatWasSentForMe - position;
        if (difference >= 0 && difference <= 1)
            return true;
        return difference <= 0 && difference >= -1;
    }

    public static boolean isRoboticAnswerAcceptable(String roboticAnswer) {
        if (roboticAnswer.equals("true"))
            return true;
        return roboticAnswer.equals("false");
    }

    public static boolean isItARobot(String robotAnswer) {
        return robotAnswer.equals("true");
    }

    public static void printEveryThing(String firstName, String lastName, String message, String whatTimeWasThisMessageSent) {
        System.out.println("--------------------");
        System.out.println("*" + firstName + " " + lastName + "*");
        System.out.println(message);
        System.out.println("_" + whatTimeWasThisMessageSent.charAt(8) + whatTimeWasThisMessageSent.charAt(9) + ":" +
                whatTimeWasThisMessageSent.charAt(10) + whatTimeWasThisMessageSent.charAt(11) + "_");
        System.out.println("--------------------");
    }
}

