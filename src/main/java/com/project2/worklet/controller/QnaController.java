package com.project2.worklet.controller;

import com.project2.worklet.component.QnaVO;
import com.project2.worklet.qna.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private QnaService qnaService;

    @GetMapping("/qna_write")
    public String qna_write() {
        return "Board/qna_write";
    }

    @GetMapping("/qna_list")
    public String qnalist(Model model) {
        List<QnaVO> qnaList = qnaService.qnalist(); // 작성된 문의 가져오기
        model.addAttribute("qnaList", qnaList);       // 화면에 전달
        return "Board/qna_list";
    }




    @PostMapping("/qnaForm")
    public String qnaForm(QnaVO vo) {
        vo.setInquiryCreateAt(LocalDateTime.now());
        vo.setInquiryUpdateAt(LocalDateTime.now());
        vo.setInquiryStatus("답변대기");
        qnaService.qnaForm(vo);
        return "redirect:/qna/qna_list";
    }


    @GetMapping("/qna_reply")
    public String qnaReply(Model model) {
        List<QnaVO> qnaList = qnaService.qnalist(); // 같은 서비스 메서드 재사용 가능
        model.addAttribute("qnaList", qnaList);     // JSP에서 출력 가능
        return "qna_reply1";
    }

    @PostMapping("/qna_reply")
    public String submitReply(QnaVO vo) {
        // 답변 업데이트 시
        vo.setInquiryUpdateAt(LocalDateTime.now());  // 수정일시 설정
        vo.setInquiryStatus("답변완료");               // 상태 변경
        qnaService.qnaReply(vo);  // 서비스 메서드 호출
        return "redirect:/qna/qna_reply";  // 다시 답변 관리 페이지로 리다이렉트
    }



}
