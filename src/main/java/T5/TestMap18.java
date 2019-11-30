package T5;

public class TestMap18 {
    public static void main(String[] args) {
        int c = 13;
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 16;
        n = n + 1;
        System.out.println("n="+n);
    }
}
