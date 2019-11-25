package T1;

public class SleepLock {
    private Object lock = new Object();

    public static void main(String[] args) {
        SleepLock sleepLock = new SleepLock();
        Thread threadA = sleepLock.new ThreadSleep();
        threadA.setName("ThreadSleep");
        Thread threadB = sleepLock.new ThreadNotSleep();
        threadB.setName("ThreadNotSleep");
        threadA.start();
        try {
            Thread.sleep(1000);
            System.out.println("Main sleep...");
        }catch (InterruptedException e){
        }
        threadB.start();

    }

    private class ThreadSleep extends Thread{
        @Override
        public void run(){
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + "will take the lock..");
            try{
                synchronized (lock){
                    System.out.println(threadName + "taking the lock");
                    System.out.println(threadName + "finished the lock");
                }
                Thread.sleep(5000);
            }catch (InterruptedException e){

            }
        }
    }

    private class ThreadNotSleep extends Thread{
        @Override
        public void run(){
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName+"will take the lock "+System.currentTimeMillis());
            synchronized (lock){
                System.out.println(threadName+"take the lock time "+System.currentTimeMillis());
                System.out.println(threadName + "finished the lock");
            }
        }
    }


}
