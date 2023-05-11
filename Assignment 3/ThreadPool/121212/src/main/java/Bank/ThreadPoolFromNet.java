package Bank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//all of these are copied and pasted from http://tutorials.jenkov.com/java-concurrency/thread-pools.html
public class ThreadPoolFromNet {
    private BlockingQueue taskQueue = null;
    private List<PoolThreadRunnable> runnables = new ArrayList<>();
    private boolean isStopped = false;

    public ThreadPoolFromNet(int noOfThreads, int maxNoOfTasks) {
        taskQueue = new ArrayBlockingQueue(maxNoOfTasks);

        for (int i = 0; i < noOfThreads; i++) {
            PoolThreadRunnable poolThreadRunnable =
                    new PoolThreadRunnable(taskQueue);

            runnables.add(new PoolThreadRunnable(taskQueue));
        }
        for (PoolThreadRunnable runnable : runnables) {
            new Thread(runnable).start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (this.isStopped) throw
                new IllegalStateException("ThreadPool is stopped");

        this.taskQueue.offer(task);
    }

    public synchronized void stop() {
        this.isStopped = true;
        for (PoolThreadRunnable runnable : runnables) {
            runnable.doStop();
        }
    }

    public synchronized void waitUntilAllTasksFinished() {
        while (this.taskQueue.size() > 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
