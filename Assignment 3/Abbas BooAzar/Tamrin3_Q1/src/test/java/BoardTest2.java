import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest2 {

    @Test
    void allPerimeter() {
        RandomShape2 rd1 = new RandomShape2();
        RandomShape2 rd2 = new RandomShape2();
        RandomShape2 rd3 = new RandomShape2();
        Board<Drawable> board = new Board<>();

        board.addNewShape(rd1);
        board.addNewShape(rd2);
        board.addNewShape(rd3);

        double perimeter = rd1.getPerimeter() + rd2.getPerimeter() + rd3.getPerimeter();
        assertEquals(board.allPerimeter() , perimeter);
    }

    @Test
    void allSurface() {
        RandomShape2 rd1 = new RandomShape2();
        RandomShape2 rd2 = new RandomShape2();
        RandomShape2 rd3 = new RandomShape2();
        Board<Drawable> board = new Board<>();

        board.addNewShape(rd1);
        board.addNewShape(rd2);
        board.addNewShape(rd3);

        double surface = rd1.getSurface() + rd2.getSurface() + rd3.getSurface();
        assertEquals(board.allSurface() , surface);
    }

    @Test
    void allSide() {
        RandomShape2 rd1 = new RandomShape2();
        RandomShape2 rd2 = new RandomShape2();
        RandomShape2 rd3 = new RandomShape2();
        Circle c = new Circle(10);
        Board<Drawable> board = new Board<>();

        board.addNewShape(rd1);
        board.addNewShape(rd2);
        board.addNewShape(rd3);
        board.addNewShape(c);

        double side = rd1.getSide() + rd2.getSide() + rd3.getSide();
        assertEquals(board.allSide() , side);
    }

    @Test
    void allSideException() {
        RandomShape2 rd1 = new RandomShape2();
        RandomShape2 rd2 = new RandomShape2();
        RandomShape2 rd3 = new RandomShape2();
        Circle c = new Circle(10);
        Board<Drawable> board = new Board<>();

        board.addNewShape(rd1);
        board.addNewShape(rd2);
        board.addNewShape(rd3);
        board.addNewShape(c);

        double side = rd1.getSide() + rd2.getSide() + rd3.getSide();
        try {
            assertEquals(board.allSideException() , side);
        } catch (SideNotDefinedException e) {
        }
    }

    @Test
    void minimumSurface() {
        Circle c1 = new Circle(10);
        Circle c2 = new Circle(20);
        Circle c3 = new Circle(30);
        Board<Drawable> board = new Board<>();

        board.addNewShape(c2);
        board.addNewShape(c1);
        board.addNewShape(c3);

        assertEquals(board.minimumSurface(), c1);
    }

    @Test
    void sortedList() {
        Circle c1 = new Circle(10);
        Circle c2 = new Circle(20);
        Circle c3 = new Circle(30);
        Board<Drawable> board = new Board<>();

        board.addNewShape(c3);
        board.addNewShape(c1);
        board.addNewShape(c2);

        assertEquals(board.sortedList(70).get(0), c2);
        assertEquals(board.sortedList(70).get(1), c3);
    }
}