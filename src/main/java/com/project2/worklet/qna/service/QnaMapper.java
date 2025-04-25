package com.project2.worklet.qna.service;
import com.project2.worklet.component.QnaVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaMapper {
    int qnaForm(QnaVO vo);
    List<QnaVO> qnalist();
    void updateReply(QnaVO vo);
    void qnaReply(QnaVO vo);
    QnaVO getQnaById(Integer inquiryId);

}
