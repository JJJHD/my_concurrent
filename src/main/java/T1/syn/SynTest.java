package T1.syn;

public class SynTest {
    private volatile int age = 100000;

    public void setAge() {
        age = age + 20;
    }

    private static class TestThread extends Thread{
        private SynTest synTest;

        public TestThread(SynTest synTest,String name){
            super(name);
            this.synTest = synTest;
        }

        @Override
        public void run(){
            for (int i =0;i<100000;i++){
                synTest.test();
            }
            System.out.println(Thread.currentThread().getName()+" age = "+synTest.getAge());
        }
    }

    public synchronized void test(){
        age++;
        test2();
    }

    public synchronized void test2(){
        age--;
    }

    public int getAge(){
        return age;
    }

    public static void main(String[] args) {
        SynTest synTest = new SynTest();
        Thread endThread = new TestThread(synTest,"endThread");
        endThread.start();
        for (int i=0;i<100000;i++){
            synTest.test2();
        }
        System.out.println(Thread.currentThread().getName()+" age = "+synTest.getAge());
    }
}
