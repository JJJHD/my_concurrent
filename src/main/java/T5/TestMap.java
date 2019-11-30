package T5;

public class TestMap {
    public static void main(String[] args) {
        int initialCapacity = 16;
        float loadFactor = 0.75f;
        int concurrencyLevel = 16;

        if (!(loadFactor > 0)||initialCapacity < 0||concurrencyLevel <= 0){
            throw new IllegalArgumentException();
        }
        if (concurrencyLevel > 65536){
            concurrencyLevel = 65536;
        }
        int sshift = 0;
        int ssize = 1;
        while (ssize < concurrencyLevel){
            ++sshift;
            sshift <<= 1;
        }
        int segmentShift = 32 - sshift;
        int segmentMark = ssize - 1;
        if (initialCapacity > 1073741824){
            initialCapacity = 1073741824;
        }
        int c = initialCapacity/ssize;
        if (c * ssize < initialCapacity){
            ++c;
        }
        int cap = 2;
        while (cap < c){
            cap <<= 1;
        }
        System.out.println(cap * loadFactor);
    }
}
