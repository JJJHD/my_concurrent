package T4.template;

public class SendSms extends SendCustom {

    @Override
    public void to() {
        System.out.println("Mark");
    }

    @Override
    public void from() {
        System.out.println("Bill");
    }

    @Override
    public void content() {
        System.out.println("Hello World");
    }

    @Override
    public void send() {
        System.out.println("send sms");
    }

    public static void main(String[] args) {
        SendCustom sendC = new SendSms();
        sendC.sendMessage();
    }
}
