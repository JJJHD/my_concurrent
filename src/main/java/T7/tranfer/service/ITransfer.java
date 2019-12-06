package T7.tranfer.service;

import T7.tranfer.UserAccount;

public interface ITransfer {
    void transfer(UserAccount from,UserAccount to,int amount) throws InterruptedException;
}
