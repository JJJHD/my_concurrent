package T1.safeend;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HasInterrputException {

    private static SimpleDateFormat formater =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss_SSS");

    public static class UseThread extends Thread{
        public UseThread(String name) {
            super(name);
        }

        @Override
        public void run(){
            String name = Thread.currentThread().getName();
            while (!isInterrupted()){
                try {
                    System.out.println("UseThread: "+formater.format(new Date()));
                    Thread.sleep(3000);
                }catch (Exception e){
                    System.out.println(name+" catch interrupted flag is " +isInterrupted()
                    + "at "+(formater.format(new Date())));
//                    interrupt();
                    e.printStackTrace();
                }
                System.out.println(name);
            }
            System.out.println(name + " isInterrupted flag is "+isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread endThread = new UseThread("HasInterrputEx");
        endThread.start();
        System.out.println("Main : "+formater.format(new Date()));
        Thread.sleep(800);
        endThread.interrupt();
    }
}
