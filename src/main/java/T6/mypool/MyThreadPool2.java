package T6.mypool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyThreadPool2 {
    private static int WORK_NUM = 5;
    private static int TASK_COUNT = 100;
    private WorkThread[] workThreads;

    private final BlockingQueue<Runnable> taskQueue;
    private final int worker_num;

    public MyThreadPool2(){
        this(WORK_NUM,TASK_COUNT);
    }

    public MyThreadPool2(int worker_num,int taskCount){
        if (worker_num<=0){
            worker_num = WORK_NUM;
        }
        if (taskCount<=0){
            taskCount = TASK_COUNT;
        }
        this.worker_num = worker_num;
        taskQueue = new ArrayBlockingQueue<>(taskCount);
        workThreads = new WorkThread[worker_num];
        for (int i=0;i<worker_num;i++){
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }
        Runtime.getRuntime().availableProcessors();
    }

    public void execute(Runnable task){
        try {
            taskQueue.put(task);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void destroy(){
        System.out.println("ready close pool ...");
        for (int i=0;i<worker_num;i++){
            workThreads[i].stopWorker();
            workThreads[i] = null;
        }
        taskQueue.clear();
    }

    private class WorkThread extends Thread{

        @Override
        public void run(){
            Runnable r = null;
            try {
                while (!isInterrupted()){
                    r = taskQueue.take();
                    if(r != null){
                        System.out.println(getId()+" ready exec : "+r);
                        r.run();
                    }
                    r = null;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void stopWorker(){
            interrupt();
        }
    }
}
