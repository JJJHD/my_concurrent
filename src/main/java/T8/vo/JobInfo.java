package T8.vo;

import T8.CheckJobProcesser;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class JobInfo<R> {
    private final String jobName;
    private final int jobLength;
    private final ITaskProcesser<?,?> taskProcesser;
    private final AtomicInteger successCount;
    private final AtomicInteger taskProcesserCount;
    private final LinkedBlockingDeque<TaskResult<R>> taskDetailQueue;
    private final long expireTime;

    public JobInfo(String jobName, int jobLength, ITaskProcesser<?, ?> taskProcesser,
                    long expireTime) {
        this.jobName = jobName;
        this.jobLength = jobLength;
        this.taskProcesser = taskProcesser;
        this.successCount = new AtomicInteger(0);
        this.taskProcesserCount = new AtomicInteger(0);
        this.taskDetailQueue = new LinkedBlockingDeque<>(jobLength);
        this.expireTime = expireTime;
    }

    public ITaskProcesser<?, ?> getTaskProcesser() {
        return taskProcesser;
    }

    public AtomicInteger getSuccessCount() {
        return successCount;
    }

    public AtomicInteger getTaskProcesserCount() {
        return taskProcesserCount;
    }

    public int getFailCount(){
        return taskProcesserCount.get() - successCount.get();
    }

    public String getTotalProcess(){
        return "Success["+successCount.get()+"]/Current["
                +taskProcesserCount.get()+"] Total["+jobLength+"]";
    }

    public List<TaskResult<R>> getTaskDetail(){
        List<TaskResult<R>> taskList = new LinkedList<>();
        TaskResult<R> taskResult;
        while ((taskResult = taskDetailQueue.pollFirst()) != null){
            taskList.add(taskResult);
        }
        return taskList;
    }

    public void addTaskResult(TaskResult<R> result, CheckJobProcesser checkJob){
        if (TaskResultType.Success.equals(result.getTaskResultType())){
            successCount.incrementAndGet();
        }
        taskDetailQueue.addLast(result);
        taskProcesserCount.incrementAndGet();
        if (taskProcesserCount.get() == jobLength){
            checkJob.putJob(jobName,expireTime);
        }
    }
}
