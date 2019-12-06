package T9.service.question;

import T9.assist.Consts;
import T9.assist.SL_QuestionBank;
import T9.vo.QuestionInCacheVo;
import T9.vo.QuestionInDBVo;
import T9.vo.TaskResultVo;

import java.util.concurrent.*;

public class ParallerQstService {

    //已处理题目的缓存
    private static ConcurrentHashMap<Integer, QuestionInCacheVo> questionCache
            = new ConcurrentHashMap<>();

    //正在处理题目的缓存
    private static ConcurrentHashMap<Integer, Future<QuestionInCacheVo>>
            processingQuestionCache = new ConcurrentHashMap<>();

    private static ExecutorService makeQuestionService =
            Executors.newFixedThreadPool(Consts.CPU_COUNT*2);

    public static TaskResultVo makeQuestion(Integer questionId){
        QuestionInCacheVo qstCacheVo = questionCache.get(questionId);
        if (qstCacheVo == null){
            System.out.println("题目【"+questionId+"】在缓存中不存在，" +
                    " 准备启动任务.");
            return new TaskResultVo(getQstFuture(questionId));
        }else {
            String questionSha = SL_QuestionBank.getSha(questionId);
            if (questionSha.equals(qstCacheVo.getQuestionSha())){
                System.out.println("题目【"+questionId+"在缓存中已存在，且未变化.");
                return new TaskResultVo(qstCacheVo.getQuestionDetail());
            }else {
                System.out.println("题目【"+questionId+"】在缓存中已存在，但是发生" +
                        "了变化，更新缓存");
                return new TaskResultVo(getQstFuture(questionId));
            }
        }
    }

    private static Future<QuestionInCacheVo> getQstFuture(Integer questionId){
        Future<QuestionInCacheVo> questionFuture =
                processingQuestionCache.get(questionId);
        try {
            if (questionFuture == null){
                QuestionInDBVo qstDbvo = SL_QuestionBank.getQuestion(questionId);
                QuestionTask questionTask = new QuestionTask(qstDbvo,questionId);
                FutureTask<QuestionInCacheVo> ft =
                        new FutureTask<>(questionTask);
                questionFuture = processingQuestionCache.putIfAbsent(questionId,ft);
                if (questionFuture == null){
                    questionFuture = ft;
                    makeQuestionService.execute(ft);
                    System.out.println("成功启动了题目【"+questionId+"】的计算任务，" +
                            "请等待完成>>>>>>>>>>");
                }else {
                    System.out.println("<<<<<<<<<<<<<<<<有其他线程刚刚启动了题目【"
                            +questionId+"】的计算任务，本任务无需开启");
                }
            }else {
                System.out.println("题目【"+questionId+"】已存在计算任务，无需重新生成");
            }
        }catch (Exception e){
            processingQuestionCache.remove(questionId);
            e.printStackTrace();
            throw e;
        }
        return questionFuture;
    }

    private static class QuestionTask implements Callable<QuestionInCacheVo>{

        private QuestionInDBVo qstDbvo;
        private Integer questionId;

        public QuestionTask(QuestionInDBVo qstDbvo, Integer questionId) {
            super();
            this.qstDbvo = qstDbvo;
            this.questionId = questionId;
        }

        @Override
        public QuestionInCacheVo call() throws Exception {
            try {
                String qstDetail = BaseQuestionProcessor.makeQuestion(questionId,
                        SL_QuestionBank.getQuestion(questionId).getDetail());

                String questionSha = qstDbvo.getSha();
                QuestionInCacheVo qstCache = new QuestionInCacheVo(qstDetail,questionSha);
                questionCache.put(questionId,qstCache);
                return qstCache;
            }finally {
                processingQuestionCache.remove(questionId);
            }

        }
    }


}
