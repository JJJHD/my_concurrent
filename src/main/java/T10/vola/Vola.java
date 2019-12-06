package T10.vola;

public class Vola {
    volatile long i = 0L;
    public long getI(){
        return i;
    }

    public void inc(){
        i++;
    }

}
