package com.project2.worklet.controller;

import com.project2.worklet.calendarService.CalendarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/calendar")
public class CalendarController {

//    @Autowired
//    @Qualifier("calendar")
//    private CalendarService calendarService;


    @GetMapping("/calendar")
    public String calendar() {
      return "calendar/calendar";
    };


}
