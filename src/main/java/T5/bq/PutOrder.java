package T5.bq;

import java.util.concurrent.DelayQueue;

public class PutOrder implements Runnable {

    private DelayQueue<ItemVo<Order>> queue;

    public PutOrder(DelayQueue<ItemVo<Order>> queue) {
        super();
        this.queue = queue;
    }

    @Override
    public void run() {
        Order ordeTb = new Order("Tb12345",366);
        ItemVo<Order> itemTb = new ItemVo<>(5000,ordeTb);
        queue.offer(itemTb);
        System.out.println("订单5秒后到期： "+ordeTb.getOrderNo());

        Order ordeJd = new Order("Jd54321",366);
        ItemVo<Order> itemJd = new ItemVo<>(8000,ordeJd);
        queue.offer(itemJd);
        System.out.println("订单8秒后到期： "+ordeJd.getOrderNo());
    }
}
