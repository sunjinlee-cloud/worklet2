package com.project2.worklet.controller;


import com.project2.worklet.component.QnaVO;
import com.project2.worklet.qna.service.QnaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;

    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping("/qna_list")
    public String qnalist(Model model) {
        List<QnaVO> qnaList = qnaService.qnalist(); // 작성된 문의 가져오기
        model.addAttribute("qnaList", qnaList);       // 화면에 전달
        return "Board/qna_list";
    }


    @PostMapping("/qna_reply")
    public String qnaReply(QnaVO vo) {
        // 답변 등록 시간, 상태, 답변 내용 업데이트
        vo.setInquiryUpdateAt(LocalDateTime.now());
        vo.setInquiryStatus("답변완료");
        qnaService.updateReply(vo); // 답변 저장 서비스 호출
        return "redirect:/qna/qna_list"; // 리스트로 이동
    }


    @GetMapping("/qna_write")
    public String qna_write() {
        return "Board/qna_write";
    }


    @PostMapping("/qnaForm")
    public String qnaForm(QnaVO vo) {
        vo.setInquiryCreateAt(LocalDateTime.now());
        vo.setInquiryUpdateAt(LocalDateTime.now());
        vo.setInquiryStatus("답변대기");
        qnaService.qnaForm(vo);
        return "redirect:/qna/qna_list";
    }


}
