package T1;

/**
 * 其他线程执行完，守护线程也跟着立即结束，finally里也不会执行
 */
public class DaemonThread {

    private static class UserThread extends Thread{

        @Override
        public void run(){
            try {
                while (!isInterrupted()){
                    System.out.println("1111");
                }
                System.out.println("==11==");
            }catch (Exception e){
            }finally {
                System.out.println("finally==11");
            }
        }
    }

    private static class UserThread2 extends Thread{

        @Override
        public void run(){
            try {
                while (!isInterrupted()){
                    System.out.println("2222");
                }
                System.exit(-1);
                System.out.println("2 continue");
            }catch (Exception e){

            }finally {
                System.out.println("finally=22=");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        UserThread us = new UserThread();
        UserThread2 us2 = new UserThread2();
        us.setDaemon(true);
        System.out.println("start");
        us.start();
        us2.start();
        Thread.sleep(1);
        us2.interrupt();
        System.out.println("end");
    }
}
