package Bank;
//all of these are copied and pasted from http://tutorials.jenkov.com/java-concurrency/thread-pools.html
//and also some of them are written from Mr.JahaniNejad's code during one of the classes
import Tasks.Task;

import java.util.ArrayList;

public class Bank {
    private static int size = -1;
    private ATM[] allAtms;
    private ThreadPoolFromNet executor;


    public Bank(int AtmCount) {
        allAtms = new ATM[AtmCount];
        for (int i = 0; i < allAtms.length; i++) {
            allAtms[i] = new ATM();
        }
        executor = new ThreadPoolFromNet(AtmCount, 3000);
    }


    public static synchronized void increaseSize() {
        size++;
    }

    public static synchronized void decreaseSize() {
        size--;
    }


    public ArrayList<Object> runATM(ArrayList<Task> tasks, Handler handler) {
        ArrayList<Object> result = new ArrayList<>();


        try {
            executor.execute(() -> {
                increaseSize();
                int thisAtm = size;
                long start = System.currentTimeMillis();

                for (int i = 0; i < tasks.size(); i++) {
                    tasks.get(i).setATM(allAtms[thisAtm]);
                    try {
                        result.add(tasks.get(i).run());
                    } catch (Exception e) {
                        result.add(e);
                    }
                }

                decreaseSize();
                handler.done();
                long end = System.currentTimeMillis();
                System.out.println("done : task " + tasks + " in " + (end - start) + "milliseconds");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
