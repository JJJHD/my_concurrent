package T8.demo;

import T8.PendingJobPool;
import T8.vo.TaskResult;

import java.util.List;

public class QueryTest implements Runnable{

        private final static String JOB_NAME = "计算数值";
        private final static int JOB_LENGTH = 10;


            private PendingJobPool pool;

            public QueryTest(PendingJobPool pool) {
                super();
                this.pool = pool;
            }

            @Override
            public void run() {
                int i = 0;
                while (true){
                    try {
//                        System.out.println("1");
                        List<TaskResult<String>> taskDetal = pool.getTaskDetail(JOB_NAME);
                        if (!taskDetal.isEmpty()){
                            System.out.println(pool.getTaskProgess(JOB_NAME));
                            System.out.println(taskDetal);
                        }
                    }catch (Exception e){
                    }

//                break;
//                SleepTools.ms(10);
//                i++;
                }
            }

        public static void main(String[] args) {
//        MyTask myTask = new MyTask();
        PendingJobPool pool = PendingJobPool.getInstance();
            System.out.println(pool);
            System.out.println(pool.getJobInfoMap());
//            Thread t = new Thread(new QueryTest(pool));
//        t.start();
    }
}
