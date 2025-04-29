package com.project2.worklet.controller;

import com.project2.worklet.calendarService.CalendarDTO;
import com.project2.worklet.calendarService.CalendarService;
import com.project2.worklet.component.CalendarVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
//    @Qualifier("calendar")
    private CalendarService calendarService;

    @Autowired
    private HttpSession session; //세션 가져오기


    public String getUserIdFromSession() {

        Object userId = session.getAttribute("userId");

        return userId != null ? userId.toString() : null;

    };


    @GetMapping("/calendar")
    public String calendar() {
      return "calendar/calendar";
    };

    @ResponseBody
    @GetMapping("/events")
    public List<CalendarVO> getEvents() {
        String userId = getUserIdFromSession(); //세션에서 userId 가져오기

        if(userId == null) {
            return calendarService.getAllEvent(null);
        }

        return calendarService.getAllEvent(userId); //내부에 favorite 설정 완료
    }

    //찜 추가
    @PostMapping("/favorite/add")
    @ResponseBody
    public ResponseEntity<Void> addFavorite(@RequestBody CalendarDTO request) {
        calendarService.addFavorite(request.getEmpSeqNo(), request.getUserId());
        return ResponseEntity.ok().build();
    }

    //찜 삭제
    @PostMapping("/favorite/remove")
    @ResponseBody
    public ResponseEntity<Void> removeFavorite(@RequestBody CalendarDTO request) {
        calendarService.removeFavorite(request.getEmpSeqNo(), request.getUserId());
        return ResponseEntity.ok().build();
    }

    //찜 목록 가져오기
//    @GetMapping("/favorites")
//    public ResponseEntity<List<Integer>> getFavoriteEvents(@RequestParam String userId) {
//        List<Integer> favoriteEmpSeqNos = calendarService.getFavoriteEmpSeqNos(userId);
//
//        return ResponseEntity.ok(favoriteEmpSeqNos);
//
//    }










}
