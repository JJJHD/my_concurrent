package T7.safeclass;

public class ImmutableFinal {
    private final int a;
    private final int b;

    public ImmutableFinal(int a, int b) {
        super();
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }
}
