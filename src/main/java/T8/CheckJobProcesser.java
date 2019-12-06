package T8;

import T5.bq.ItemVo;

import java.util.concurrent.DelayQueue;

public class CheckJobProcesser {

    private static DelayQueue<ItemVo<String>> queue =
            new DelayQueue<>();

    private CheckJobProcesser(){}
    private static class ProcesserHolder{
        public static CheckJobProcesser processer = new CheckJobProcesser();
    }
    public static CheckJobProcesser getInstance(){
        return ProcesserHolder.processer;
    }

    private static class FetchJob implements Runnable{

        @Override
        public void run() {
            while (true){
                try {
                    ItemVo<String> item = queue.take();
                    String jobName = (String) item.getDate();
                    PendingJobPool.getJobInfoMap().remove(jobName);
                    System.out.println(jobName+"is out of date,remove from map!");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void putJob(String jobName,long expireTime){
        ItemVo<String> item = new ItemVo<>(expireTime,jobName);
        queue.offer(item);
        System.out.println("Job["+jobName+"] 已经放入了过期检查缓存，过期时长："+expireTime);
    }

    static {
        Thread thread = new Thread(new FetchJob());
        thread.setDaemon(true);
        thread.start();
        System.out.println("开启任务过期检查守护线程...........");
    }
}
