package T6.comps;

import java.util.Random;
import java.util.concurrent.Callable;

public class WorkTask implements Callable {
    private String name;

    public WorkTask(String name) {
        this.name = name;
    }

    @Override
    public Object call() throws Exception {
        int sleepTime = new Random().nextInt(1000);
        try {
            Thread.sleep(sleepTime);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sleepTime;
    }
}
