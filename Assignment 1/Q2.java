// Author: Moahammad Javad Maheronnaghsh
// Email: mjmaher987@gmail.com   |   m.j.maheronnaghsh@gmail.com


import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int NumberOfDays = scanner.nextInt();
        scanner.nextLine();
        String string = scanner.nextLine();
        ArrayList<String> bags = new ArrayList<>();
        String[] splited = string.split("\\s+");
        makeItLowerCase(bags, splited);
        bags = (ArrayList<String>) bags.stream().distinct().collect(Collectors.toList());
        for (int i = 1; i < NumberOfDays; i++) {
            String string2 = scanner.nextLine();
            ArrayList<String> bags2 = new ArrayList<>();
            String[] splited2 = string2.split("\\s+");
            makeItLowerCase(bags2, splited2);
            bags.retainAll(bags2);
        }
        String removal = scanner.nextLine();
        String[] removeSplitor = removal.split("\\s+");
        removeFunction(bags, removeSplitor);
        for (int Counter = 1; Counter < NumberOfDays; Counter++) {
            String string2 = scanner.nextLine();
            String[] splited2 = string2.split("\\s+");
            removeFunction(bags, splited2);
        }
        Collections.sort(bags, Collections.reverseOrder());
        if (bags.size() == 0) System.out.println("Nothing in common");
        else {
            printAll(bags);
        }
    }

    private static void printAll(ArrayList<String> bags) {
        for (int c = 0; c < bags.size(); c++) {
            System.out.print(bags.get(c).toLowerCase() + " ");
        }
    }

    private static void removeFunction(ArrayList<String> bags, String[] removeSplitor) {
        for (int j = 0; j < removeSplitor.length; j++) {
            bags.remove(removeSplitor[j].toLowerCase());
        }
    }

    private static void makeItLowerCase(ArrayList<String> bags, String[] splited) {
        for (int j = 0; j < splited.length; j++) {
            bags.add(splited[j].toLowerCase());
        }
    }

}
