package T9.service.question;

import T9.assist.SL_QuestionBank;

public class SingleQstService {
    public static String makeQuestion(Integer questionId){
        return BaseQuestionProcessor.makeQuestion(questionId,
                SL_QuestionBank.getQuestion(questionId).getDetail());
    }
}
