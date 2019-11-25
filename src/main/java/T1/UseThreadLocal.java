package T1;

public class UseThreadLocal {
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue(){
            return 1;
        }
    };

    public void startThreadArray(){
        Thread[] runs = new Thread[3];
        for (int i = 0;i < runs.length;i++){
            runs[i] = new Thread(new TestThread(i));
        }
        for (int i = 0;i < runs.length;i++){
            runs[i].start();
        }
    }

    public static class TestThread implements Runnable{
        int id;

        public TestThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" :start ");
            Integer s = threadLocal.get();
            s = s + id;
            threadLocal.set(s);
            System.out.println(Thread.currentThread().getName()+" : "+threadLocal.get());
        }
    }

    public static void main(String[] args) {
        UseThreadLocal test = new UseThreadLocal();
        test.startThreadArray();
    }
}
