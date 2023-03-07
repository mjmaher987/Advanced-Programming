// Author: Moahammad Javad Maheronnaghsh
// Email: mjmaher987@gmail.com   |   m.j.maheronnaghsh@gmail.com

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Position.x = 50;
        Position.y = 50;
        Visited.array = new boolean[101][101];
        for (int z = 0; z < 101; z++) {
            for (int v = 0; v < 101; v++) {
                Visited.array[z][v] = false;
            }
        }
        Scanner in = new Scanner(System.in);
        int number = in.nextInt();
        for (int i = 0; i < number; i++) {
            int first = in.nextInt();
            int second = in.nextInt();
            if (AcceptableMove(first, second)) {
                move(first, second);
            }
        }
        in.close();
        System.out.println(Position.x + " " + Position.y);
    }

    public static class Position {
        public static int x;
        public static int y;
    }

    public static class Visited {
        public static boolean[][] array;
    }

    static boolean VisitedBefore(int x, int y) {
        if (x >= 0) {
            for (int i = 1; i <= x; i++) {
                if (Visited.array[Position.x + i][Position.y]) return true;
            }
        } else {
            for (int i = -1; i >= x; i--) {
                if (Visited.array[Position.x + i][Position.y]) return true;
            }
        }
        if (y >= 0) {
            for (int j = 1; j <= y; j++) {
                if (Visited.array[Position.x + x][Position.y + j]) return true;
            }
        } else {
            for (int j = -1; j >= y; j--) {
                if (Visited.array[Position.x + x][Position.y + j]) return true;
            }
        }
        if (x >= 0) makeAllOfThemTrueForTheFirstOne(x, y);
        else makeAllOfThemTrueForTheSecondOne(x, y);
        if (y >= 0) makeAllOfThemTrueForTheThirdOne(x, y);
        else makeAllOfThemTrueForTheFourthOne(x, y);
        return false;
    }

    private static void makeAllOfThemTrueForTheFourthOne(int x, int y) {
        for (int j1 = -1; j1 >= y; j1--) {
            Visited.array[Position.x + x][Position.y + j1] = true;
        }
    }

    private static void makeAllOfThemTrueForTheThirdOne(int x, int y) {
        for (int j1 = 1; j1 <= y; j1++) {
            Visited.array[Position.x + x][Position.y + j1] = true;
        }
    }

    private static void makeAllOfThemTrueForTheSecondOne(int x, int y) {
        for (int i1 = -1; i1 >= x; i1--) {
            Visited.array[Position.x + i1][Position.y] = true;
        }
    }

    private static void makeAllOfThemTrueForTheFirstOne(int x, int y) {
        for (int i1 = 1; i1 <= x; i1++) {
            Visited.array[Position.x + i1][Position.y] = true;
        }
    }

    static boolean OutOfBoundaries(int x, int y) {
        if (Position.x + x > 100 || Position.x + x < 1) {
            return true;
        }
        if (Position.y + y > 100 || Position.y + y < 1) {
            return true;
        }
        return false;
    }

    static void move(int x, int y) {
        Position.x += x;
        Position.y += y;
    }

    static boolean AcceptableMove(int x, int y) {
        if (OutOfBoundaries(x, y)) {
            return false;
        } else return !VisitedBefore(x, y);
    }
}
