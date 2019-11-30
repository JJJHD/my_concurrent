package T2.tools;

import util.SleepTools;

import java.util.concurrent.CountDownLatch;

public class UseCountDownLatch {
    static CountDownLatch latch = new CountDownLatch(6);
    private static class InitThread implements Runnable{

        @Override
        public void run() {
            System.out.println("Thread["+Thread.currentThread().getId()+"] read" +
                    " init work...");
            latch.countDown();
            for (int i=0;i<2;i++){
                System.out.println("Thread["+Thread.currentThread().getId()+"] continue " +
                        " to do its work");
            }
        }
    }

    private static class BusiThread implements Runnable{

        @Override
        public void run() {
            try {
                latch.await();
            }catch (Exception e){
                e.printStackTrace();
            }
            for (int i=0;i<3;i++){
                System.out.println("BusiThread["+Thread.currentThread().getId()+"] " +
                        " do business...");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SleepTools.ms(1);
                System.out.println("Thread["+Thread.currentThread().getId()+"] " +
                        " ready init work step 1st ... ");
                latch.countDown();
                System.out.println("begin step 2nd");
                SleepTools.ms(1);
                System.out.println("Thread["+Thread.currentThread().getId()+"] " +
                        " ready init work step 2nd ... ");
                latch.countDown();
            }
        }).start();
        new Thread(new BusiThread()).start();
        for (int i=0;i<=3;i++){
            Thread thread = new Thread(new InitThread());
            thread.start();
        }
        latch.await();
        System.out.println("Main do work end");
    }
}
