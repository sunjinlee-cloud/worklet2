package com.project2.worklet.controller;

import com.project2.worklet.component.ResumeVO;
import com.project2.worklet.component.UserVO;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.project2.worklet.resume.service.ResumeService;


import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//@RestController
public class ResumeController {

   @Autowired
   @Qualifier("resumeService")
   private ResumeService resumeService;


    @PostMapping("/submitresume")
    public Map<String, String> airesume(@RequestBody Map<String, String> payload) {

        String content = payload.get("content");

        RestTemplate restTemplate = new RestTemplate();
        String pythonApiUrl = "http://localhost:5000/format-intro";

        Map<String, String> request = new HashMap<>();
        request.put("content", content);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(pythonApiUrl, entity, Map.class);
        String formatted = (String) response.getBody().get("result");

        Map<String, String> result = new HashMap<>();
        result.put("result", formatted);
        return result;
    }

    @PostMapping("/generatepdf")
    public ResponseEntity<byte[]> generateStatus(@RequestBody ResumeVO resumeVO) throws Exception, JRException {

        System.out.println("컨트롤러 탔음");

        // JasperReport 템플릿 파일 로드
        JasperReport report = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/resume_ex_01.jrxml")
        );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userName", "김길동");
        parameters.put("userBirthday", "1990-01-01");
        parameters.put("userAddress", "서울 마포구 망원로2길 63");
        parameters.put("userEmail", "abc@adfasfdas.com");
        parameters.put("userPhone", "010-1234-1234");
        parameters.put("army", "군필");
        parameters.put("resumeEduPeriod1", "2009.03-2015.02");
        parameters.put("resumeEduPeriod2", "2006.03-2009.02");
        parameters.put("resumeEduCont1", "서울대학교 컴퓨터공학과 졸업");
        parameters.put("resumeEduCont2", "경기고등학교 졸업");
        parameters.put("resumeCarPeriod1", "2017.05-2022.03");
        parameters.put("resumeCarPeriod2", "2015.03-2017.02");
        parameters.put("resumeCarBusiName1", "좋은 직장이겠지요");
        parameters.put("resumeCarBusiName2", "괜찮은 직장인듯");
        parameters.put("resumeCarWorkCont1", "이런저런 직무 as 대리");
        parameters.put("resumeCarWorkCont2", "이런저런 직무 as 사원");
        parameters.put("resumeCertRegDate1", "2014.01");
        parameters.put("resumeCertRegDate2", "2017.04");
        parameters.put("resumeCertRegDate3", "2020.09");
        parameters.put("resumeCertName1", "자격증1");
        parameters.put("resumeCertName2", "자격증2");
        parameters.put("resumeCertName3", "자격증3");
        parameters.put("resumeCertFrom1", "자격증발급기관1");
        parameters.put("resumeCertFrom2", "자격증발급기관2");
        parameters.put("resumeCertFrom3", "자격증발급기관3");
        parameters.put("resumeEarlyLife", resumeVO.getResumeEarlyLife());
        parameters.put("resumeStrengthWeakness", resumeVO.getResumeStrengthWeakness());
        parameters.put("resumeApplyReason", resumeVO.getResumeApplyReason());
        parameters.put("resumeApplyAfterDream", resumeVO.getResumeApplyAfterDream());

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        byte[] pdfBytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("resume.pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

    }

    @PostMapping("/resume/create")
    public String createResume(
            @RequestParam String title,
            @RequestParam String growth,
            @RequestParam String student_day,
            @RequestParam String pros_and_cons,
            @RequestParam String aspiration,
            @RequestParam List<Long> education_ids,  // 다중 선택된 값들을 받는 경우
            @RequestParam List<Long> career_ids,
            @RequestParam List<Long> license_ids) {

        // ResumeVO 객체 생성 후 저장
        ResumeVO resumeVO = new ResumeVO();
        resumeVO.setTitle(title);
        resumeVO.setGrowth(growth);
        resumeVO.setStudentDay(student_day);
        resumeVO.setProsAndCons(pros_and_cons);
        resumeVO.setAspiration(aspiration);
        resumeVO.setEducationIds(education_ids);
        resumeVO.setCareerIds(career_ids);
        resumeVO.setLicenseIds(license_ids);

        resumeService.insertResume(resumeVO);  // 이력서 저장 서비스 호출

        return "이력서가 성공적으로 저장되었습니다.";
    }

    @GetMapping("/resume/create")
    public String createResume(){
        return "User/resume";
    }


    @GetMapping("/user/resumeProc")
    public String resumeProc(HttpSession session) {
        long uniqueTime = System.currentTimeMillis();

        // 로그인된 사용자 정보에서 user_num 꺼내기
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        int userNum = loginUser.getUserNum();

        // VO에 값 넣기
        ResumeVO resume = new ResumeVO();
        resume.setResumeId(uniqueTime);
        resume.setUserNum(userNum);

        System.out.println(">>> [resumeProc] session ID: " + session.getId());
        System.out.println(">>> [resumeProc] loginUser: " + session.getAttribute("loginUser"));
        System.out.println("resume: " + resume);
        // 서비스 호출
        resumeService.insertResume(resume);

        return "redirect:/user/resume?uniqueTime="+uniqueTime;
    }




}



