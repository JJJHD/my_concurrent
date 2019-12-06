package T9.assist;

import T9.vo.QuestionInDBVo;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 题库数据
 */
public class SL_QuestionBank {

    private static ConcurrentHashMap<Integer, QuestionInDBVo> questionBankMap
            = new ConcurrentHashMap<Integer, QuestionInDBVo>();

    private static ScheduledExecutorService updateQuestionBank
            = new ScheduledThreadPoolExecutor(1);

    public static void initBank(){
        for (int i=0;i<Consts.SIZE_OF_QUESTION_BANK;i++){
            String questionContent = makeQuestionDetail(Consts.QUESTION_LENGTH);
            questionBankMap.put(i,new QuestionInDBVo(i,
                    questionContent,EncryptUtils.EncryptBySHA1(questionContent)));
        }
        updateQuestionTimer();
    }

    private static String makeQuestionDetail(int lenth){
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<lenth;i++){
            int number = r.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static QuestionInDBVo getQuestion(int i){
        SL_Busi.buisness(20);
        return questionBankMap.get(i);
    }

    public static String getSha(int i){
        SL_Busi.buisness(10);
        return questionBankMap.get(i).getSha();
    }

    private static class UpdateBank implements Runnable{

        @Override
        public void run() {
            Random r = new Random();
            int questionId = r.nextInt(Consts.SIZE_OF_QUESTION_BANK);
            String questionContent = makeQuestionDetail(Consts.QUESTION_LENGTH);
            questionBankMap.put(questionId,new QuestionInDBVo(questionId,
                    questionContent,EncryptUtils.EncryptBySHA1(questionContent)));
            System.out.println("题目【"+questionId+"]被更新");
        }
    }

    private static void updateQuestionTimer(){
        System.out.println("开始定时更新题库....");
        updateQuestionBank.scheduleAtFixedRate(new UpdateBank(),
                15,5, TimeUnit.SECONDS);
    }


}
