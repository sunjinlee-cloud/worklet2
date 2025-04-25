package com.project2.worklet.controller;

import com.project2.worklet.component.JobPostingVO2;
import com.project2.worklet.jobPostingService.JobPostingService;
import com.project2.worklet.util_interceptor.Criteria;
import com.project2.worklet.util_interceptor.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class JobPostingController {

    @Autowired
    @Qualifier("JobPosting")
    private JobPostingService jobPostingService;


    @GetMapping("/jobposting")
    public String jobPosting(Model model, Criteria cri) {

        List<JobPostingVO2> list = jobPostingService.getList(cri);

        int total = jobPostingService.getTotal(cri);

        PageVO pagevo = new PageVO(cri, total);
        model.addAttribute("list", list);
        model.addAttribute("pageVO", pagevo);

        return "jobposting";
    }

    @GetMapping("/openjobposting")
    public String openJobPosting() {
        return "openjobposting";
    }

    @GetMapping("/airesume")
    public String airesume() {
        return "airesume";
    }

    @PostMapping("/submitresume")
    public String airesume(@RequestParam String content,
                           Model model) {

        RestTemplate restTemplate = new RestTemplate();
        String pythonApiUrl = "http://localhost:5000/format-intro";

        Map<String, String> request = new HashMap<>();
        request.put("content", content);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(pythonApiUrl, entity, Map.class);
        String formatted = (String) response.getBody().get("result");

        model.addAttribute("result", formatted);

        return "airesumeresult";
    }


//    @GetMapping("/apiexam")
//    public String apiExam() {
//        int result = jobPostingService.postList();
//        log.info("postList: " + result);
//        return "apiexam";
//    }

}
