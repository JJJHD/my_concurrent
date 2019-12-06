package T7;

import util.SleepTools;

public class NormalDeadLock {
    private static Object valueFirst = new Object();
    private static Object valueSecond = new Object();

    private static void firstToSecond(){
        String threadName = Thread.currentThread().getName();
        synchronized (valueFirst){
            System.out.println(threadName+" get first.");
            SleepTools.ms(500);
            synchronized (valueSecond){
                System.out.println(threadName+" get second.");
            }
        }
    }

    private static void SecondToFirst(){
        String threadName = Thread.currentThread().getName();
        synchronized (valueSecond){
            System.out.println(threadName+" get second.");
            SleepTools.ms(100);
            synchronized (valueFirst){
                System.out.println(threadName+" get first.");
            }
        }
    }

    private static class TestThread extends Thread{
        private String name;
        public TestThread(String name){
            this.name = name;
        }

        @Override
        public void run(){
            Thread.currentThread().setName(name);
            try {
                SecondToFirst();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("TestDeadLock");
        TestThread testThread = new TestThread("SubTestThread");
        testThread.start();
        try {
            firstToSecond();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
