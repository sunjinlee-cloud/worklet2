package com.project2.worklet.controller;
import com.project2.worklet.board.service.NoticeService;
import com.project2.worklet.component.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/Board")

public class MainController {

   @Autowired
    private NoticeService noticeService;

    @GetMapping("/mainPage")
    public String showMainPage(Model model) {
        // 최신 공지사항 6개 가져오기
        List<NoticeVO> noticeList = noticeService.getRecentNotices();

        // 뷰로 전달
        model.addAttribute("noticeList", noticeList);

        return "Board/mainPage"; // main.html로 forward
    }






}
