package com.project2.worklet.controller;
import com.project2.worklet.board.service.NoticeService;
import com.project2.worklet.component.JobPostingVO2;
import com.project2.worklet.component.NoticeVO;
import com.project2.worklet.jobPostingService.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/Board")

public class MainController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private JobPostingService jobPostingService;

    @GetMapping("/mainPage")
    public String showMainPage(Model model) {
        // 최신 공지사항 6개 가져오기
        List<NoticeVO> noticeList = noticeService.getRecentNotices();
        // 최신 채용 공고 6개 가져오기
        List<JobPostingVO2> jobPostingList = jobPostingService.getRecentJobPostings(); // 채용 공고

        // 뷰로 전달
        model.addAttribute("noticeList", noticeList); // 공지사항 목록
        model.addAttribute("jobPostingList", jobPostingList); // 채용 공고 목록

        return "Board/mainPage"; // mainPage 뷰로 이동
    }



















}
