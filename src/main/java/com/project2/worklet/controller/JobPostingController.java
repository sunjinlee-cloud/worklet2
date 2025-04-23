package com.project2.worklet.controller;

import com.project2.worklet.component.JobPostingVO2;
import com.project2.worklet.jobPostingService.JobPostingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Slf4j
@Controller
public class JobPostingController {

    @Autowired
    @Qualifier("JobPosting")
    private JobPostingService jobPostingService;


    @GetMapping("/jobposting")
    public String jobPosting() {




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
