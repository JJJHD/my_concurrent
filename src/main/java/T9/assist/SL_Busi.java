package T9.assist;

public class SL_Busi {
    public static void buisness(int sleepTime){
        try {
            Thread.sleep(sleepTime);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
