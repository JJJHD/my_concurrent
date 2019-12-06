package T8.demo;

import T8.PendingJobPool;
import T8.vo.TaskResult;

import java.util.List;
import java.util.Random;

public class AppTest {

    private final static String JOB_NAME = "计算数值";
    private final static int JOB_LENGTH = 1000;

    private static class QueryResult implements Runnable{

        private PendingJobPool pool;

        public QueryResult(PendingJobPool pool) {
            super();
            this.pool = pool;
        }

        @Override
        public void run() {
            int i = 0;
            while (true){
                List<TaskResult<String>> taskDetal = pool.getTaskDetail(JOB_NAME);
                if (!taskDetal.isEmpty()){
                    System.out.println(pool.getTaskProgess(JOB_NAME));
                    System.out.println(taskDetal);
                }
//                break;
//                SleepTools.ms(10);
//                i++;
            }
        }
    }

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        PendingJobPool pool = PendingJobPool.getInstance();
        System.out.println(pool);
        pool.registerJob(JOB_NAME,JOB_LENGTH,myTask,10000*5);
        System.out.println(pool.getJobInfoMap());
        Random r = new Random();
        for (int i=0;i<JOB_LENGTH;i++){
            pool.putTask(JOB_NAME,r.nextInt(1000));
        }
//        Thread t = new Thread(new QueryResult(pool));
//        t.start();
    }
}
