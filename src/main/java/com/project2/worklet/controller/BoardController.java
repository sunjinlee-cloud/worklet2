package com.project2.worklet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Board")
public class BoardController {

    @GetMapping("/mainPage")
    public String mainPage() {
        return "Board/mainPage";
    }
}
