package T3.answer;

import java.util.concurrent.atomic.AtomicInteger;

public class HalfAtomicInt {
    private AtomicInteger ac = new AtomicInteger(0);

    public void increament(){
        for(;;){
            int i = ac.get();
            boolean success = ac.compareAndSet(i,++i);
            if (success){
                break;
            }
        }
    }

    public int getCount(){
        return ac.get();
    }

}
