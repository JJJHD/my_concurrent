package T10.vola;

public class VolaLikeSyn {

    long i = 0L;
    public synchronized long getI(){
        return i;
    }

    public synchronized void setI(long i){
        this.i = i;
    }

    public void inc(){
        long temp = getI();
        temp = temp + 1L;
        setI(temp);
    }

}
