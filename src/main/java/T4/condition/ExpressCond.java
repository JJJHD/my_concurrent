package T4.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExpressCond {

    public final static String CITY = "ShangHai";
    private int km;
    private String site;
    private Lock lock = new ReentrantLock();
    private Condition keCond = lock.newCondition();
    private Condition siteCond = lock.newCondition();

    public ExpressCond(){
    }

    public ExpressCond(int km,String site){
        this.km = km;
        this.site = site;
    }

    public void changeKm(){
        lock.lock();
        try {
            this.km = 101;
            keCond.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void changeSite(){
        lock.lock();
        try {
            this.site = "BeiJing";
            siteCond.signal();
        }finally {
            lock.unlock();
        }
    }

    public void waitKm(){
        lock.lock();
        try {
            while (this.km <= 100){
                try{
                    keCond.await();
                    System.out.println("check km thread["+Thread.currentThread().getId()+"" +
                            "] is be notified.");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }finally {
            lock.unlock();
        }
        System.out.println("the km is "+this.km+", i will change db");
    }

    public void waitSite(){
        lock.lock();
        try {
            while (CITY.equals(this.site)){
                try {
                    siteCond.await();
                    System.out.println("check site thread["+Thread.currentThread().getId()
                    +"] is be notified.");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }finally {
            lock.unlock();
        }
        System.out.println("the site is "+this.site+", i will call user");
    }


}
