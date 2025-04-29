package com.project2.worklet.controller;

import com.project2.worklet.board.service.NoticeService;
import com.project2.worklet.component.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

   @Autowired
    private NoticeService noticeService;


   @GetMapping("/mainPage")
    public String mainPage(Model model) {
       List<NoticeVO> topNotices = noticeService.getRecentNotices(4); // 최신 공지 4개만
       model.addAttribute("topNotices", topNotices);
       return "/Board/mainPage";

   }



}
