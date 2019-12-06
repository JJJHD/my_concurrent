package T8;

import T8.vo.ITaskProcesser;
import T8.vo.JobInfo;
import T8.vo.TaskResult;
import T8.vo.TaskResultType;

import java.util.List;
import java.util.concurrent.*;

public class PendingJobPool {
    private static final int THREAD_COUNTS =
            Runtime.getRuntime().availableProcessors();

    private static BlockingQueue<Runnable> taskQueue =
            new ArrayBlockingQueue<>(5000);

    private static ExecutorService taskExecutor =
            new ThreadPoolExecutor(THREAD_COUNTS,THREAD_COUNTS,60,
                    TimeUnit.SECONDS,taskQueue);

    private static ConcurrentHashMap<String, JobInfo<?>> jobInfoMap
            = new ConcurrentHashMap<>();

    private static CheckJobProcesser checkJob =
            CheckJobProcesser.getInstance();

    public static ConcurrentHashMap<String, JobInfo<?>> getJobInfoMap() {
        return jobInfoMap;
    }

    private PendingJobPool(){}

    private static class JobPollHodler{
        public static PendingJobPool pool = new PendingJobPool();
    }

    public static PendingJobPool getInstance(){
        return JobPollHodler.pool;
    }

    private static class PendingTask<T,R> implements Runnable{

        private JobInfo<R> jobInfo;
        private T proccessData;

        public PendingTask(JobInfo<R> jobInfo, T proccessData) {
            super();
            this.jobInfo = jobInfo;
            this.proccessData = proccessData;
        }

        @Override
        public void run() {
            R r = null;
            ITaskProcesser<T,R> taskProcesser =
                    (ITaskProcesser<T, R>)jobInfo.getTaskProcesser();
            TaskResult<R> result = null;
            try {
                result = taskProcesser.taskExecute(proccessData);
                if (result == null){
                    result = new TaskResult<R>(TaskResultType.Exception,r,
                            "result is null");
                }
                if (result.getTaskResultType() == null){
                    if (result.getReason() == null){
                        result = new TaskResult<R>(TaskResultType.Exception,r,
                                "reason is null");
                    }else {
                        result = new TaskResult<R>(TaskResultType.Exception,r,
                                "result is null,but reason: "
                                        +result.getReason());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                result = new TaskResult<R>(TaskResultType.Exception,r,e.getMessage());
            }finally {
                jobInfo.addTaskResult(result,checkJob);
            }
        }
    }

    private <R> JobInfo<R> getJob(String  jobName){
        JobInfo<R> jobInfo = (JobInfo<R>) jobInfoMap.get(jobName);
        if (jobInfo == null){
            throw new RuntimeException(jobName+"是个非法任务");
        }
        return jobInfo;
    }

    public <T,R> void putTask(String jobName,T t){
        JobInfo<R> jobInfo = getJob(jobName);
        PendingTask<T,R> task = new PendingTask<T,R>(jobInfo,t);
        taskExecutor.execute(task);
    }

    public <R> void registerJob(String jobName,int jobLength,
                                ITaskProcesser<?,?> taskProcesser,long expireTime){
        JobInfo<R> jobInfo = new JobInfo<>(jobName,jobLength,taskProcesser,expireTime);
        if (jobInfoMap.putIfAbsent(jobName,jobInfo) != null){
            throw new RuntimeException(jobName+"已经注册了！");
        }

    }

    public <R> List<TaskResult<R>> getTaskDetail(String jobName){
        JobInfo<R> jobInfo = getJob(jobName);
        return jobInfo.getTaskDetail();
    }

    public <R> String getTaskProgess(String jobName){
        JobInfo<R> jobInfo = getJob(jobName);
        return jobInfo.getTotalProcess();
    }

}
