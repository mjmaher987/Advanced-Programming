import java.util.*;
import java.util.regex.*;
import java.lang.String;

public class Main {

    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Scanner scan = new Scanner(System.in);
        boolean continueScanning = true;
        while (continueScanning) {
            String scanner = scan.nextLine();
            scanner = scanner.trim();
            if (stringNCompare(scanner, "ADD DOC ", 8) == 0) addDocument(scanner, hashMap, scan.nextLine());
            else if (stringNCompare(scanner, "RMV DOC ", 8) == 0) removeDocument(scanner, hashMap);
            else if (stringNCompare(scanner, "RPLC -ALL ", 10) == 0) replaceAllDocument(scanner, hashMap);
            else if (stringNCompare(scanner, "RPLC ", 5) == 0) replaceDocument(scanner, hashMap);
            else if (stringNCompare(scanner, "RMV WORD -ALL ", 14) == 0) removeAllWord(scanner, hashMap);
            else if (stringNCompare(scanner, "RMV WORD ", 9) == 0) removeWord(scanner, hashMap);
            else if (stringNCompare(scanner, "ADD WORD -ALL ", 14) == 0) addAllWord(scanner, hashMap);
            else if (stringNCompare(scanner, "ADD WORD ", 9) == 0) addWord(scanner, hashMap);
            else if (stringNCompare(scanner, "FIND REP ", 9) == 0) findRepetition(scanner, hashMap);
            else if (stringNCompare(scanner, "GCD ", 4) == 0) GCD(scanner, hashMap);
            else if (stringNCompare(scanner, "FIND MIRROR ", 12) == 0) mirror(scanner, hashMap);
            else if (stringNCompare(scanner, "FIND ALPHABET WORDS ", 20) == 0) alphabet(scanner, hashMap);
            else if (stringNCompare(scanner, "PRINT ", 6) == 0) print(scanner, hashMap);
            else if (stringNCompare(scanner, "END", 3) == 0 && scanner.length() == 3) continueScanning = false;
            else {
                System.out.println("invalid command!");
            }
        }
    }

    public static void mirror(String scanner, HashMap<String, String> hashMap) {
        if (!pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^FIND MIRROR (\\S*) ([a-zA-Z])$", scanner, 1).equals("")) {
            boolean didWeFoundThisDocument = false;
            for (String i : hashMap.keySet()) {
                if (i.equals(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^FIND MIRROR (\\S*) ([a-zA-Z])$", scanner, 1))) {
                    didWeFoundThisDocument = true;
                    String searchForAlphabeticalWords = hashMap.get(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^FIND MIRROR (\\S*) ([a-zA-Z])$", scanner, 1));
                    int counter = 0;
                    Pattern pattern = Pattern.compile("(\\d+)" + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^FIND MIRROR (\\S*) ([a-zA-Z])$", scanner, 2) + "(\\d+)");
                    Matcher matcher = pattern.matcher(searchForAlphabeticalWords);
                    while (matcher.find()) {
                        if (Integer.parseInt(matcher.group(1)) == Integer.parseInt(matcher.group(2))) counter++;
                    }
                    System.out.println(counter + " mirror words!");
                }
            }
            if (!didWeFoundThisDocument) {
                System.out.println("invalid file name!");
            }
        } else {
            System.out.println("invalid command!");
        }
    }

    public static void alphabet(String scanner, HashMap<String, String> hashMap) {
        if (!pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^FIND ALPHABET WORDS (\\S*)$", scanner, 1).equals("")) {
            boolean didWeFoundThisDocument = false;
            for (String i : hashMap.keySet()) {
                if (i.equals(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^FIND ALPHABET WORDS (\\S*)$", scanner, 1))) {
                    didWeFoundThisDocument = true;
                    String searchForAlphabeticalWords = hashMap.get(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^FIND ALPHABET WORDS (\\S*)$", scanner, 1));
                    int counter = 0;
                    ArrayList<String> elephantList = new ArrayList<>(Arrays.
                            asList(searchForAlphabeticalWords.split("[\\s]+")));
                    counter = elephantList.size();
                    System.out.println(counter + " alphabetical words!");
                }
            }
            if (!didWeFoundThisDocument) {
                System.out.println("invalid file name!");
            }
        } else {
            System.out.println("invalid command!");
        }
    }

    public static void print(String scanner, HashMap<String, String> hashMap) {
        if (!pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^PRINT (\\S*)$", scanner, 1).equals("")) {
            boolean didWeFoundThisDocument = false;
            for (String i : hashMap.keySet()) {
                if (i.equals(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^PRINT (\\S*)$", scanner, 1))) {
                    didWeFoundThisDocument = true;
                    System.out.println(hashMap.get(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^PRINT (\\S*)$", scanner, 1)));
                }
            }
            if (!didWeFoundThisDocument) {
                System.out.println("invalid file name!");
            }
        } else {
            System.out.println("invalid command!");
        }
    }

    public static void GCD(String scanner, HashMap<String, String> hashMap) {
        if (!pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^GCD (\\S*)$", scanner, 1).equals("")) {
            boolean didWeFoundThisDocument = false;
            for (String i : hashMap.keySet()) {
                if (i.equals(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^GCD (\\S*)$", scanner, 1))) {
                    didWeFoundThisDocument = true;
                    String searchForGCD = hashMap.get(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^GCD (\\S*)$", scanner, 1));
                    int counter = 0;
                    int finalAnswer = 0;
                    Pattern pattern = Pattern.compile("([0-9])");
                    Matcher matcher = pattern.matcher(searchForGCD);
                    while (matcher.find()) {
                        finalAnswer = findBeMimMim(finalAnswer, Integer.parseInt(matcher.group(1)));
                        counter++;
                    }
                    if (counter != 0) {
                        searchForGCD = searchForGCD + finalAnswer;
                        hashMap.put(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^GCD (\\S*)$", scanner, 1), searchForGCD);
                    }
                }
            }
            if (!didWeFoundThisDocument) {
                System.out.println("invalid file name!");
            }
        } else {
            System.out.println("invalid command!");
        }
    }

    private static int findBeMimMim(int finalAnswer, int parseInt) {
        if (finalAnswer == 0) return parseInt;
        if (parseInt == 0) return finalAnswer;
        return findBeMimMim(parseInt, finalAnswer % parseInt);
    }

    public static void findRepetition(String scanner, HashMap<String, String> hashMap) {
        if (pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^FIND REP (\\S*) (.*)$", scanner, 1) != "") {
            boolean didWeFoundThisDocument = false;
            for (String i : hashMap.keySet()) {
                if (i.equals(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^FIND REP (\\S*) (.*)$", scanner, 1))) {
                    didWeFoundThisDocument = true;
                    int index = 0, count = 0;
                    while (true) {
                        index = hashMap.get(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^FIND REP (\\S*) (.*)$", scanner, 1))
                                .indexOf(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^FIND REP (\\S*) (.*)$", scanner, 2), index);
                        if (index != -1) {
                            count++;
                            index++;
                        } else {
                            break;
                        }
                    }
                    System.out.println(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^FIND REP (\\S*) (.*)$", scanner, 2) + " is repeated " +
                            count + " times in " + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^FIND REP (\\S*) (.*)$", scanner, 1));
                }
            }
            if (didWeFoundThisDocument == false) {
                System.out.println("invalid file name!");
            }

        } else {
            System.out.println("invalid command!");
        }
    }

    public static void addWord(String scanner, HashMap<String, String> hashMap) {
        if (pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^ADD WORD (\\S*) (.*)$", scanner, 1) != "") {
            String temporaryString;
            boolean didWeFoundThisDocument = false;
            for (String i : hashMap.keySet()) {
                if (i.equals(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^ADD WORD (\\S*) (.*)$", scanner, 1))) {
                    didWeFoundThisDocument = true;
                    temporaryString = hashMap.get(i);
                    temporaryString = temporaryString + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^ADD WORD (\\S*) (.*)$", scanner, 2);
                    hashMap.put(i, temporaryString);
                }
            }
            if (!didWeFoundThisDocument) {
                System.out.println("invalid file name!");
            }
        } else {
            System.out.println("invalid command!");
        }
    }

    public static void addAllWord(String scanner, HashMap<String, String> hashMap) {
        if (pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^ADD WORD -ALL (.*)$", scanner, 1) != "") {
            String temporaryString;
            for (String i : hashMap.keySet()) {
                temporaryString = hashMap.get(i);
                temporaryString = temporaryString + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^ADD WORD -ALL (.*)$", scanner, 1);
                hashMap.put(i, temporaryString);
            }
        } else {
            System.out.println("invalid command!");
        }
    }

    public static String pleaseGiveMeThisGroupMatcherThatIAmTellingYou(String thisPattern, String scanner, int i) {
        Pattern pattern = Pattern.compile(thisPattern);
        Matcher matcher = pattern.matcher(scanner);
        if (matcher.find()) {
            return matcher.group(i);
        }
        return "";
    }

    public static void removeWord(String scanner, HashMap<String, String> hashMap) {
        if (!pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RMV WORD (\\S*) (\\w*)$", scanner, 1).equals("")) {
            boolean didWeFoundThisDocument = false;
            for (String i : hashMap.keySet()) {
                if (i.equals(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RMV WORD (\\S*) (\\w*)$", scanner, 1))) {
                    didWeFoundThisDocument = true;
                    String searchForRemovalWords = hashMap.get(i);
                    searchForRemovalWords = searchForRemovalWords.replaceAll(" " + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RMV WORD (\\S*) (\\w*)$", scanner, 2) + " ", "  ");
                    searchForRemovalWords = searchForRemovalWords.replaceAll("^" + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RMV WORD (\\S*) (\\w*)$", scanner, 2) + " ", " ");
                    searchForRemovalWords = searchForRemovalWords.replaceAll(" " + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RMV WORD (\\S*) (\\w*)$", scanner, 2) + "$", " ");
                    searchForRemovalWords = searchForRemovalWords.replaceAll(" " + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RMV WORD (\\S*) (\\w*)$", scanner, 2) + " ", "");
                    hashMap.put(i, searchForRemovalWords);
                }
            }
            if (!didWeFoundThisDocument) {
                System.out.println("invalid file name!");
            }
        } else {
            System.out.println("invalid command!");
        }
    }

    public static void removeAllWord(String scanner, HashMap<String, String> hashMap) {
        if (!pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RMV WORD -ALL (\\w*)$", scanner, 1).equals("")) {
            for (String i : hashMap.keySet()) {
                String searchForRemovalWords = hashMap.get(i);
                searchForRemovalWords = searchForRemovalWords.replaceAll(" " + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RMV WORD -ALL (\\w*)$", scanner, 1) + " ", "  ");
                searchForRemovalWords = searchForRemovalWords.replaceAll("^" + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RMV WORD -ALL (\\w*)$", scanner, 1) + " ", " ");
                searchForRemovalWords = searchForRemovalWords.replaceAll(" " + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RMV WORD -ALL (\\w*)$", scanner, 1) + "$", " ");
                searchForRemovalWords = searchForRemovalWords.replaceAll(" " + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RMV WORD -ALL (\\w*)$", scanner, 1) + " ", "");
                hashMap.put(i, searchForRemovalWords);
            }
        } else {
            System.out.println("invalid command!");
        }
    }

    public static void replaceAllDocument(String scanner, HashMap<String, String> hashMap) {
        if (!pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RPLC -ALL (\\S+) (\\S+)$", scanner, 1).equals("")) {
            boolean didWeFoundThisDocument = false;
            ArrayList<String> elephantList = new ArrayList<>(Arrays.
                    asList(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RPLC -ALL (\\S+) (\\S+)$", scanner, 2).split(",")));
            for (String i : hashMap.keySet()) {
                didWeFoundThisDocument = true;
                for (int i1 = 0; i1 < elephantList.size(); i1++) {
                    String myWord = hashMap.get(i);
                    String replacement = " " + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RPLC (\\S+) (\\S+) (\\S+)$", scanner, 3) + " ";
                    String thisElephant = elephantList.get(i1);
                    thisElephant = " " + thisElephant + " ";
                    int start = myWord.lastIndexOf(thisElephant);
                    StringBuilder builder = new StringBuilder();
                    if (start != -1) {
                        builder.append(myWord, 0, start);
                        builder.append(replacement);
                        builder.append(myWord.substring(start + thisElephant.length()));
                        hashMap.put(i, String.valueOf(builder));
                    }
                }
            }
            if (!didWeFoundThisDocument) {
                System.out.println("invalid file name!");
            }
        } else {
            System.out.println("invalid command!");
        }
    }

    public static void replaceDocument(String scanner, HashMap<String, String> hashMap) {
        if (!pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RPLC (\\S+) (\\S+) (\\S+)$", scanner, 1).equals("")) {
            boolean didWeFoundThisDocument = false;
            ArrayList<String> elephantList = new ArrayList<>(Arrays.
                    asList(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RPLC (\\S+) (\\S+) (\\S+)$", scanner, 2).split(",")));
            for (String i : hashMap.keySet()) {
                if (i.equals(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RPLC (\\S+) (\\S+) (\\S+)$", scanner, 1))) {
                    didWeFoundThisDocument = true;
                    for (int i1 = 0; i1 < elephantList.size(); i1++) {
                        String myWord = hashMap.get(i);
                        String replacement = " " + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RPLC (\\S+) (\\S+) (\\S+)$", scanner, 3) + " ";
                        String thisElephant = elephantList.get(i1);
                        thisElephant = " " + thisElephant + " ";
                        int start = myWord.lastIndexOf(thisElephant);
                        StringBuilder builder = new StringBuilder();
                        if (start != -1) {
                            builder.append(myWord, 0, start);
                            builder.append(replacement);
                            builder.append(myWord.substring(start + thisElephant.length()));
                            hashMap.put(i, String.valueOf(builder));
                        }
                    }
                }
            }
            if (!didWeFoundThisDocument) {
                System.out.println("invalid file name!");
            }
        } else {
            System.out.println("invalid command!");
        }
    }

    public static void removeDocument(String scanner, HashMap<String, String> hashMap) {
        if (!pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RMV DOC (.*\\S)+$", scanner, 1).equals("")) {
            boolean didWeFoundThisDocument = false;
            String toRemove = "";
            for (String i : hashMap.keySet()) {
                if (i.equals(pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^RMV DOC (.*\\S)+$", scanner, 1))) {
                    didWeFoundThisDocument = true;
                    toRemove = i;
                }
            }
            if (didWeFoundThisDocument) {
                hashMap.remove(toRemove);
            }
            if (!didWeFoundThisDocument) {
                System.out.println("invalid file name!");
            }
        } else {
            System.out.println("invalid command!");
        }
    }

    public static void addDocument(String scanner, HashMap<String, String> hashMap, String s) {
        Pattern pattern = Pattern.compile("ADD DOC (.*\\S)+");
        Matcher matcher = pattern.matcher(scanner);
        String valueOfTheKey = s;
        if (matcher.find()) {
            for (String i : hashMap.keySet()) {
                if (hashMap.get(i).equals(matcher.group(1))) {
                    hashMap.remove(i);
                }
            }
            for (int i = 0; i < 10; i++) {
                valueOfTheKey = checkWhetherWeHaveAPicture(valueOfTheKey);
            }
            for (int i = 0; i < 10; i++) {
                valueOfTheKey = checkWhetherWeHaveALink(valueOfTheKey);
            }

            for (int i = 0; i < 10; i++)
                valueOfTheKey = checkWhetherWeHaveABoldWord(valueOfTheKey);
            for (int i = 0; i < 10; i++)
                valueOfTheKey = checkWhetherWeHaveANoise(valueOfTheKey);
            hashMap.put(matcher.group(1), valueOfTheKey);
        } else {
            System.out.println("invalid command!");
        }
    }

    private static String checkWhetherWeHaveALink(String valueOfTheKey) {
        valueOfTheKey = valueOfTheKey.replaceFirst(" " + "\\[(\\S*)\\]\\(.*?\\)" + " ",
                " " + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("\\[(\\S*)\\]\\(.*?\\)", valueOfTheKey, 1) + " ");
        valueOfTheKey = valueOfTheKey.replaceFirst("^\\[(\\S*)\\]\\(.*?\\)" + " ",
                pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^\\[(\\S*)\\]\\(.*?\\)", valueOfTheKey, 1) + " ");
        valueOfTheKey = valueOfTheKey.replaceFirst("^\\[(\\S*)\\]\\(.*?\\)$",
                pleaseGiveMeThisGroupMatcherThatIAmTellingYou("^\\[(\\S*)\\]\\(.*?\\)", valueOfTheKey, 1) );
        return valueOfTheKey;
    }

    private static String checkWhetherWeHaveAPicture(String valueOfTheKey) {
        valueOfTheKey = valueOfTheKey.replaceFirst("\\!{1}\\[(\\S*)\\]\\(\\S*\\)",
                pleaseGiveMeThisGroupMatcherThatIAmTellingYou("\\[(\\S*)\\]\\(\\S*\\)", valueOfTheKey, 1));
        return valueOfTheKey;
    }

    private static String checkWhetherWeHaveABoldWord(String valueOfTheKey) {
        valueOfTheKey = valueOfTheKey.replaceFirst(" " + "\\*\\*([\\w\\s]*?)\\*\\*" + " ",
                " " + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("\\*\\*([\\w\\s]*?)\\*\\*", valueOfTheKey, 1) + " ");
        valueOfTheKey = valueOfTheKey.replaceFirst(" " + "\\*\\*([\\w\\s]*?)\\*\\*" + "$",
                " " + pleaseGiveMeThisGroupMatcherThatIAmTellingYou("\\*\\*([\\w\\s]*?)\\*\\*", valueOfTheKey, 1));
        valueOfTheKey = valueOfTheKey.replaceFirst("^" + "\\*\\*([\\w\\s]*?)\\*\\*" + "$",
                pleaseGiveMeThisGroupMatcherThatIAmTellingYou("\\*\\*([\\w\\s]*?)\\*\\*", valueOfTheKey, 1));
        return valueOfTheKey;
    }

    private static String checkWhetherWeHaveANoise(String valueOfTheKey) {
        valueOfTheKey = valueOfTheKey.replaceFirst(" " + "[\\S]{1,20}[^0-9a-zA-Z\\s+][\\S]{1,20}" + " ", "  ");
        valueOfTheKey = valueOfTheKey.replaceFirst("^" + "[\\S]{1,20}[^0-9a-zA-Z\\s+][\\S]{1,20}" + " ", " ");
        valueOfTheKey = valueOfTheKey.replaceFirst(" " + "[\\S]{1,20}[^0-9a-zA-Z\\s+][\\S]{1,20}" + "$", " ");
        return valueOfTheKey;
    }

    public static int stringNCompare(String firstString, String secondString, int n) {
        if (firstString.length() < n || secondString.length() < n) return -1;
        firstString = firstString.substring(0, n);
        secondString = secondString.substring(0, n);
        return ((secondString.equals(firstString)) ? 0 : -1);
    }
}