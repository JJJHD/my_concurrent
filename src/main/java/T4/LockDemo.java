package T4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    private Lock lock = new ReentrantLock();
    private int count;

    public void increament(){
        lock.lock();
        try{
            count++;
        }finally {
            lock.unlock();
        }
    }

    public synchronized void incr2(){
        count++;
        incr2();
    }

    public synchronized void test3(){
        incr2();
    }

}
