package T8.demo;

import T8.vo.ITaskProcesser;
import T8.vo.TaskResult;
import T8.vo.TaskResultType;
import util.SleepTools;

import java.util.Random;

public class MyTask implements ITaskProcesser<Integer,Integer> {
    @Override
    public TaskResult<Integer> taskExecute(Integer data) {
        Random r = new Random();
        int flag = r.nextInt(500);
        SleepTools.ms(flag);
        if (flag<=300){
            Integer returnValue = data.intValue()+flag;
            return new TaskResult<Integer>(TaskResultType.Success,returnValue);
        }else if (flag>30&&flag<=400){
            return new TaskResult<Integer>(TaskResultType.Failure,-1,
                    "Failure");
        }else {
            try {
                throw new RuntimeException("异常发生了！！");
            }catch (Exception e){
                return new TaskResult<Integer>(TaskResultType.Exception,
                        -1,e.getMessage());
            }
        }
    }
}
