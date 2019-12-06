package T7.dcl;

public class InstanceLazy {
    private Integer value;
    private Integer val;

    public InstanceLazy(Integer value) {
        super();
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static class ValHolder{
        public static Integer vHolder = new Integer(1000000);
    }

    public Integer getVal() {
        return ValHolder.vHolder;
    }
}
