package T2.tools.semaphore;

import util.SleepTools;

import java.sql.Connection;
import java.util.Random;

public class AppTest {
    private static DBPoolSemaphore dbPool = new DBPoolSemaphore();

    private static class BusiThread extends Thread{

        @Override
        public void run(){
            Random r = new Random();
            long start = System.currentTimeMillis();
            try {
                Connection connection = dbPool.takeConnect();
                System.out.println("Thread["+Thread.currentThread().getId()+"] " +
                        "获取数据库连接共耗时 【"+(System.currentTimeMillis() - start)+"ms");
                SleepTools.ms(100+r.nextInt(100));
                System.out.println("查询数据完成，归还连接");
                dbPool.returnConnect(connection);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }

    }

    public static void main(String[] args) {
        for (int i=0;i<50;i++){
            Thread thread = new BusiThread();
            thread.start();
        }
    }
}
