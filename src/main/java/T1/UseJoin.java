package T1;

import util.SleepTools;

public class UseJoin {
    static class JumpQueue implements Runnable{

        private Thread thread;

        public JumpQueue(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                System.out.println(thread.getName() + " will be join before "
                +Thread.currentThread().getName());
                thread.join();
            }catch (Exception e){
            }
            System.out.println(Thread.currentThread().getName() + " terminted ");
        }
    }

    public static void main(String[] args) {
        Thread previous = Thread.currentThread();
        for (int i = 0;i < 100;i++){
            Thread thread = new Thread(new JumpQueue(previous),String.valueOf(i));
            System.out.println(previous.getName()+" jump a queue the thread: "
                +thread.getName());
            thread.start();
            previous = thread;
        }
        SleepTools.second(2);
        System.out.println(Thread.currentThread().getName() + " terminate ");
    }
}
