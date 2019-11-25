package T1.syn;

import util.SleepTools;

public class SynClzAndInst {
    /**
     * 类锁
     */
    private static synchronized void synClass(){
        SleepTools.second(1);
        System.out.println("synClass going ...");
        SleepTools.second(1);
        System.out.println("synClass end");
    }

    private synchronized void instance(){
        SleepTools.second(3);
        System.out.println("synInstance going ..."+this.toString());
        SleepTools.second(3);
        System.out.println("synInstance ended "+this.toString());
    }

    private synchronized void instance2(){
        SleepTools.second(3);
        System.out.println("synInstance going ..."+this.toString());
        SleepTools.second(3);
        System.out.println("synInstance ended "+this.toString());
    }

    private static class SynClass extends Thread{
        @Override
        public void run(){
            System.out.println("TestClass is running ... ");
            synClass();
        }
    }

    private static class InstanceSyn implements Runnable{
        private SynClzAndInst synClzAndInst;

        public InstanceSyn(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }

        @Override
        public void run() {
            System.out.println("TestInstance running ..."+synClzAndInst);
            synClzAndInst.instance();
        }
    }

    private static class Instance2Syn implements Runnable{
        private SynClzAndInst synClzAndInst;

        public Instance2Syn(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }

        @Override
        public void run() {
            System.out.println("TestInstance2 running ..."+synClzAndInst);
            synClzAndInst.instance2();
        }
    }

    public static void main(String[] args) {
        SynClzAndInst synClzAndInst = new SynClzAndInst();
        Thread t1 = new Thread(new InstanceSyn(synClzAndInst));
        t1.start();
        SynClass synClass = new SynClass();
        synClass.start();
        SleepTools.second(1);
    }
}
