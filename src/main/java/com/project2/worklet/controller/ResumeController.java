package com.project2.worklet.controller;

import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfWriter;
import com.project2.worklet.component.ResumeVO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.coyote.Response;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ResumeController {




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




//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("userName", "이선진");
//        parameters.put("userBirthday", "1990-09-18");
//        parameters.put("userAddress", "집주소");
//        parameters.put("resumeEarlyLife", resumeVO.getResumeEarlyLife());
//        parameters.put("resumeApplyReason", resumeVO.getResumeApplyReason());
//        parameters.put("resumeStrengthWeakness", resumeVO.getResumeStrengthWeakness());
//        parameters.put("resumeApplyAfterDream", resumeVO.getResumeApplyAfterDream());

//        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

//        byte[] pdfBytes = outputStream.toByteArray();

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDisposition(ContentDisposition.attachment().filename("resume.pdf").build());

//        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}


/// /////////////////////////////////////////////////////////////////////////////////////////

// jasper에 띄울 데이터 date에 맞춰 받아오기
//List<ResumeVO> result = new ArrayList<>();
//        ResumeVO vo = new ResumeVO("이선진", "1990-09-18", "집주소",
//                "이메일@이메일.com", "010-1234-1234",
//                "기타", "2009.03-2013.02", "",
//                "한양대학교 분자생명과학부 졸업", "",
//                "2020.01-2024.07", "",
//                "성화물산 경영관리팀", "",
//                "무역사무", "","2024.11",
//                "TOEIC 920", "YBM토익", "",
//                "","","","","",
//                "","","",
//                resumeVO.getResumeEarlyLife(), resumeVO.getResumeStrengthWeakness(),
//                resumeVO.getResumeApplyReason(), resumeVO.getResumeApplyAfterDream());
//result.add(vo);

// 받아온 데이터를 jasper datasource로 등록
//JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(result);

// jasper 컴파일할 양식 설정 - 만들어둔 jrxml 파일 경로 설정
//JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/resume_ex_01.jrxml"));

// datasource를 매핑해 양식(jrxml)에 맞게 컴파일
//HashMap<String, Object> map =new HashMap<>();
//JasperPrint report1 = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);

// return 방식1. 컴파일된 pdf파일을 현재 폴더에 생성
//			JasperExportManager.exportReportToPdfFile(report, "resume.pdf");
//			return "generated";

// return 방식2. 프린트 및 adobe pdf 화면 띄우기
// *주의: 프론트에서 화면을 띄울 수 없고, 서버 url을 직접 띄워야함..
//        byte[] data = JasperExportManager.exportReportToPdf(report);
//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=BoardStatus.pdf");
//
//        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);