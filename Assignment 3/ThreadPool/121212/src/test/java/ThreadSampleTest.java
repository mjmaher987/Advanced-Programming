

import Bank.Bank;
import Bank.Card;
import Bank.Handler;
import Tasks.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ThreadSampleTest {

    @BeforeEach
    void eraseData() {
        Card.eraseData();
    }
    @Test
        // Test parallel tasks
    void Test1() {
        Bank bank = new Bank(2);
        ArrayList<Task> firstTasks = new ArrayList<>();
        ArrayList<Task> secondTasks = new ArrayList<>();
        firstTasks.add(new CreateAccountTask("12345", "1234"));
        secondTasks.add(new CreateAccountTask("1234567", "1234"));
        Handler hOne = new Handler();
        Handler hTwo = new Handler();
        long startTime = System.currentTimeMillis();
        System.out.println("running first task... " + firstTasks);
        bank.runATM(firstTasks, hOne);
        System.out.println("running second task... " + secondTasks);
        bank.runATM(secondTasks, hTwo);
        while (hOne.notFinished() || hTwo.notFinished()){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        Assertions.assertTrue((endTime - startTime) >= 2000);
        Assertions.assertTrue((endTime - startTime) < 4000);
    }

    //Test if thread is killed after exception
    @Test
    void Test2() {
        Bank bank = new Bank(2);
        ArrayList<Task> failingTask = new ArrayList<>();
        failingTask.add(new ChangePasswordTask("654321"));
        Handler hFail = new Handler();
        Handler hOne = new Handler();
        Handler hTwo = new Handler();

        bank.runATM(failingTask, hFail);
        ArrayList<Task> firstTasks = new ArrayList<>();
        ArrayList<Task> secondTasks = new ArrayList<>();
        firstTasks.add(new CreateAccountTask("12345", "1234"));
        secondTasks.add(new CreateAccountTask("1234567", "1234"));
        long startTime = System.currentTimeMillis();
        bank.runATM(firstTasks, hOne);
        bank.runATM(secondTasks, hTwo);
        while (hFail.notFinished() || hOne.notFinished() || hTwo.notFinished()){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();

        Assertions.assertTrue((endTime - startTime) >= 2000);
        Assertions.assertTrue((endTime - startTime) < 4000);
    }

    @Test
    void Test3() {
        Bank bank = new Bank(2);
        Card cardOne = new Card("12345", "1234");
        Card cardTwo = new Card("23456", "1234");
        Card cardThree = new Card("34567", "1234");
        ArrayList<Task> taskOne = new ArrayList<>();
        taskOne.add(new InsertCardTask(cardOne, "1234"));
        taskOne.add(new DepositCashTask(200));
        taskOne.add(new MoveBalanceTask(300, "23456"));
        taskOne.add(new MoveBalanceTask(100, "23456"));
        taskOne.add(new RemoveCardTask());

        ArrayList<Task> taskTwo = new ArrayList<>();
        taskTwo.add(new InsertCardTask(cardTwo, "1234"));
        taskTwo.add(new MoveBalanceTask(100, "34567"));
        taskTwo.add(new RemoveCardTask());

        Handler hOne = new Handler();
        Handler hTwo = new Handler();
        System.out.println("running second task... " + taskTwo);
        bank.runATM(taskTwo, hTwo);
        System.out.println("running first task... " + taskOne);
        bank.runATM(taskOne, hOne);
        while (hOne.notFinished() || hTwo.notFinished()){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Assertions.assertEquals(100, cardOne.getBalance());
        Assertions.assertEquals(100, cardTwo.getBalance());
        Assertions.assertEquals(0, cardThree.getBalance());
    }

    @Test
    void Test4() {
        Bank bank = new Bank(5);
        ArrayList<Task> one = new ArrayList<>();
        ArrayList<Task> two = new ArrayList<>();
        ArrayList<Task> three = new ArrayList<>();
        ArrayList<Task> four = new ArrayList<>();
        ArrayList<Task> five = new ArrayList<>();
        one.add(new CreateAccountTask("12345", "1234"));
        two.add(new CreateAccountTask("23451", "1234"));
        three.add(new CreateAccountTask("34512", "1234"));
        four.add(new CreateAccountTask("45123", "1234"));
        five.add(new CreateAccountTask("51234", "1234"));

        Handler hOne = new Handler();
        Handler hTwo = new Handler();
        Handler hThree = new Handler();
        Handler hFour = new Handler();
        Handler hFive = new Handler();

        System.out.println("running first task... " + one);
        bank.runATM(one, hOne);
        System.out.println("running second task... " + two);
        bank.runATM(two, hTwo);
        System.out.println("running third task... " + three);
        bank.runATM(three, hThree);
        System.out.println("running fourth task... " + four);
        bank.runATM(four, hFour);
        System.out.println("running fifth task... " + five);
        bank.runATM(five, hFive);

        while (hOne.notFinished() || hTwo.notFinished() || hThree.notFinished() || hFour.notFinished() || hFive.notFinished()){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Assertions.assertEquals(5, Card.getAllCards().size());

    }

}

//import Bank.Bank;
//import Bank.Card;
//import Bank.Handler;
//import Tasks.*;
//import org.junit.jupiter.api.Assertions;
//
//import org.junit.jupiter.api.BeforeEach;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//public class ThreadSampleTest {
//
//    @BeforeEach
//    void eraseData() {
//        Card.eraseData();
//    }
//    @Test
//    // Test parallel tasks
//    void Test1() {
//        Bank bank = new Bank(2);
//        ArrayList<Tasks.Task> firstTasks = new ArrayList<>();
//        ArrayList<Tasks.Task> secondTasks = new ArrayList<>();
//        firstTasks.add(new CreateAccountTask("12345", "1234"));
//        secondTasks.add(new CreateAccountTask("1234567", "1234"));
//        Handler hOne = new Handler();
//        Handler hTwo = new Handler();
//        long startTime = System.currentTimeMillis();
//        System.out.println("running first task... " + firstTasks);
//        bank.runATM(firstTasks, hOne);
//        System.out.println("running second task... " + secondTasks);
//        bank.runATM(secondTasks, hTwo);
//        while (hOne.notFinished() || hTwo.notFinished()){
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        long endTime = System.currentTimeMillis();
//        Assertions.assertTrue((endTime - startTime) >= 2000);
//        Assertions.assertTrue((endTime - startTime) < 4000);
//    }
//
//    //Test if thread is killed after exception
//    @Test
//    void Test2() {
//        Bank bank = new Bank(2);
//        ArrayList<Task> failingTask = new ArrayList<>();
//        failingTask.add(new ChangePasswordTask("654321"));
//        Handler hFail = new Handler();
//        Handler hOne = new Handler();
//        Handler hTwo = new Handler();
//
//        bank.runATM(failingTask, hFail);
//        ArrayList<Task> firstTasks = new ArrayList<>();
//        ArrayList<Task> secondTasks = new ArrayList<>();
//        firstTasks.add(new CreateAccountTask("12345", "1234"));
//        secondTasks.add(new CreateAccountTask("1234567", "1234"));
//        long startTime = System.currentTimeMillis();
//        bank.runATM(firstTasks, hOne);
//        bank.runATM(secondTasks, hTwo);
//        while (hFail.notFinished() || hOne.notFinished() || hTwo.notFinished()){
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        long endTime = System.currentTimeMillis();
//
//        Assertions.assertTrue((endTime - startTime) >= 2000);
//        Assertions.assertTrue((endTime - startTime) < 4000);
//    }
//
//    @Test
//    void Test3() {
//        Bank bank = new Bank(2);
//        Card cardOne = new Card("12345", "1234");
//        Card cardTwo = new Card("23456", "1234");
//        Card cardThree = new Card("34567", "1234");
//        ArrayList<Task> taskOne = new ArrayList<>();
//        taskOne.add(new InsertCardTask(cardOne, "1234"));
//        taskOne.add(new DepositCashTask(200));
//        taskOne.add(new MoveBalanceTask(300, "23456"));
//        taskOne.add(new MoveBalanceTask(100, "23456"));
//        taskOne.add(new RemoveCardTask());
//
//        ArrayList<Task> taskTwo = new ArrayList<>();
//        taskTwo.add(new InsertCardTask(cardTwo, "1234"));
//        taskTwo.add(new MoveBalanceTask(100, "34567"));
//        taskTwo.add(new RemoveCardTask());
//
//        Handler hOne = new Handler();
//        Handler hTwo = new Handler();
//        System.out.println("running second task... " + taskTwo);
//        bank.runATM(taskTwo, hTwo);
//        System.out.println("running first task... " + taskOne);
//        bank.runATM(taskOne, hOne);
//        while (hOne.notFinished() || hTwo.notFinished()){
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        Assertions.assertEquals(100, cardOne.getBalance());
//        Assertions.assertEquals(100, cardTwo.getBalance());
//        Assertions.assertEquals(0, cardThree.getBalance());
//    }
//
//    @Test
//    void Test4() {
//        Bank bank = new Bank(5);
//        ArrayList<Task> one = new ArrayList<>();
//        ArrayList<Task> two = new ArrayList<>();
//        ArrayList<Task> three = new ArrayList<>();
//        ArrayList<Task> four = new ArrayList<>();
//        ArrayList<Task> five = new ArrayList<>();
//        one.add(new CreateAccountTask("12345", "1234"));
//        two.add(new CreateAccountTask("23451", "1234"));
//        three.add(new CreateAccountTask("34512", "1234"));
//        four.add(new CreateAccountTask("45123", "1234"));
//        five.add(new CreateAccountTask("51234", "1234"));
//
//        Handler hOne = new Handler();
//        Handler hTwo = new Handler();
//        Handler hThree = new Handler();
//        Handler hFour = new Handler();
//        Handler hFive = new Handler();
//
//        System.out.println("running first task... " + one);
//        bank.runATM(one, hOne);
//        System.out.println("running second task... " + two);
//        bank.runATM(two, hTwo);
//        System.out.println("running third task... " + three);
//        bank.runATM(three, hThree);
//        System.out.println("running fourth task... " + four);
//        bank.runATM(four, hFour);
//        System.out.println("running fifth task... " + five);
//        bank.runATM(five, hFive);
//
//        while (hOne.notFinished() || hTwo.notFinished() || hThree.notFinished() || hFour.notFinished() || hFive.notFinished()){
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        Assertions.assertEquals(5, Card.getAllCards().size());
//
//    }
//
//}
