package T6.schd;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledCase {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor schedule
                = new ScheduledThreadPoolExecutor(1);

        schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.HasException)
        ,1000,3000, TimeUnit.MILLISECONDS);

        schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.Normal)
        ,1000,3000,TimeUnit.MILLISECONDS);
    }
}
