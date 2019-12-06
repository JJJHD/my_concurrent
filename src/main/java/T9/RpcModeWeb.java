package T9;

import T9.assist.Consts;
import T9.assist.CreatePendingDocs;
import T9.assist.SL_QuestionBank;
import T9.service.ProduceDocService;
import T9.vo.SrcDocVo;

import java.util.List;
import java.util.concurrent.*;

public class RpcModeWeb {
    private static ExecutorService docMakeService
            = Executors.newFixedThreadPool(Consts.CPU_COUNT*2);

    private static ExecutorService docUpLoadService
            = Executors.newFixedThreadPool(Consts.CPU_COUNT*2);

    private static CompletionService<String> docCs =
            new ExecutorCompletionService<>(docUpLoadService);

    private static CompletionService<String> docUpLoadCs =
            new ExecutorCompletionService<>(docMakeService);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("题库开始初始化.......");
        SL_QuestionBank.initBank();
        System.out.println("题库初始化完成");

        List<SrcDocVo> docList = CreatePendingDocs.makePendingDoc(2);
        Long startTotal = System.currentTimeMillis();
        for (SrcDocVo doc:docList){
            docCs.submit(new MakeDocTask(doc));
        }
        for (SrcDocVo doc:docList){
            Future<String> future = docCs.take();
            docUpLoadCs.submit(new UploadDocTask(future.get()));
        }
        for (SrcDocVo doc:docList){
            docUpLoadCs.take().get();
        }

        System.out.println("-------------------共耗时: "+
                (System.currentTimeMillis()-startTotal)+"ms");
    }

    private static class MakeDocTask implements Callable<String>{

        private SrcDocVo pendingDocVo;

        public MakeDocTask(SrcDocVo pendingDocVo) {
            super();
            this.pendingDocVo = pendingDocVo;
        }

        @Override
        public String call() throws Exception {
            long start = System.currentTimeMillis();
            String localName = ProduceDocService.makeDocAsyn(pendingDocVo);
            System.out.println("文档"+localName+"生成耗时："
                            +(System.currentTimeMillis()-start)+"ms");
            return localName;
        }
    }

    private static class UploadDocTask implements Callable<String>{

        private String filePath;

        public UploadDocTask(String filePath) {
            super();
            this.filePath = filePath;
        }


        @Override
        public String call() throws Exception {
            long start = System.currentTimeMillis();
            String remoteUrl = ProduceDocService.upLoadDoc(filePath);
            System.out.println("已上传至【"+remoteUrl+"】耗时");
            return remoteUrl;
        }
    }


}
