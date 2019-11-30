package T4.aqs;

import util.SleepTools;

import java.util.concurrent.locks.Lock;

public class TestMyLock {
    public void test(){
        final Lock lock = new SelfLock();
        class Worker extends Thread{
            @Override
            public void run(){
                while (true){
                    lock.lock();
                    try {
                        SleepTools.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepTools.second(1);
                    }finally {
                        lock.unlock();
                    }
                    SleepTools.second(2);
                }
            }
        }

        for (int i=0;i<10;i++){
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        for (int i=0;i<10;i++){
            SleepTools.second(1);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TestMyLock testMyLock = new TestMyLock();
        testMyLock.test();
    }
}
