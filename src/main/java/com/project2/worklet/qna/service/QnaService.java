package com.project2.worklet.qna.service;
import com.project2.worklet.component.QnaVO;

import java.util.List;

public interface QnaService  {
    int qnaForm(QnaVO vo);
    List<QnaVO> qnalist();



}
