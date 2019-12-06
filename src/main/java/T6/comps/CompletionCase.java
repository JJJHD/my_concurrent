package T6.comps;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletionCase {
    private final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final int TOTAL_TASK = Runtime.getRuntime().availableProcessors();

    public void testByQueue() throws Exception{
        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

        BlockingQueue<Future<Integer>> queue = new LinkedBlockingDeque<>();
        for (int i=0;i<TOTAL_TASK;i++){
            Future<Integer> future = pool.submit(new WorkTask("ExecTask"+i));
            queue.add(future);
        }

        for (int i=0;i<TOTAL_TASK;i++){
            int sleptTime = queue.take().get();
            System.out.println("slept "+sleptTime+" ms ...");
            count.addAndGet(sleptTime);
        }
        pool.shutdown();
        System.out.println("----------tasks sleep time "+count.get()+" ms " +
                ", and spend time "+(System.currentTimeMillis()-start)+" ms");
    }

    public void testByCompletion() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        CompletionService<Integer> cService = new ExecutorCompletionService<>(pool);

        for (int i=0;i<TOTAL_TASK;i++){
            cService.submit(new WorkTask("ExecTask"+i));
        }

        for (int i=0;i<TOTAL_TASK;i++){
            int sleptTime = cService.take().get();
            System.out.println("slept "+sleptTime+" ms ...");
            count.addAndGet(sleptTime);
        }
        pool.shutdown();
        System.out.println("-----------------------tasks sleep time "+count.get()+"ms" +
                ", and spend time "+(System.currentTimeMillis()-start)+"ms ");
    }

    public static void main(String[] args) throws Exception {
        CompletionCase t = new CompletionCase();
        t.testByQueue();
        t.testByCompletion();
    }

}
