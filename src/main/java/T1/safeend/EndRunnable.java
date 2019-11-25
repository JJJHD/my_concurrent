package T1.safeend;

import util.SleepTools;

public class EndRunnable {

    private static class UseRunnable implements Runnable{

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + "interrupted flag is "
                    +Thread.currentThread().isInterrupted());
            SleepTools.second(5);
            while (Thread.currentThread().isInterrupted()){
                System.out.println(threadName+" is run ");
            }
            System.out.println(threadName + "interrupted flag is "
            +Thread.currentThread().isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UseRunnable useRunnable = new UseRunnable();
        Thread endThread = new Thread(useRunnable);
        endThread.start();
        endThread.interrupt();
    }
}
