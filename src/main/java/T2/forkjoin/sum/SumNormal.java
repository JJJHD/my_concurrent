package T2.forkjoin.sum;

public class SumNormal {
    public static void main(String[] args) {
        int count = 0;
        int[] src = MakeArray.makeArray();
        long start = System.currentTimeMillis();
        for (int i=0;i<src.length;i++){
            count = count+src[i];
        }
        System.out.println("the count is "+count
                +" spend time : "+(System.currentTimeMillis() - start)+"ms");
    }
}
