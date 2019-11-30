package T4.rw;

import util.SleepTools;

import java.util.Random;

public class BusiApp {
    static final int readWriteRatio = 10;
    static final int minthreadCount = 3;

    private static class GetThread implements Runnable{

        private GoodsService goodsService;

        public GetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for (int i=0;i<100;i++){
                goodsService.getNum();
            }
            System.out.println(Thread.currentThread().getName()+" 读取商品数据耗时： "
                            +(System.currentTimeMillis() - start)+"ms");
        }
    }

    private static class SetThread implements Runnable{

        private GoodsService goodsService;

        public SetThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            Random r = new Random();
            for (int i=0;i<10;i++){
                SleepTools.ms(50);
                goodsService.setNum(r.nextInt(10));
            }
            System.out.println(Thread.currentThread().getName()+" 写商品数据耗时： "
                            +(System.currentTimeMillis() - start)+"ms");
        }
    }

    public static void main(String[] args) {
        GoodsInfo goodsInfo = new GoodsInfo("Cup",100000,
                100000);
        GoodsService goodsService = new UseRwLock(goodsInfo);
        for (int i=0;i<minthreadCount;i++){
            Thread set = new Thread(new SetThread(goodsService));
            for (int j=0;j<readWriteRatio;j++){
                Thread get = new Thread(new GetThread(goodsService));
                get.start();
            }
            SleepTools.ms(100);
            set.start();
        }
    }
}
