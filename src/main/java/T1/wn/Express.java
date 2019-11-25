package T1.wn;

public class Express {
    public final static String CITTY = "ShangHai";
    private int km;
    private String site;

    public Express() {
    }

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    public synchronized void changeKm(){
        this.km = 101;
        notifyAll();
    }

    public synchronized void changeSite(){
        this.site = "BeiJing";
        notifyAll();
    }

    public synchronized void waitKm(){
        while (this.km <= 100){
            try {
                wait();
                System.out.println("check km thread["+Thread.currentThread().getId()
                +"] is notifed");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("the km is "+this.km+" ,i will change db.");
    }

    public synchronized void waitSite(){
        while (CITTY.equals(this.site)){
            try {
                wait();
                System.out.println("check site thread[" + Thread.currentThread().getId()
                +"] is be notifed ");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("the site is "+this.site+" ,i will call user.");
    }
}
