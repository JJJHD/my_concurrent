package T1.dbpool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 50个线程
 * 每个线程操作20次
 */
public class DBPoolTest {

    static DBPool pool = new DBPool(10);
    static  int threadCount = 500;
    static CountDownLatch end = new CountDownLatch(threadCount);

    public static void main(String[] args) throws InterruptedException {
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0;i<threadCount;i++){
            new Thread(new Worker(count,got,notGot),"worker_"+i).start();
        }

        end.await();

        System.out.println("total times ["+threadCount*count+"]");
        System.out.println("success times ["+got+"]");
        System.out.println("failed times ["+notGot+"]");
    }

    static class Worker implements Runnable{
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public Worker(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            while (count > 0){
                try {
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null){
                        try {
                            connection.createStatement();
                            connection.commit();
                        }finally {
                            pool.releaseConn(connection);
                            got.incrementAndGet();
                        }
                    }else {
                       notGot.incrementAndGet();
                        System.out.println(Thread.currentThread().getName()+"time out");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    count--;
                }
            }
            end.countDown();
        }
    }

}
