package T5.bitwise;

public class Permission {

    public static final int ALLOW_SELECT = 1 << 0;
    public static final int ALLOW_INSERT = 1 << 1;
    public static final int ALLOW_UPDATE = 1 << 2;
    public static final int ALLOW_DELETE = 1 << 3;

    private int flag;

    public void setPer(int per){
        flag = per;
    }

    public void enable(int per){
        flag = flag|per;
    }

    public void disable(int per){
        flag = flag &~ per;
    }

    public boolean isAllow(int per){
        return ((flag & per) == per);
    }

    public boolean isNotAllow(int per){
        return ((flag & per) == 0);
    }

    public static void main(String[] args) {
        int flag = 15;
        System.out.println(Integer.toBinaryString(flag));
        Permission permission = new Permission();
        permission.setPer(flag);
        permission.disable(ALLOW_DELETE|ALLOW_INSERT);
        System.out.println(Integer.toBinaryString(permission.flag));
        System.out.println("slect == "+permission.isAllow(ALLOW_SELECT));
        System.out.println("update == "+permission.isAllow(ALLOW_UPDATE));
        System.out.println("insert == "+permission.isAllow(ALLOW_INSERT));
        System.out.println("delete == "+permission.isAllow(ALLOW_DELETE));
    }
}
