package T9.service;

import T9.assist.SL_Busi;
import T9.service.question.ParallerQstService;
import T9.service.question.SingleQstService;
import T9.vo.SrcDocVo;
import T9.vo.TaskResultVo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class ProduceDocService {
    public static String upLoadDoc(String docFileName){
        Random r = new Random();
        SL_Busi.buisness(9000+r.nextInt(400));
        return "http://www.xxx.com/file/upload/"+docFileName;
    }

    public static String makeDoc(SrcDocVo pendingDocVo){
        System.out.println("开始处理文档："+pendingDocVo.getDocName());
        StringBuffer sb = new StringBuffer();
        for (Integer quesitonId : pendingDocVo.getQuestionList()){
            sb.append(SingleQstService.makeQuestion(quesitonId));
        }
        return "complete_"+System.currentTimeMillis()+"_"
                + pendingDocVo.getDocName()+".pdf";
    }

    public static String makeDocAsyn(SrcDocVo pendingDocVo) throws ExecutionException, InterruptedException {
        System.out.println("开始处理文档："+pendingDocVo.getDocName());
        Map<Integer, TaskResultVo> qstResultMap = new HashMap<>();
        for (Integer questionId : pendingDocVo.getQuestionList()){
            qstResultMap.put(questionId, ParallerQstService.makeQuestion(questionId));
        }

        StringBuffer sb = new StringBuffer();
        for (Integer questionId : pendingDocVo.getQuestionList()){
            TaskResultVo result = qstResultMap.get(questionId);
            sb.append(result.getQuestionDetail() == null ?
                    result.getQuestionFuture().get().getQuestionDetail()
                    : result.getQuestionDetail());
        }
        return "complete_"+System.currentTimeMillis()+"_"
                + pendingDocVo.getDocName()+".pdf";

    }
}
