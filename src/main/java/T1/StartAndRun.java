package T1;

import util.SleepTools;

public class StartAndRun {
    public static class ThreadRun extends Thread{

        @Override
        public void run(){
            int i = 10;
            while (i > 0){
                SleepTools.ms(1000);
                System.out.println("I am "+Thread.currentThread().getName()
                +" and now the i = "+i--);
            }
        }
    }

    public static void main(String[] args) {
        ThreadRun threadRun = new ThreadRun();
        threadRun.setName("BeCalled");
        threadRun.run();
        threadRun.start();
    }
}
