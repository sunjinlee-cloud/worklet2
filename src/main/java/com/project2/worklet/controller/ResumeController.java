package com.project2.worklet.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.io.ByteArrayOutputStream;
import java.util.HashMap;
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
