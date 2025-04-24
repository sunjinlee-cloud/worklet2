package com.project2.worklet.controller;

import com.project2.worklet.component.JobPostingVO2;
import com.project2.worklet.jobPostingService.JobPostingService;
import com.project2.worklet.util_interceptor.Criteria;
import com.project2.worklet.util_interceptor.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class JobPostingController {

    @Autowired
    @Qualifier("JobPosting")
    private JobPostingService jobPostingService;


    @GetMapping("/jobposting")
    public String jobPosting(Model model, Criteria cri) {

        List<JobPostingVO2> list = jobPostingService.getList(cri);
        System.out.println(list.toString());

        int total = jobPostingService.getTotal(cri);
        System.out.println(total);

        PageVO pagevo = new PageVO(cri, total);
        model.addAttribute("list", list);
        model.addAttribute("pageVO", pagevo);


        return "jobposting";
    }

    @GetMapping("/openjobposting")
    public String openJobPosting() {
        return "openjobposting";
    }



//    @GetMapping("/apiexam")
//    public String apiExam() {
//        int result = jobPostingService.postList();
//        log.info("postList: " + result);
//        return "apiexam";
//    }

}
