package T2.tools.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class DBPoolSemaphore {
    private final static int POOL_SIZE = 10;
    private final Semaphore useful,useless;

    public DBPoolSemaphore() {
        this.useful = new Semaphore(POOL_SIZE);
        this.useless = new Semaphore(0);
    }

    private static LinkedList<Connection> pool = new LinkedList<>();
    static {
        for (int i=0;i<POOL_SIZE;i++){
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }

    public void returnConnect(Connection connection) throws InterruptedException {
        if (connection != null){
            System.out.println("当前有"+useful.getQueueLength()+"个线程线程等待数据库连接 " +
                    "可用连接数："+useful.availablePermits());
            useless.acquire();
            synchronized (pool){
                pool.addLast(connection);
            }
            useful.release();
        }
    }

    public Connection takeConnect() throws InterruptedException {
        useful.acquire();
        Connection connection;
        synchronized (pool){
            connection = pool.removeFirst();
        }
        useless.release();
        return connection;
    }
}
