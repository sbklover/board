package com.board.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/")
    public String login() {
        return "login";
    }

//    @GetMapping("/board")
//    public String listAll() {
//        return "list";
//    }
}
