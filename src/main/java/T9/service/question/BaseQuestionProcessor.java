package T9.service.question;

import T9.assist.SL_Busi;

import java.util.Random;

public class BaseQuestionProcessor {
    public static String makeQuestion(Integer questionId,String questionSrc){
        Random r = new Random();
        SL_Busi.buisness(450+r.nextInt(100));
        return "CompleteQuestion[id="+questionId+" content=:"+questionSrc+"]";
    }
}
