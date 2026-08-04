package com.board.board.controller;

import com.board.board.dto.Board;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    // 새 글쓰기 폼 화면
    @GetMapping("/board/new")
    public String newForm() {
        return "new"; // templates/new.html
    }

    // 새 글쓰기 폼 제출 처리
    @PostMapping("/board/new")
    public String write(Board board) {
        boardService.boardWrite(board);
        return "redirect:/board";
    }
}