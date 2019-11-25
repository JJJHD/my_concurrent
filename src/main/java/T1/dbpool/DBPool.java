package T1.dbpool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 数据库连接池
 */
public class DBPool {

    private static LinkedList<Connection> pool = new LinkedList<Connection>();

    public DBPool(int initalSize){
        if (initalSize <= 0){
            throw new RuntimeException("initalSize exception ");
        }
        for (int i = 0;i < initalSize;i++){
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }


    //取数据库连接
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool){
            if (mills <= 0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else {
                long overtime = System.currentTimeMillis() + mills;
                long remain = mills;
                while (pool.isEmpty() && remain > 0){
                    pool.wait(remain);
                    remain = System.currentTimeMillis() - overtime;
                }
                Connection result = null;
                if (!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }

    //放数据库连接
    public void releaseConn(Connection connection){
        if (connection != null){
            synchronized (pool){
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }
}
