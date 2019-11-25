package T1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class NewThread {

    private static class UseThread extends Thread{
        @Override
        public void run(){
            System.out.println("I am extends Thread..");
        }
    }

    private static class UseRun implements Runnable{

        @Override
        public void run() {
            System.out.println("I am implements Runnable..");
        }
    }

    private static class UseCall implements Callable<String>{

        @Override
        public String call() throws Exception {
            System.out.println("I am implements Callable..");
            return "CallResult";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UseThread useThread = new UseThread();
        useThread.start();

        UseRun useRun = new UseRun();
        new Thread(useRun).start();

        UseCall useCall = new UseCall();
        FutureTask<String> future = new FutureTask<String>(useCall);
        new Thread(future).start();
        System.out.println(future.get());
    }


}
