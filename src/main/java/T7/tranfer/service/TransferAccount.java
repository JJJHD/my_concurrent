package T7.tranfer.service;

import T7.tranfer.UserAccount;

public class TransferAccount implements ITransfer {
    @Override
    public void transfer(UserAccount from,
                         UserAccount to, int amount) throws InterruptedException {
        synchronized (from){
            System.out.println(Thread.currentThread().getName()+" get "
                            +from.getName());
            Thread.sleep(100);
            synchronized (to){
                System.out.println(Thread.currentThread().getName()+" get "
                            +to.getName());
                from.flyMoney(amount);
                to.addMoney(amount);
            }
        }
    }
}
