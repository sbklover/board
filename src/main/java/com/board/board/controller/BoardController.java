package com.board.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    @GetMapping("/")
    public String login() {
        return "login";
    }

    // 새 글쓰기 폼 화면
    @GetMapping("/board/new")
    public String newForm() {
        return "board/new"; // templates/board/new.html
    }

    // 새 글쓰기 폼 제출 처리
    @PostMapping("/board/new")
    public String write(String title, String content) {
        // Board 객체로 감싸서 저장
        // boardService.write(title, content);  // 서비스에 맞게 구현
        return "redirect:/board";
    }
}
