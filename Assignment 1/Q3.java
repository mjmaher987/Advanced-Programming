// Author: Moahammad Javad Maheronnaghsh
// Email: mjmaher987@gmail.com   |   m.j.maheronnaghsh@gmail.com

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Main {

    public static void main(String[] args) {
        int verticalDimension = 0, horizontalDimension = 0, howManyTimes = 0;
        Scanner sc = new Scanner(System.in);
        verticalDimension = sc.nextInt();
        horizontalDimension = sc.nextInt();
        howManyTimes = sc.nextInt();
        howManyNumbers.repeatTimes = howManyTimes;
        map.map1 = new char[2 * verticalDimension + 1][2 * horizontalDimension + 1];
        visitedCells.isThisCellVisitedBefore = new boolean[2 * verticalDimension + 1][2 * horizontalDimension + 1];
        for (int i = 0; i < howManyTimes; i++) {
            generateMaze();
            if(i != 0){
                for(int counter = 0; counter < saved.savedAnswers.length; counter++){
                    if(saved.savedAnswers[i].equals(map.map1)) generateMaze();
                }
            }
            saved.savedAnswers[i] = map.map1;
            printGeneratedMaze();
        }
    }

    public static class map {
        public static char[][] map1;
    }

    public static class visitedCells {
        public static boolean[][] isThisCellVisitedBefore;
    }

    public static void generateMaze() {
        for (int i = 0; i < map.map1.length; i++) {
            Arrays.fill(visitedCells.isThisCellVisitedBefore[i], false);
            Arrays.fill(map.map1[i], '1');
        }
        for (int starCounter = 1; starCounter < map.map1.length; starCounter += 2) {
            for (int secondStarCounter = 1; secondStarCounter < map.map1[0].length; secondStarCounter += 2) {
                map.map1[starCounter][secondStarCounter] = '*';
            }
        }
        map.map1[0][1] = 'e';
        map.map1[map.map1.length - 1][map.map1[0].length - 2] = 'e';
        startDfsMethod(1, 1);
    }

    public static void startDfsMethod(int y, int x) {
        visitedCells.isThisCellVisitedBefore[y][x] = true;
        int generatedRandomNumber = (ThreadLocalRandom.current().nextInt() % 4), count = 0;
        if(generatedRandomNumber < 0) generatedRandomNumber -= generatedRandomNumber;
        while (count < 4) {
            if (y - 2 >= 0 && generatedRandomNumber == 0) {
                if (!visitedCells.isThisCellVisitedBefore[y - 2][x]) {
                    map.map1[y - 1][x] = '0';
                    startDfsMethod(y - 2, x);
                }
            } else if (x + 2 <= map.map1[0].length - 1 && generatedRandomNumber == 1) {
                if (!visitedCells.isThisCellVisitedBefore[y][x + 2]) {
                    map.map1[y][x + 1] = '0';
                    startDfsMethod(y, x + 2);
                }
            } else if (y + 2 <= map.map1.length - 1 && generatedRandomNumber == 2) {
                if (!visitedCells.isThisCellVisitedBefore[y + 2][x]) {
                    map.map1[y + 1][x] = '0';
                    startDfsMethod(y + 2, x);
                }
            } else if (x - 2 >= 0 && generatedRandomNumber == 3) {
                if (!visitedCells.isThisCellVisitedBefore[y][x - 2]) {
                    map.map1[y][x - 1] = '0';
                    startDfsMethod(y, x - 2);
                }
            }
            count++;
            generatedRandomNumber = (++generatedRandomNumber) % 4;
        }
    }

    public static class howManyNumbers {
        public static int repeatTimes;
    }

    public static class saved {
        public static char[][][] savedAnswers = new char[100][100][100];
    }

    public static void printGeneratedMaze() {
        System.out.println(Arrays.deepToString(map.map1).replaceAll("],", "\n").replaceAll(",", "").replaceAll("[\\[\\]]", "").replaceAll(" ", ""));
        System.out.println();
    }


}
