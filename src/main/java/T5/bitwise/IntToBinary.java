package T5.bitwise;

public class IntToBinary {
    public static void main(String[] args) {
        int data = 4;
//        System.out.println("the 4 is "+Integer.toBinaryString(data));

        System.out.println("the 4 is "+Integer.toBinaryString(4));
        System.out.println("the 6 is "+Integer.toBinaryString(6));
        System.out.println("the 4&6 is "+Integer.toBinaryString(4&6));
        System.out.println("----------------------------------------------");
        System.out.println("the 4 is "+Integer.toBinaryString(4));
        System.out.println("the 6 is "+Integer.toBinaryString(6));
        System.out.println("the 4|6 is "+Integer.toBinaryString(4|6));
        System.out.println("----------------------------------------------");
        System.out.println("the 4 is "+Integer.toBinaryString(4));
        System.out.println("the ~4 is "+Integer.toBinaryString(~4));
        System.out.println("----------------------------------------------");
        System.out.println("the 4 is "+Integer.toBinaryString(4));
        System.out.println("the 6 is "+Integer.toBinaryString(6));
        System.out.println("the 4^6 is "+Integer.toBinaryString(4^6));
        System.out.println("----------------------------------------------");
        System.out.println("the 345 % 16 is "+(345%16));
        System.out.println("the 345 & (16 - 1) is "+(345&15));
        System.out.println("----------------------------------------------");
    }
}
