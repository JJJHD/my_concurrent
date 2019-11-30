package T5.bq;

import java.util.concurrent.DelayQueue;

public class FetchOrder implements Runnable {

    private DelayQueue<ItemVo<Order>> queue;

    public FetchOrder(DelayQueue<ItemVo<Order>> queue) {
        super();
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            try {
                ItemVo<Order> itemVo = queue.take();
                Order order = (Order)itemVo.getDate();
                System.out.println("get from quene: "+order.getOrderNo());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
