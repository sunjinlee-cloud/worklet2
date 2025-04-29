package com.project2.worklet.controller;

import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfWriter;
import com.project2.worklet.component.ResumeVO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.coyote.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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

    @PostMapping("/generate-pdf")
    public String generateStatus(@PathVariable("date") String date) throws Exception, JRException {

        // jasper에 띄울 데이터 date에 맞춰 받아오기
        List<ResumeVO> result = new ArrayList<>();
        ResumeVO vo = new ResumeVO("이선진", "1990-09-18", "집주소",
                "이메일@이메일.com", "010-1234-1234",
                "기타", "2009.03-2013.02", "",
                "한양대학교 분자생명과학부 졸업", "",
                "2020.01-2024.07", "",
                "성화물산 경영관리팀", "",
                "무역사무", "","2024.11",
                "TOEIC 920", "YBM토익", "",
                "","","","","",
                "","","",
                "어린시절에는 ", "장단점은 ",
                "지원동기는 ","포부는 ");
        result.add(vo);

        // 받아온 데이터를 jasper datasource로 등록
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(result);

        // jasper 컴파일할 양식 설정 - 만들어둔 jrxml 파일 경로 설정
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/resume_ex_01.jrxml"));

        // datasource를 매핑해 양식(jrxml)에 맞게 컴파일
        HashMap<String, Object> map =new HashMap<>();
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);

        // return 방식1. 컴파일된 pdf파일을 현재 폴더에 생성
			JasperExportManager.exportReportToPdfFile(report, "BoardStatus.pdf");
			return "generated";

        // return 방식2. 프린트 및 adobe pdf 화면 띄우기
        // *주의: 프론트에서 화면을 띄울 수 없고, 서버 url을 직접 띄워야함..
//        byte[] data = JasperExportManager.exportReportToPdf(report);
//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=BoardStatus.pdf");
//
//        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
    }

    //    @PostMapping("/generate-pdf")
//    public ResponseEntity<byte[]> generatePdf(@RequestBody Map<String, String> contents) throws Exception {
//        // PDF를 저장할 메모리 스트림 생성
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//
//        // PDF 문서 생성
//        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
//        PdfDocument pdfDoc = new PdfDocument(writer);
//        Document document = new Document(pdfDoc);
//
//        // 데이터 처리 (여러 개의 textarea 데이터)
//        for (Map.Entry<String, String> entry : contents.entrySet()) {
//            String type = entry.getKey();
//            String content = entry.getValue();
//
//            // PDF에 각 항목 추가
//            document.add(new Paragraph(type + ":"));
//            document.add(new Paragraph(content));
//            document.add(new Paragraph("\n"));  // 항목 간 간격
//        }
//
//        // PDF 저장 완료
//        document.close();
//
//        // 클라이언트로 PDF 파일을 반환
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "attachment; filename=resume.pdf");
//
//        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
//    }

}
