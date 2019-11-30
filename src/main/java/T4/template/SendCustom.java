package T4.template;

import java.util.Date;

public abstract class SendCustom {
    public abstract void to();
    public abstract void from();
    public abstract void content();
    public void date(){
        System.out.println(new Date());
    }
    public abstract void send();

    public void sendMessage(){
        to();
        from();
        content();
        date();
        send();
    }
}
