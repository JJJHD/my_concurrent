package T7.tranfer.service;

import T7.tranfer.UserAccount;
import util.SleepTools;

import java.util.Random;

public class SafeOperateToo implements ITransfer {
    @Override
    public void transfer(UserAccount from,
                         UserAccount to, int amount) throws InterruptedException {
        Random r = new Random();
        while (true){
            if (from.getLock().tryLock()){
                try {
                    System.out.println(Thread.currentThread().getName()+" get "
                                    +from.getName());
                    if (to.getLock().tryLock()){
                        try {
                            System.out.println(Thread.currentThread().getName()
                                        +" get "+to.getName());
                            from.flyMoney(amount);
                            to.addMoney(amount);
                            break;
                        }finally {
                            to.getLock().unlock();
                        }
                    }
                }finally {
                    from.getLock().unlock();
                }
            }
            SleepTools.ms(r.nextInt(10));
        }
    }
}
