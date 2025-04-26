package com.project2.worklet.controller;

import com.project2.worklet.component.QnaVO;
import com.project2.worklet.qna.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "Board/qna_reply";
    }

    @PostMapping("/qna_reply")
    public String submitReply(@RequestParam("inquiryId") Integer inquiryId, @RequestParam("inquiryReply") String inquiryReply) {
        QnaVO qnaVO = qnaService.getQnaById(inquiryId);  // 문의 ID로 조회
        qnaVO.setInquiryReply(inquiryReply);               // 답변 내용 설정
        qnaVO.setInquiryUpdateAt(LocalDateTime.now());    // 수정일시 설정
        qnaVO.setInquiryStatus("답변완료");                // 상태 변경
        qnaService.qnaReply(qnaVO);                        // DB에 답변 저장
        return "redirect:/qna/qna_list";                  // 답변 후 페이지 리다이렉트
    }





}
