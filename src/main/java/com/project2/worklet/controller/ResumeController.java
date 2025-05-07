package com.project2.worklet.controller;

import com.project2.worklet.component.LicenseVO;
import com.project2.worklet.component.ResumeVO;
import com.project2.worklet.component.UserVO;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @ResponseBody
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
    @ResponseBody
    public ResponseEntity<byte[]> generateStatus(@RequestBody ResumeVO resumeVO) throws Exception, JRException {

        System.out.println("컨트롤러 탔음");

        // JasperReport 템플릿 파일 로드
        JasperReport report = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/resume_ex_01.jrxml")
        );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userName", "김길동");
        parameters.put("userBirthday", "2000-01-01");
        parameters.put("userAddress", "서울 마포구 망원로2길 63");
        parameters.put("userEmail", "abc@adfasfdas.com");
        parameters.put("userPhone", "010-1234-1234");
        parameters.put("army", "군필");
        parameters.put("resumeEduPeriod1", "2016.03-2019.02");
        parameters.put("resumeEduPeriod2", "2019.03-2025.02");
        parameters.put("resumeEduCont1", "서울대학교 컴퓨터공학과 졸업");
        parameters.put("resumeEduCont2", "경기고등학교 졸업");
        parameters.put("resumeCarPeriod1", "");
        parameters.put("resumeCarPeriod2", "");
        parameters.put("resumeCarBusiName1", "");
        parameters.put("resumeCarBusiName2", "");
        parameters.put("resumeCarWorkCont1", "");
        parameters.put("resumeCarWorkCont2", "");
        parameters.put("resumeCertRegDate1", "2020.01");
        parameters.put("resumeCertRegDate2", "2024.04");
        parameters.put("resumeCertRegDate3", "2024.11");
        parameters.put("resumeCertName1", "정보처리기사");
        parameters.put("resumeCertName2", "토익 950");
        parameters.put("resumeCertName3", "리눅스마스터 2급");
        parameters.put("resumeCertFrom1", "한국산업인력공단");
        parameters.put("resumeCertFrom2", "YBM토익");
        parameters.put("resumeCertFrom3", "KAIT");
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

    @PostMapping("/user/saveResume")
    public String saveResume(
            @RequestParam("resumeId") long   resumeId,
            @RequestParam("title2") String title,
            @RequestParam("growth2") String growth,
            @RequestParam("studentDay2") String studentDay,
            @RequestParam("prosAndCons2") String prosAndCons,
            @RequestParam("aspiration2") String aspiration
    ) {
        ResumeVO resumeVO = new ResumeVO();
        resumeVO.setResumeId(resumeId);
        resumeVO.setTitle(title);
        resumeVO.setGrowth(growth);
        resumeVO.setStudentDay(studentDay);
        resumeVO.setProsAndCons(prosAndCons);
        resumeVO.setAspiration(aspiration);

        int result= resumeService.saveResume(resumeVO);


        // ✅ 올바른 VO 전달
        if(result==1){
            // 업데이트된 이력서를 가져옴
            ResumeVO updatedResume = resumeService.getResumeById(resumeVO.getResumeId());

            // updatedAt 값 확인을 위한 출력
            System.out.println("updatedAt 값 확인"+updatedResume.getUpdatedAt());
        }else{
            System.out.println("안됨");
        }

        return "redirect:/user/resumePage?uniqueTime=" + resumeVO.getResumeId();
    }

    @GetMapping("/user/resumePage")
    public String saveResume(HttpSession session, Model model) {
        // 로그인한 사용자 정보 가져오기
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");


        // 로그인 정보가 없으면 userNum이 0일 가능성 있음
        if (loginUser == null || loginUser.getUserNum() == 0) {
            System.out.println("로그인 정보가 없습니다.");
            return "redirect:/user/login"; // 로그인 페이지로 리다이렉트할 수도 있음
        } else {
            int userNum = loginUser.getUserNum();
            System.out.println("로그인한 사용자: " + userNum);

            // 서비스에서 이력서 목록 가져오기
            List<ResumeVO> resumeList = resumeService.getResumesByUserNum(userNum);
            System.out.println("이력서 목록: " + resumeList);

            model.addAttribute("resumeList", resumeList);

            return "User/mypage"; // 타임리프 페이지
        }
    }


    // 이력서 수정
    @PostMapping("/user/editResume")
    public String editResume(@RequestParam("resumeId") long resumeId,
                             @RequestParam("title") String title,
                             @RequestParam("growth") String growth,
                             @RequestParam("studentDay") String studentDay,
                             @RequestParam("prosAndCons") String prosAndCons,
                             @RequestParam("aspiration") String aspiration) {
        // 수정된 내용을 ResumeVO에 담기
        ResumeVO resumeVO = new ResumeVO();
        resumeVO.setResumeId(resumeId);
        resumeVO.setTitle(title);
        resumeVO.setGrowth(growth);
        resumeVO.setStudentDay(studentDay);
        resumeVO.setProsAndCons(prosAndCons);
        resumeVO.setAspiration(aspiration);

        // 이력서 수정 서비스 호출
        int result = resumeService.updateResume(resumeVO);

        // 수정 결과에 따라 리다이렉트
        if (result == 1) {
            return "redirect:/user/resumePage"; // 수정된 이력서 목록 페이지로 리다이렉트
        } else {
            return "redirect:/user/editResume?resumeId=" + resumeId; // 수정 실패 시 다시 수정 페이지로 리다이렉트
        }
    }


    @GetMapping("/user/editResume")
    public String editResume(@RequestParam("resumeId") long resumeId, Model model) {
        // resumeId로 해당 이력서 정보 가져오기
        ResumeVO resumeVO = resumeService.getResumeById(resumeId);
        model.addAttribute("resume", resumeVO);

        return "User/resume"; // 수정 페이지로 이동
    }

    @PostMapping("/user/deleteResume")
    public String deleteResume(@RequestParam("resumeId") long resumeId) {

        ResumeVO resumeVO = new ResumeVO();
        resumeVO.setResumeId(resumeId);
       return "redirect:/user/resumePage?resumeId=" + resumeId;
    }


}



