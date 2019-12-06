package T9.assist;

import T9.vo.SrcDocVo;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CreatePendingDocs {
    public static List<SrcDocVo> makePendingDoc(int count){
        Random r = new Random();
        List<SrcDocVo> docList = new LinkedList<>();
        for (int i=0;i<count;i++){
            List<Integer> questionList = new LinkedList<>();
            for (int j=0;j<Consts.QUESTION_COUNT_IN_DOC;j++){
                int questionId = r.nextInt(Consts.SIZE_OF_QUESTION_BANK);
                questionList.add(questionId);
            }
            SrcDocVo pendingDocVo = new SrcDocVo("pending_"+i,
                    questionList);
            docList.add(pendingDocVo);
        }
        return docList;
    }
}
