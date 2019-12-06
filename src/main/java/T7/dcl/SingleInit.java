package T7.dcl;

public class SingleInit {
    private SingleInit(){}
    private static class InstanceHolder{
        public static SingleInit instance = new SingleInit();
    }

    public static SingleInit getInstance(){
        return InstanceHolder.instance;
    }
}
