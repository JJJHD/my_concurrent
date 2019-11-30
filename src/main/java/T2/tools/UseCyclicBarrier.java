package T2.tools;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

public class UseCyclicBarrier {
    private static CyclicBarrier barrier = new CyclicBarrier(5,new CollectThread());
    private static ConcurrentHashMap<String,Long> resultMap =
            new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i=0;i<=4;i++){
            Thread thread = new Thread(new SubThread());
            thread.start();
        }
    }

    private static class CollectThread implements Runnable{

        @Override
        public void run() {
            StringBuilder result = new StringBuilder();
            for (Map.Entry<String,Long> workResult:resultMap.entrySet()){
                result.append("["+workResult.getValue()+"]");
            }
            System.out.println(" the result = "+result);
            System.out.println("do other business...");
        }
    }

    private static class SubThread implements Runnable{

        @Override
        public void run() {
            long id = Thread.currentThread().getId();
            resultMap.put(Thread.currentThread().getId()+"",id);
            Random r = new Random();
            try {
                if (r.nextBoolean()){
                    Thread.sleep(200+id);
                    System.out.println("Thread["+id+"] do something...");
                }
                System.out.println(id + "...is await");
                barrier.await();
                Thread.sleep(1000+id);
                System.out.println("Thread["+id+"] do business end");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
