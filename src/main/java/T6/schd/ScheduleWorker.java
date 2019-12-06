package T6.schd;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleWorker implements Runnable {
    public final static int Normal = 0;
    public final static int HasException = -1;
    public final static int ProcessException = 1;

    public static SimpleDateFormat formater = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private int taskType;

    public ScheduleWorker(int taskType) {
        this.taskType = taskType;
    }

    @Override
    public void run() {
        if (taskType==HasException){
            System.out.println(formater.format(new Date())+" Exception made...");
            throw new RuntimeException("HasException Happen");
        }else if (taskType==ProcessException){
            try {
                System.out.println(formater.format(new Date())+" " +
                        "Exception made,but catch");
                throw new RuntimeException("ProcessException Happen");
            }catch (Exception e){
                System.out.println("Exception be catched");
            }
        }else {
            System.out.println(" Normal ... "+formater.format(new Date()));
        }
    }
}
